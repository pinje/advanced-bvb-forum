Feature: Delete seasons
  As an admin user,
  I want to remove seasons,
  So that I can remove seasons if I made a mistake.

  Scenario: Remove a season
    Given I am an admin user
    When I remove a season
    Then the season is removed from the system
    And I do not see the season on the seasons' page