package com.pismo.payments.dtos;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class TransactionDTO {

    @Min(1)
    @Max(Long.MAX_VALUE)
    @NotNull
    Long account_id;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    BigDecimal amount;

    @NotNull
    @Min(1)
    @Max(4)
    int operation_type_id;
}
