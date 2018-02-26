Feature: Test login view

  Background:
    Given: Insert default values
    And: Insert stuff to database

  Scenario: This is first test to check if progress view is visible after pressing login button

    Given: Open Login view
    When: Pressing login button
    Then: I can see progress view