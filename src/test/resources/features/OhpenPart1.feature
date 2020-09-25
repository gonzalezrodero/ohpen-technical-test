Feature: Ohpen technical test part 1

  Scenario: Get user's github information
    When requesting information for the user "gonzalezrodero"
    Then all the user info should appear

  Scenario: Create Registration token for a repository
    When creating a registration token for a the repo "Parking" of "gonzalezrodero"
    Then the token should be created

  Scenario: Get interaction limits for a repository
    When requesting the interaction limits for a the repo "Parking" of "gonzalezrodero"
    Then the interaction limits should be answered