Feature:
  GET operations - Users from /public/v2

  Scenario: Score users
    Given GET users from "/public-api/users/"
    Then Users quantity should be 20

  Scenario: User number 1 from list have a name
    Given GET users from "/public-api/users/"
    Then User number 1 should have a name

  Scenario: User number 20 exist
    Given GET users from "/public-api/users/"
    Then User number 20 should exist

  Scenario: User number 21 does not exist
    Given GET users from "/public-api/users/"
    Then User number 0 shouldn't exist

  Scenario: User number 21 does not exist
    Given GET users from "/public-api/users/"
    Then User number -1 shouldn't exist


