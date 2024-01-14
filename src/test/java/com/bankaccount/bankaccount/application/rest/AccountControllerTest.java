package com.bankaccount.bankaccount.application.rest;

import com.bankaccount.bankaccount.common.InsufficientFundsException;
import com.bankaccount.bankaccount.common.ResourceNotFoundException;
import com.bankaccount.bankaccount.domain.model.*;
import com.bankaccount.bankaccount.domain.ports.in.ConsultAccountUseCase;
import com.bankaccount.bankaccount.domain.ports.in.DepositUseCase;
import com.bankaccount.bankaccount.domain.ports.in.WithdrawUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static com.bankaccount.bankaccount.domain.service.OperationsService.NO_ACCOUNT_WAS_FOUND_FOR_THE_GIVEN_ID;
import static com.bankaccount.bankaccount.domain.service.OperationsService.INSUFFICIENT_FUNDS;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest
@AutoConfigureMockMvc
class AccountControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ConsultAccountUseCase consultAccountUseCase;

    @MockBean
    DepositUseCase depositUseCase;

    @MockBean
    WithdrawUseCase withdrawUseCase;

    @Test
    void should_return_account() throws Exception {
        long accountId = 15L;

        Account account = new Account(accountId, new Balance(BigDecimal.TEN), List.of(new Transaction(new Operation(OperationType.DEPOSIT, new Amount(BigDecimal.TEN)
                , LocalDateTime.of(2024, 11, 2, 9, 5)), new Balance(BigDecimal.TEN))));
        when(consultAccountUseCase.account(accountId)).thenReturn(account);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/accounts/{id}", accountId))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(account)));

    }

    @Test
    void should_return_404_not_found() throws Exception {
        long accountId = 15L;

        when(consultAccountUseCase.account(accountId)).thenThrow(new ResourceNotFoundException(NO_ACCOUNT_WAS_FOUND_FOR_THE_GIVEN_ID));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/accounts/{id}", accountId))
                .andExpect(status().isNotFound());

    }

    @Test
    void should_return_200_OK_when_deposit() throws Exception {
        long accountId = 15L;

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/accounts/{id}/deposit/{amount}", accountId, BigDecimal.TEN))
                .andExpect(status().isOk());

    }

    @Test
    void should_return_404_NOT_FOUND_when_account_is_not_found() throws Exception {
        long accountId = 15L;
        Amount amount = new Amount(BigDecimal.TEN);

        doThrow(new ResourceNotFoundException(NO_ACCOUNT_WAS_FOUND_FOR_THE_GIVEN_ID)).when(depositUseCase).deposit(accountId, amount);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/accounts/{id}/deposit/{amount}", accountId, BigDecimal.TEN))
                .andExpect(content().json("{\"errors\":[\"No account was found for the given ID\"]}"))
                .andExpect(status().isNotFound());

    }

    @Test
    void should_return_200_OK_when_withdraw() throws Exception {
        long accountId = 15L;

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/accounts/{id}/withdraw/{amount}", accountId, BigDecimal.TEN))
                .andExpect(status().isOk());

    }

    @Test
    void should_return_404_NOT_FOUND_withdraw_and_when_account_is_not_found() throws Exception {
        long accountId = 15L;
        Amount amount = new Amount(BigDecimal.TEN);

        doThrow(new ResourceNotFoundException(NO_ACCOUNT_WAS_FOUND_FOR_THE_GIVEN_ID)).when(withdrawUseCase).withdraw(accountId, amount);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/accounts/{id}/withdraw/{amount}", accountId, BigDecimal.TEN))
                .andExpect(content().json("{\"errors\":[\"No account was found for the given ID\"]}"))
                .andExpect(status().isNotFound());

    }

    @Test
    void should_return_422_UNPROCESSABLE_ENTITY_when_account_is_not_found() throws Exception {
        long accountId = 15L;
        Amount amount = new Amount(BigDecimal.TEN);

        doThrow(new InsufficientFundsException(INSUFFICIENT_FUNDS)).when(withdrawUseCase).withdraw(accountId, amount);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/accounts/{id}/withdraw/{amount}", accountId, BigDecimal.TEN))
                .andExpect(content().json("{\"errors\":[\"Insufficient funds\"]}"))
                .andExpect(status().isUnprocessableEntity());

    }
}