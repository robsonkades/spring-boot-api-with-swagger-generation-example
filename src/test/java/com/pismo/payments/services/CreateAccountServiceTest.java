package com.pismo.payments.services;

import com.pismo.payments.dtos.AccountDTO;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class CreateAccountServiceTest {

    @Autowired
    ICreateAccountService createAccountService;

    @Test
    void shouldBeAbleToCreateAccount() {
        var account = new AccountDTO();
        account.setDocument_number(11111L);
        var response = createAccountService.execute(account);
        Assertions.assertEquals(1, response.getAccount_id().intValue());
        Assertions.assertEquals(11111, response.getDocument_number().intValue());
    }
}
