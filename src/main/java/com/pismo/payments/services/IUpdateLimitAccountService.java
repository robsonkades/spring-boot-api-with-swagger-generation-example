package com.pismo.payments.services;

import java.math.BigDecimal;

import com.pismo.payments.dtos.AccountDTO;
import com.pismo.payments.entities.Account;

public interface IUpdateLimitAccountService {
    Account execute(AccountDTO dto, int operationType, BigDecimal amount);
}
