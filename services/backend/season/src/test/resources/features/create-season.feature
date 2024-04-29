Feature: Create seasons
  As an admin user,
  I want to add seasons,
  So that they can be used to organize matches.

  Scenario: Add a new season
    Given I am an admin user
    When I add a new season
    Then a new season is created in the system
    And I see the newly created season on the seasons' page

  Scenario: Add an existing season
    Given I am an admin user
    When I add an existing season
    Then I see an error message