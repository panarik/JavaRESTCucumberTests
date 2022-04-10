Feature:
  POST operations - Users to /public-api/users/

  Scenario: Create new user and check fields created user
    Given User name is "Test", user mail is "test1@test.dev", user gender is "male", user status is "active"
    Then User has created and have id
    Then Search user by this id and compare his fields with created user

  Scenario: Update created user
    Given Update current user name "Test Updated"
    Then Current user name should be "Test Updated"

  Scenario: Delete created user
    Given Delete current user
    Then Current user shouldn't be exist