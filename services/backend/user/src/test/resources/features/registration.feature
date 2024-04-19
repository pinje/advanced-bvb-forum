Feature: Registration
  As a guest user,
  I want to register to the website,
  So that I can access to the system's member's functionalities

  Scenario: Create a new user
    Given I am a guest user with email "user@gmail.com"
    When I register with email "user@gmail.com" and username "user123" and password "123"
    Then I am redirected to the login page

#  Scenario: Create a new user with existing email
#    Given the list of users contains a user with email "user@gmail.com"
#    When I create a new user with email "user@gmail.com"
#    Then I should see an error message indicating the email is already in use
#
#  Scenario: Create a new user with existing username
#    Given the list of users contains a user with username "user123"
#    When I create a new user with username "user123"
#    Then I should see an error message indicating the username is already in use
