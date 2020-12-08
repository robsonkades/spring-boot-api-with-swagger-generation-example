package com.pismo.payments.services;

import java.math.BigDecimal;

import com.pismo.payments.dtos.AccountDTO;
import com.pismo.payments.dtos.OperationType;
import com.pismo.payments.entities.Account;
import com.pismo.payments.exceptions.AccountLimitExceededException;
import com.pismo.payments.repositories.AccountRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UpdateLimitAccountService implements IUpdateLimitAccountService {

    ModelMapper modelMapper;
    AccountRepository repository;

    @Override
    public Account execute(AccountDTO dto, int operationType, BigDecimal amount) {
        Account account =  modelMapper.map(dto, Account.class);
        if(operationType == OperationType.PAYMENT.getValue()) {
            var newLimit = account.getAvailable_credit_limit().add(amount);
            account.setAvailable_credit_limit(newLimit);
        } else {
            if(amount.doubleValue() > account.getAvailable_credit_limit().doubleValue()) {
                throw new AccountLimitExceededException(account.getId());
            }
            var newLimit = account.getAvailable_credit_limit().subtract(amount);
            account.setAvailable_credit_limit(newLimit);
        }
        return repository.save(account);
    }
}
