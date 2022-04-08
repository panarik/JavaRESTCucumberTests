Feature:
  GET operations - Users from /public/v2

  Scenario: Score users
    Given GET users from "/public-api/users/"
    Then Users quantity should be 20

  Scenario: User 0 from list have a name
    Given GET users from "/public-api/users/"
    Then User number 0 should have a name
