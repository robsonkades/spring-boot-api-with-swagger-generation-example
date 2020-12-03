package com.pismo.payments.services;

import java.math.BigDecimal;

import com.pismo.payments.dtos.AccountDTO;
import com.pismo.payments.dtos.OperationType;
import com.pismo.payments.dtos.TransactionDTO;
import com.pismo.payments.exceptions.AccountNotFoundException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class CreateTransactionServiceTest {

    @Autowired
    ICreateTransactionService createTransactionService;
    @Autowired
    ICreateAccountService iCreateAccountService;

    @Test
    void shouldBeAbleToCreateTransaction_PAYMENT() {
        var account = iCreateAccountService.execute(createAccount(999L));
        var actual = createTransactionService
                .execute(createTransaction(
                        account.getAccount_id(),
                        OperationType.PAYMENT.getValue(),
                        new BigDecimal(10.1)));

        var expectedAmount = new BigDecimal(10.1);
        Assertions.assertEquals(expectedAmount, actual.getAmount());
        Assertions.assertEquals(account.getAccount_id(), actual.getAccount_id());
        Assertions.assertEquals(OperationType.PAYMENT.getValue(), actual.getOperation_type_id());
    }

    @Test
    void shouldBeAbleToCreateTransaction_SPOT_PURCHASE_RATE() {
        var account = iCreateAccountService.execute(createAccount(999L));
        var actual = createTransactionService
                .execute(createTransaction(
                        account.getAccount_id(),
                        OperationType.SPOT_PURCHASE_RATE.getValue(),
                        new BigDecimal(10.1)));

        var expectedAmount = new BigDecimal(10.1).negate();
        Assertions.assertEquals(expectedAmount, actual.getAmount());
        Assertions.assertEquals(account.getAccount_id(), actual.getAccount_id());
        Assertions.assertEquals(OperationType.SPOT_PURCHASE_RATE.getValue(), actual.getOperation_type_id());
    }

    @Test
    void shouldBeAbleToCreateTransaction_WITHDRAW() {
        var account = iCreateAccountService.execute(createAccount(999L));
        var actual = createTransactionService
                .execute(createTransaction(
                        account.getAccount_id(),
                        OperationType.WITHDRAW.getValue(),
                        new BigDecimal(10.1)));

        var expectedAmount = new BigDecimal(10.1).negate();
        Assertions.assertEquals(expectedAmount, actual.getAmount());
        Assertions.assertEquals(account.getAccount_id(), actual.getAccount_id());
        Assertions.assertEquals(OperationType.WITHDRAW.getValue(), actual.getOperation_type_id());
    }

    @Test
    void shouldBeAbleToCreateTransaction_PAY_IN_INSTALLMENTS() {
        var account = iCreateAccountService.execute(createAccount(999L));
        var actual = createTransactionService
                .execute(createTransaction(
                        account.getAccount_id(),
                        OperationType.PAY_IN_INSTALLMENTS.getValue(),
                        new BigDecimal(10.1)));

        var expectedAmount = new BigDecimal(10.1).negate();
        Assertions.assertEquals(expectedAmount, actual.getAmount());
        Assertions.assertEquals(account.getAccount_id(), actual.getAccount_id());
        Assertions.assertEquals(OperationType.PAY_IN_INSTALLMENTS.getValue(), actual.getOperation_type_id());
    }

    @Test
    void shouldNotBeAbleToCreateTransactionIfAccountNonExisting() {
        Exception exception = Assertions.assertThrows(AccountNotFoundException.class, () -> {
            createTransactionService.execute(createTransaction(1L, 1, new BigDecimal(10.1)));
        });
        String expectedMessage = "Account not found: 1";
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));
        Assertions.assertTrue(exception instanceof AccountNotFoundException);
    }

    private AccountDTO createAccount(Long documentNumber) {
        var account = new AccountDTO();
        account.setDocument_number(documentNumber);
        return account;
    }

    private TransactionDTO createTransaction(Long account_id, int operation_type_id, BigDecimal amount) {
        var transaction = new TransactionDTO();
        transaction.setAccount_id(account_id);
        transaction.setOperation_type_id(operation_type_id);
        transaction.setAmount(amount);
        return transaction;
    }
}
