package com.pismo.payments.configuration.mapper;

import java.util.Date;

import javax.annotation.PostConstruct;

import com.pismo.payments.dtos.AccountDTO;
import com.pismo.payments.dtos.OperationType;
import com.pismo.payments.dtos.TransactionDTO;
import com.pismo.payments.entities.Account;
import com.pismo.payments.entities.Transaction;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EntityMappingConfiguration {

    ModelMapper modelMapper;

    @PostConstruct
    public void doMapping() {
        modelMapper.createTypeMap(AccountDTO.class, Account.class).setConverter(new AbstractConverter<AccountDTO, Account>() {
            @Override
            protected Account convert(AccountDTO accountDTO) {
                Account newAccount = new Account();
                newAccount.setDocumentNumber(accountDTO.getDocument_number());
                return newAccount;
            }
        });
        modelMapper.createTypeMap(Account.class, AccountDTO.class).setConverter(new AbstractConverter<Account, AccountDTO>() {
            @Override
            protected AccountDTO convert(Account account) {
                AccountDTO newAccount = new AccountDTO();
                newAccount.setDocument_number(account.getDocumentNumber());
                newAccount.setAccount_id(account.getId());
                return newAccount;
            }
        });
        modelMapper.createTypeMap(TransactionDTO.class, Transaction.class).setConverter(new AbstractConverter<TransactionDTO, Transaction>() {
            @Override
            protected Transaction convert(TransactionDTO transactionDTO) {
                Transaction newTransaction = new Transaction();
                newTransaction.setAccountId(transactionDTO.getAccount_id());
                if (transactionDTO.getOperation_type_id() == OperationType.PAYMENT.getValue()) {
                    newTransaction.setAmount(transactionDTO.getAmount());
                } else {
                    newTransaction.setAmount(transactionDTO.getAmount().negate());
                }
                newTransaction.setOperationTypeId(transactionDTO.getOperation_type_id());
                newTransaction.setEventDate(new Date());
                return newTransaction;
            }
        });
        modelMapper.createTypeMap(Transaction.class, TransactionDTO.class).setConverter(new AbstractConverter<Transaction, TransactionDTO>() {
            @Override
            protected TransactionDTO convert(Transaction transaction) {
                TransactionDTO transactionDTO = new TransactionDTO();
                transactionDTO.setAccount_id(transaction.getAccountId());
                transactionDTO.setAmount(transaction.getAmount());
                transactionDTO.setOperation_type_id(transaction.getOperationTypeId());
                return transactionDTO;
            }
        });
    }
}
