package com.banking.service;

import com.banking.BankingSystem;
import com.banking.model.Account;
import com.banking.exception.*;
import com.banking.model.AccountType;

import java.math.BigDecimal;

public class AccountService {
    private final BankingSystem bankingSystem;

    public AccountService(BankingSystem bankingSystem) {
        this.bankingSystem = bankingSystem;
    }

    public void transfer(String fromAcc, String toAcc, BigDecimal amount)
            throws InsufficientFundsException, AccountNotFoundException {
        Account from = findAccount(fromAcc);
        Account to = findAccount(toAcc);

        try {
            from.withdraw(amount);
            to.deposit(amount);
        } catch (InsufficientFundsException | IllegalArgumentException e) {
            // Handle specific exceptions
            throw e;
        } catch (Exception e) {
            // Handle general exceptions
            throw new BankingException("Transfer failed", e);
        }
    }

    private Account findAccount(String number) {
        // Assertions for invariants
        assert number != null && !number.isEmpty() :
                "Account number cannot be null or empty";

        return bankingSystem.findAccount(number);
    }

    public void createAccount(AccountType accountType, String accountId, BigDecimal initialBalance) {
        String AccountType = accountType.toString();
        String AccountId = accountId;
        BigDecimal initialBalanceDouble = initialBalance;

    }

    public String viewAccount(String viewAccountId) {
        String AccountId = viewAccountId;

        return AccountId;
    }

    public void deposit(String depositAccountId, BigDecimal depositAmount) {
        String AccountId = depositAccountId;
        BigDecimal initialBalanceDouble = depositAmount;

    }

    public void withdraw(String withdrawAccountId, BigDecimal withdrawAmount) {
        String AccountId = withdrawAccountId;
        Double initialBalanceDouble = withdrawAmount.doubleValue();

    }

    public Iterable<Object> getAllTransactions() {
        return null;
    }

}


