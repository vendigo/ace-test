Feature: Sample

  Scenario: Run App with properties
    Given Properties:
      | db.driverClassName=org.h2.Driver |
      | db.url=jdbc:h2:mem:devdb         |
      | age.limit=65                     |
    Given Table TOldUser is empty
    Given Table TUser with records:
      | name    | age |
      | Dima    | 24  |
      | Natasha | 23  |
      | Petya   | 64  |
      | Vasya   | 81  |
      | Vova    | 66  |
    When Application TestApp run with params: hello
    Then Table TOldUser will have records:
      | name  | age |
      | Vasya | 81  |
      | Vova  | 66  |
