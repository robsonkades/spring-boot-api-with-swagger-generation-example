package com.pismo.payments.services;

import com.pismo.payments.dtos.AccountDTO;
import com.pismo.payments.dtos.TransactionDTO;
import com.pismo.payments.entities.Transaction;
import com.pismo.payments.repositories.TransactionRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CreateTransactionService implements ICreateTransactionService {

    TransactionRepository repository;
    ModelMapper modelMapper;
    IGetAccountService getAccountService;
    IUpdateLimitAccountService iUpdateLimitAccountService;

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ) // race condition
    public TransactionDTO execute(TransactionDTO transactionDTO) {
        var accountId = transactionDTO.getAccount_id();
        AccountDTO accountDTO = getAccountService.execute(accountId);
        iUpdateLimitAccountService.execute(accountDTO, transactionDTO.getOperation_type_id(), transactionDTO.getAmount());
        Transaction transaction = modelMapper.map(transactionDTO, Transaction.class);
        var response = repository.save(transaction);
        return modelMapper.map(response, TransactionDTO.class);
    }
}
