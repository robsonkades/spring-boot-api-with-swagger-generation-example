package com.pismo.payments.services;

import com.pismo.payments.dtos.TransactionDTO;

public interface ICreateTransactionService {
    TransactionDTO execute(TransactionDTO transactionDTO);
}
