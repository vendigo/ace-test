Feature: Sample

  Scenario: Run App with properties
    Given Properties:
      | db.driverClassName=org.h2.Driver |
      | db.url=jdbc:h2:mem:devdb         |
      | age.limit=30                     |
    When Application TestApp run with params: hello