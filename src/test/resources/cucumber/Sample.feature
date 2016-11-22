Feature: Sample

  Scenario: Run App with properties
    Given Properties:
      | db.driverClassName=org.h2.Driver |
      | db.url=jdbc:h2:mem:devdb         |
      | age.limit=65                     |
      | input.dir=${in.dir}              |
      | out.dir=${out.dir}               |

    Given Table TOldUser is empty
    Given Table TUser with records:
      | name    | age |
      | Dima    | 24  |
      | Natasha | 23  |
      | Petya   | 64  |
      | Vasya   | 81  |
      | Vova    | 66  |
    Given Table Country with records:
      | name    | capital | currency | region | area   |
      | Ukraine | Kyiv    | UAH      | Europe | 603700 |
    Given Table Product with records:
      | id | name       | description                              | price  | insertDate | lastUpdateTime |
      | 1  | Mi Band 1s | Fitness band from Xiaomi, 1nd generation | 949.99 | 2016-05-20 | 2016-11-05     |
      | 2  | Mi Band 2  | Fitness band from Xiaomi, 2nd generation | 429.00 | 2015-02-22 | 2016-11-06     |
    When Application TestApp run with params: hello
    Then Table TOldUser will have records:
      | name  | age |
      | Vasya | 81  |
      | Vova  | 66  |
