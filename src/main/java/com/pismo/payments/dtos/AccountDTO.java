package com.pismo.payments.dtos;

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
}