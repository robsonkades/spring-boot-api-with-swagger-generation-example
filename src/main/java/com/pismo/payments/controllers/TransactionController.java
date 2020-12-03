package com.pismo.payments.controllers;

import com.pismo.payments.dtos.TransactionDTO;
import com.pismo.payments.services.ICreateTransactionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Api(value = "Api para gerenciar transações", produces = MediaType.APPLICATION_JSON_VALUE, tags = {"Transação"})
public class TransactionController {

    ICreateTransactionService createTransactionService;

    @RequestMapping(value = "/transactions", method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
    @ApiOperation(value = "Cria uma transação financeira")
    public ResponseEntity<TransactionDTO> create(@Valid @RequestBody TransactionDTO transactionDTO) {
        var response = createTransactionService.execute(transactionDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
