Feature:
  GET operations - Users name.

  Scenario: Verify name 'user 1'
    Given GET user name for "/public/v2/users"
    And Give user "1"
    Then User 1 name is "Menka Pilla"
