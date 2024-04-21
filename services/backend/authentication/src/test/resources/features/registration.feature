Feature: Registration
  As a guest user,
  I want to register to the website,
  So that I can access to the system's member's functionalities

  Scenario: Create a new user
    Given I am a guest user
    When I register with valid credentials
    Then I am redirected to the login page

  Scenario: Create a new user with existing email
    Given a user exists in the system
    When I register with the same email as the existing user
    Then I see an error message

  Scenario: Create a new user with existing username
    Given a user exists in the system
    When I register with the same username as the existing user
    Then I see an error message

  Scenario: Create a new user with invalid credentials
    Given I am a guest user
    When I register with invalid credentials
    Then I see an error message

  Scenario: Create a new user with empty credentials
    Given I am a guest user
    When I register with empty credentials
    Then I see an error message
