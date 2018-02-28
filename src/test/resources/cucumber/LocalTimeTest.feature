Feature: Local time test

  Background:
    Given Properties:
      | db.driverClassName=org.h2.Driver |
      | db.url=jdbc:h2:mem:devDb         |
      | input.dir=${in.dir}              |
      | output.dir=${out.dir}            |

  Scenario: Dummy DB insertion and fetch to see if LocalTime is handled correctly
    Given Table LocalTimeTest with records:
        | country | localTime |
        | UA      | 17:28:13  |
        | UK      | 15:28:13  |
        | US      | 10:28:13  |

    Then Table LocalTimeTest will have records:
        | country | localTime |
        | UA      | 17:28:13  |
        | UK      | 15:28:13  |
        | US      | 10:28:13  |