package com.pismo.payments.dtos;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class AccountDTO {

    Long account_id;

    @Min(1)
    @Max(Long.MAX_VALUE)
    @NotNull
    Long document_number;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    BigDecimal available_credit_limit;
}