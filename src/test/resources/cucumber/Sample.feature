Feature: Sample

  Scenario: Run App with properties
    Given Properties:
      | db.driverClassName=org.h2.Driver |
      | db.url=jdbc:h2:mem:devdb         |
      | area.limit=400000                |
      | input.dir=${in.dir}              |
      | output.dir=${out.dir}            |

    Given Table Country is empty
    Given File countries.csv in folder in with lines:
      | name, capital, currency, region, area |
      | Ukraine, Kyiv, UAH, Europe, 603700    |
      | Germany, Berlin, EUR, Europe, 357021  |
      | France, Paris, EUR, Europe, 643801    |
    Given Table Product with records:
      | id | name       | description                              | price  | insertDate | lastUpdateTime |
      | 1  | Mi Band 1s | Fitness band from Xiaomi, 1nd generation | 949.99 | 2016-05-20 | 2016-11-05     |
      | 2  | Mi Band 2  | Fitness band from Xiaomi, 2nd generation | 429.00 | 2015-02-22 | 2016-11-06     |
    When Application TestApp run with params: hello
    Then Table Country will have records:
      | name    | capital | currency | region | area   |
      | Ukraine | Kyiv    | UAH      | Europe | 603700 |
      | France  | Paris   | EUR      | Europe | 643801 |
    #Then File products.csv in folder out will have lines:
    #  | id,name,description,price,insertDate,lastUpdateTime                                                        |
    #  | 1,Mi Band 1s,"Fitness band from Xiaomi, 1nd generation",949.99,2016-05-20 00:00:00.0,2016-11-05 00:00:00.0 |
    #  | 2,Mi Band 2,"Fitness band from Xiaomi, 2nd generation",429.0,2016-05-20 00:00:00.0,2016-11-05 00:00:00.0   |

