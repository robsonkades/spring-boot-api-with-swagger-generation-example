package com.pismo.payments.services;

import com.pismo.payments.dtos.AccountDTO;
import com.pismo.payments.exceptions.AccountNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class GetAccountServiceTest {

    @Autowired
    IGetAccountService getAccountService;
    @Autowired
    ICreateAccountService createAccountService;

    @Test
    void shouldBeAbleToGetAccount() {
        var expected = createAccountService.execute(createAccount(10000L));
        var actual = getAccountService.execute(1L);


        Assertions.assertEquals(expected.getAccount_id(), actual.getAccount_id());
        Assertions.assertEquals(expected.getDocument_number(), actual.getDocument_number());
    }

    @Test
    void shouldNotBeAbleToGetAccountIfNonExisting() {
        Exception exception = Assertions.assertThrows(AccountNotFoundException.class, () -> {
            getAccountService.execute(1L);
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
}
