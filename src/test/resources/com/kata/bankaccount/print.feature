Feature: Print
  Print statement lines of a bank account
  Scenario: Print a deposit statement line
    Given Account with balance of 0
    When Deposit amount of 100
    When print statement
    Then console print print the following
      | date       | operation   | amount    | balance |
      | 2021-10-21 17:00 | DEPOSIT     | 100       | 100     |