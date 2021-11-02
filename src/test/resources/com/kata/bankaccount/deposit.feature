Feature: Deposit
  Deposit an amount in bank account
  Scenario: Deposit 100 amount in account with balance 100
    Given Account with balance of 100
    When Deposit amount of 100
    Then balance equal to 200
  Scenario: Deposit 100 amount in account with balance 0
    Given Account with balance of 0
    When Deposit amount of 100
    Then balance equal to 100