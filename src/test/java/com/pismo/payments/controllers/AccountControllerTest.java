package com.pismo.payments.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pismo.payments.dtos.AccountDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AccountControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void shouldBeAbleToCreateAccount() throws Exception {

        ResultActions response = mockMvc.perform(
                post("/accounts")
                        .content(new ObjectMapper().writeValueAsString(createAccount(999L)))
                        .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"account_id\":1,\"document_number\":999}", true));
    }

    @Test
    void shouldBeAbleToGetAccount() throws Exception {
        mockMvc.perform(
                post("/accounts")
                        .content(new ObjectMapper().writeValueAsString(createAccount(999L)))
                        .contentType(MediaType.APPLICATION_JSON));

        ResultActions response = mockMvc.perform(
                get("/accounts/1")
                        .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"account_id\":1,\"document_number\":999}", true));
    }

    @Test
    void shouldNotBeAbleToGetAccountIfNonExisting() throws Exception {
        ResultActions response = mockMvc.perform(
                get("/accounts/1")
                        .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isBadRequest());
    }

    private AccountDTO createAccount(Long documentNumber) {
        var account = new AccountDTO();
        account.setDocument_number(documentNumber);
        return account;
    }
}
