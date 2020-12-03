package com.pismo.payments.services;

import com.pismo.payments.dtos.AccountDTO;

public interface ICreateAccountService {
    AccountDTO execute(AccountDTO account);
}
