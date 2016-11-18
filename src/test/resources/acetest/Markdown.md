# TestApp

## [Launch testApp](-)

| [ ][addProperty] [Properties][line] |
|-------------------------------------|
| db.driverClassName=org.h2.Driver    |
| db.url=jdbc:h2:mem:devdb            |
| age.limit=30                        |

[line]: - "#line"
[addProperty]: - "addProperty(#line)"

[ ](- "setProperties()")

When [TestApp](- "#appName") with parameters: '[hello](- "#params")' [runs](- "runApplication(#appName, #params)").
Then everything is OK.