Feature:
  POST operations - Users to /public-api/users/

  Scenario: Create new user
    Given User name is "Test", user mail is "test1@test.dev", user gender is "male", user status is "active"
    Then User has created and have id
    Then Search user by id and check his fields: "name", "mail", "gender", "status"