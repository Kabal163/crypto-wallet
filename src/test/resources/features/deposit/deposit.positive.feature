Feature: Make deposit

  Scenario: user successfully creates deposit
    Given user wants to create deposit with the following parameters
      | datetime            | amount |
      | 2020-11-22T22:00:00 | 0.0001 |
    When user calls API
    Then receives response with status 200
    And response contains field succeeded with value :bool=true
    And response contains field message with null value
    And response contains field error with null value
    And response contains field data.amount with value :bigDec=0.0001
    And response contains field data.id with not null value