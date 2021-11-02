Feature: Withdrawal
  Withdrawal an amount from bank account
  Scenario: Withdrawal 100 amount from account with balance 1000
    Given Account with balance of 1000
    When Withdrawal amount of 100
    Then balance equal to 900