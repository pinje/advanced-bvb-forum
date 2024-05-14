Feature: Get all seasons
  As an guest user,
  I want to see the current and past seasons,
  So that I can see past matches organized into seasons.

  Scenario: Get all seasons
    Given I am a guest user
    When I go to the seasons' page
    Then I see current and past seasons
    And I see past matches organized into seasons