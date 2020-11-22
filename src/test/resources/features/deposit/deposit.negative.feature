Feature: Make deposit

  Scenario: user cannot create deposit due to future datetime
    Given user wants to create deposit with the following parameters
      | datetime            | amount |
      | 2050-11-22T22:00:00 | 0.0001 |
    When user calls API
    Then receives response with status 400
    And response contains field succeeded with value :bool=false
    And response contains field message with value :string=Invalid argument
    And response contains field error with value :string=org.springframework.web.bind.MethodArgumentNotValidException

  Scenario: user cannot create deposit due to negative amount
    Given user wants to create deposit with the following parameters
      | datetime            | amount |
      | 2020-11-22T22:00:00 | -0.0001 |
    When user calls API
    Then receives response with status 400
    And response contains field succeeded with value :bool=false
    And response contains field message with value :string=Invalid argument
    And response contains field error with value :string=org.springframework.web.bind.MethodArgumentNotValidException