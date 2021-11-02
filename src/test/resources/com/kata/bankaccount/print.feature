Feature: Print
  Print statement lines of a bank account
  Scenario: Print a deposit statement line
    Given Account with balance of 0
    When Deposit amount of 100
    When print statement
    Then console print print the following
      | date       | operation   | amount    | balance |
      | 2021-10-21 17:00 | DEPOSIT     | 100       | 100     |

  Scenario: Print a withdrawal statement line
    Given Account with balance of 200
    When Withdrawal amount of 100
    When print statement
    Then console print print the following
      | date       | operation   | amount    | balance |
      | 2021-10-21 17:00 | WITHDRAWAL     | 100       | 100     |

  Scenario: Print a withdrawal and deposit statement line
    Given Account with balance of 100
    When Deposit amount of 100
    When Withdrawal amount of 100
    When print statement
    Then console print print the following
      | date       | operation   | amount    | balance |
      | 2021-10-21 17:00 | DEPOSIT     | 100       | 200     |
      | 2021-10-21 17:00 | WITHDRAWAL     | 100       | 100     |