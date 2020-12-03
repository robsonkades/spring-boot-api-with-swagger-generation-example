package com.pismo.payments.controllers;

import java.math.BigDecimal;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pismo.payments.dtos.AccountDTO;
import com.pismo.payments.dtos.TransactionDTO;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TransactionControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void shouldBeAbleToCreateTransaction_SPOT_PURCHASE_RATE() throws Exception {

        ResultActions response = mockMvc.perform(
                post("/accounts")
                        .content(new ObjectMapper().writeValueAsString(createAccount(999L)))
                        .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"account_id\":1,\"document_number\":999}", true));

        ResultActions responseTransaction = mockMvc.perform(
                post("/transactions")
                        .content(new ObjectMapper().writeValueAsString(createTransaction(1L, 1, new BigDecimal(12.5))))
                        .contentType(MediaType.APPLICATION_JSON));

        responseTransaction.andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"account_id\":1,\"amount\":-12.5,\"operation_type_id\":1}", true));
    }

    @Test
    void shouldBeAbleToCreateTransaction_PAY_IN_INSTALLMENTS() throws Exception {

        ResultActions response = mockMvc.perform(
                post("/accounts")
                        .content(new ObjectMapper().writeValueAsString(createAccount(999L)))
                        .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"account_id\":1,\"document_number\":999}", true));

        ResultActions responseTransaction = mockMvc.perform(
                post("/transactions")
                        .content(new ObjectMapper().writeValueAsString(createTransaction(1L, 2, new BigDecimal(12.5))))
                        .contentType(MediaType.APPLICATION_JSON));

        responseTransaction.andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"account_id\":1,\"amount\":-12.5,\"operation_type_id\":2}", true));
    }

    @Test
    void shouldBeAbleToCreateTransaction_WITHDRAW() throws Exception {

        ResultActions response = mockMvc.perform(
                post("/accounts")
                        .content(new ObjectMapper().writeValueAsString(createAccount(999L)))
                        .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"account_id\":1,\"document_number\":999}", true));

        ResultActions responseTransaction = mockMvc.perform(
                post("/transactions")
                        .content(new ObjectMapper().writeValueAsString(createTransaction(1L, 3, new BigDecimal(12.5))))
                        .contentType(MediaType.APPLICATION_JSON));

        responseTransaction.andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"account_id\":1,\"amount\":-12.5,\"operation_type_id\":3}", true));
    }

    @Test
    void shouldBeAbleToCreateTransaction_PAYMENT() throws Exception {

        ResultActions response = mockMvc.perform(
                post("/accounts")
                        .content(new ObjectMapper().writeValueAsString(createAccount(999L)))
                        .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"account_id\":1,\"document_number\":999}", true));

        ResultActions responseTransaction = mockMvc.perform(
                post("/transactions")
                        .content(new ObjectMapper().writeValueAsString(createTransaction(1L, 4, new BigDecimal(12.5))))
                        .contentType(MediaType.APPLICATION_JSON));

        responseTransaction.andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"account_id\":1,\"amount\":12.5,\"operation_type_id\":4}", true));
    }

    @Test
    void shouldNotBeAbleToCreateTransactionIfAccountNonExisting() throws Exception {

        ResultActions responseTransaction = mockMvc.perform(
                post("/transactions")
                        .content(new ObjectMapper().writeValueAsString(createTransaction(1L, 4, new BigDecimal(12.5))))
                        .contentType(MediaType.APPLICATION_JSON));

        responseTransaction.andExpect(status().isBadRequest());
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
