package com.pismo.payments.services;

import java.math.BigDecimal;

import com.pismo.payments.dtos.AccountDTO;
import com.pismo.payments.dtos.OperationType;
import com.pismo.payments.exceptions.AccountLimitExceededException;
import com.pismo.payments.exceptions.AccountNotFoundException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UpdateLimitAccountServiceTest {

    @Autowired
    IUpdateLimitAccountService iUpdateLimitAccountService;
    @Autowired
    ICreateAccountService iCreateAccountService;

    @Test
    void shouldBeAbleToUpdateLimit_PAYMENT() {
        var accountDTO = iCreateAccountService.execute(createAccount(10000L));
        var actual = iUpdateLimitAccountService.execute(accountDTO, OperationType.PAYMENT.getValue(), new BigDecimal(20));
        Assertions.assertEquals(accountDTO.getAccount_id(), actual.getId());
        Assertions.assertEquals(accountDTO.getDocument_number(), actual.getDocumentNumber());
        Assertions.assertEquals(new BigDecimal(120), actual.getAvailable_credit_limit());
    }

    @Test
    void shouldBeAbleToUpdateLimit_SPOT_PURCHASE_RATE() {
        var accountDTO = iCreateAccountService.execute(createAccount(10000L));
        var actual = iUpdateLimitAccountService.execute(accountDTO, OperationType.SPOT_PURCHASE_RATE.getValue(), new BigDecimal(20));
        Assertions.assertEquals(accountDTO.getAccount_id(), actual.getId());
        Assertions.assertEquals(accountDTO.getDocument_number(), actual.getDocumentNumber());
        Assertions.assertEquals(new BigDecimal(80), actual.getAvailable_credit_limit());
    }

    @Test
    void shouldBeAbleToUpdateLimit_WITHDRAW() {
        var accountDTO = iCreateAccountService.execute(createAccount(10000L));
        var actual = iUpdateLimitAccountService.execute(accountDTO, OperationType.WITHDRAW.getValue(), new BigDecimal(20));
        Assertions.assertEquals(accountDTO.getAccount_id(), actual.getId());
        Assertions.assertEquals(accountDTO.getDocument_number(), actual.getDocumentNumber());
        Assertions.assertEquals(new BigDecimal(80), actual.getAvailable_credit_limit());
    }

    @Test
    void shouldBeAbleToUpdateLimit_PAY_IN_INSTALLMENTS() {
        var accountDTO = iCreateAccountService.execute(createAccount(10000L));
        var actual = iUpdateLimitAccountService.execute(accountDTO, OperationType.PAY_IN_INSTALLMENTS.getValue(), new BigDecimal(20));
        Assertions.assertEquals(accountDTO.getAccount_id(), actual.getId());
        Assertions.assertEquals(accountDTO.getDocument_number(), actual.getDocumentNumber());
        Assertions.assertEquals(new BigDecimal(80), actual.getAvailable_credit_limit());
    }

    @Test
    void shouldNotBeAbleUpdateLimitIfAccountDoesntLimit() {
        var accountDTO = iCreateAccountService.execute(createAccount(10000L));
        Exception exception = Assertions.assertThrows(AccountLimitExceededException.class, () -> {
            iUpdateLimitAccountService.execute(accountDTO, OperationType.PAY_IN_INSTALLMENTS.getValue(), new BigDecimal(200));
        });

        String expectedMessage = "Account 1 exceeded the limit";
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));
        Assertions.assertTrue(exception instanceof AccountLimitExceededException);
    }

    private AccountDTO createAccount(Long documentNumber) {
        var account = new AccountDTO();
        account.setDocument_number(documentNumber);
        account.setAvailable_credit_limit(new BigDecimal(100));
        return account;
    }
}
