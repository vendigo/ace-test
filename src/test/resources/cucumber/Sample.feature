Feature: Sample

  Scenario: Population of product.csv and country filtering
    Given Properties:
      | db.driverClassName=org.h2.Driver |
      | db.url=jdbc:h2:mem:devdb         |
      | area.limit=400000                |
      | input.dir=${in.dir}              |
      | output.dir=${out.dir}            |
    Given Table Country is empty
    Given File countries.csv in folder in with lines:
      | name, capital, currency, region, area, independenceDay |
      | Ukraine, Kyiv, UAH, Europe, 603700, 1991-08-24         |
      | Germany, Berlin, EUR, Europe, 357021,                  |
      | France, Paris, EUR, Europe, 643801,                    |
    Given Table Product with records:
      | id | name                       | description                              | price  | insertDate | lastUpdateTime   |
      | 1  | Mi Band 1s                 | Fitness band from Xiaomi, 1nd generation | 949.99 | 2016-05-20 | 2016-11-05 20:00 |
      | 2  | Mi Band 2                  | Fitness band from Xiaomi, 2nd generation | 429.00 | 2015-02-22 | 2016-11-06 16:30 |
      | 3  | Xiaomi Huami Amazfit Watch | <empty>                                  | 4499   | 2016-11-02 | 2016-11-02 10:35 |
    When Application TestApp runs
    Then Table Country will have records:
      | name    | capital | currency | region | area   | independenceDay |
      | Ukraine | Kyiv    | UAH      | Europe | 603700 | 1991-08-24      |
      | France  | Paris   | EUR      | Europe | 643801 | <null>          |
    Then Folder out will have files: products.csv
    Then File products.csv in folder out will have lines:
      | id,name,description,price,insertDate,lastUpdateTime                                        |
      | 1,Mi Band 1s,"Fitness band from Xiaomi, 1nd generation",949.99,2016-05-20,2016-11-05 20:00 |
      | 2,Mi Band 2,"Fitness band from Xiaomi, 2nd generation",429.00,2015-02-22,2016-11-06 16:30  |
      | 3,Xiaomi Huami Amazfit Watch,,4499.00,2016-11-02,2016-11-02 10:35                          |

  Scenario: Ignoring area limit
    Given Properties:
      | db.driverClassName=org.h2.Driver |
      | db.url=jdbc:h2:mem:devdb         |
      | area.limit=400000                |
      | input.dir=${in.dir}              |
      | output.dir=${out.dir}            |
    Given Table Country is empty
    Given File countries.csv in folder in with lines:
      | name, capital, currency, region, area, independenceDay |
      | Ukraine, Kyiv, UAH, Europe, 603700, 1991-08-24         |
      | Germany, Berlin, EUR, Europe, 357021,                  |
      | France, Paris, EUR, Europe, 643801,                    |
    When Application TestApp runs with params: '-disableAreaLimit'
    Then Table Country will have records:
      | name    | capital | currency | region | area   | independenceDay |
      | Ukraine | Kyiv    | UAH      | Europe | 603700 | 1991-08-24      |
      | Germany | Berlin  | EUR      | Europe | 357021 | <null>          |
      | France  | Paris   | EUR      | Europe | 643801 | <null>          |

  Scenario: Too many parameters
    When Application TestApp runs with params: '-disableAreaLimit -anotherParam', it fails with exception: Too many parameters