package com.pismo.payments.services;

import com.pismo.payments.dtos.AccountDTO;
import com.pismo.payments.entities.Account;
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
public class CreateAccountService implements ICreateAccountService {

    ModelMapper modelMapper;
    AccountRepository repository;

    @Override
    public AccountDTO execute(AccountDTO accountDTO) {
        var newAccount = modelMapper.map(accountDTO, Account.class);
        var account = repository.save(newAccount);
        return modelMapper.map(account, AccountDTO.class);
    }
}
