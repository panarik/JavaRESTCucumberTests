Feature:
  POST/UPDATE/DELETE operations with users

  Scenario: Create new user and check fields created user
    Given Create new user with fields: name is "Test", user mail is "test1@test.dev", user gender is "male", user status is "active"
    Then Search user by current id and compare his fields with created user

  Scenario: Update user and check his name
    Given Create new user with fields: name is "Test", user mail is "test1@test.dev", user gender is "male", user status is "active"
    Given Update current user name with "Test Updated"
    Then Current user name should be "Test Updated"

  Scenario: Delete user and check this user has been deleted
    Given Create new user with fields: name is "Test", user mail is "test1@test.dev", user gender is "male", user status is "active"
    Then Delete current user
    Then Current user shouldn't be exist