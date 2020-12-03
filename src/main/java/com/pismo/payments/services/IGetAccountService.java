package com.pismo.payments.services;

import com.pismo.payments.dtos.AccountDTO;

public interface IGetAccountService {
    AccountDTO execute(Long account_id);
}