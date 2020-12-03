package com.pismo.payments.dtos;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class AccountDTO {

    Long account_id;

    @Min(1)
    @Max(Long.MAX_VALUE)
    @NotNull
    Long document_number;
}