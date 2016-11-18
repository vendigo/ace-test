# TestApp

## [Launch testApp](-)

Property file: [test.properties](- "#fileName")

| Properties:                                 |
|---------------------------------------------|
| [db.driverClassName=org.h2.Driver][addLine] |
| [db.url=jdbc:h2:mem:devdb][addLine]         |
| [age.limit=30][addLine]                     |

[fileName]: - "#fileName"
[addLine]: - "fileLine(#fileName, #TEXT)"

[ ](- "createPropertyFile(#fileName)")

When [TestApp](- "#appName") with parameters: [hello](- "#params") [runs](- "runApplication(#appName, #params)").
Then everything is OK.