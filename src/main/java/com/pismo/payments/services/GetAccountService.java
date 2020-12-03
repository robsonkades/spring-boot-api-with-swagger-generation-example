package com.pismo.payments.services;

import com.pismo.payments.dtos.AccountDTO;
import com.pismo.payments.exceptions.AccountNotFoundException;
import com.pismo.payments.repositories.AccountRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GetAccountService implements IGetAccountService {

    AccountRepository repository;
    ModelMapper modelMapper;

    @Override
    public AccountDTO execute(Long account_id) {
        var account = repository
                .findById(account_id).orElseThrow(() -> new AccountNotFoundException(account_id));

        return modelMapper.map(account, AccountDTO.class);
    }
}
