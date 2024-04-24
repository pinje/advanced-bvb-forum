Feature: Authentication
  As a guest user,
  I want to authentication to the website,
  So that I can access to the system's member's functionalities

  Scenario: Login with correct credentials
    Given I am a guest user
    When I login with correct credentials
    Then I am logged in
    And I am redirected to the home page

  Scenario: Login with incorrect credentials
    Given I am a guest user
    When I login with incorrect credentials
    Then I see an error message