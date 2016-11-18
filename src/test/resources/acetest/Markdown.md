# TestApp

## [Launch testApp](-)

Property file: [test.properties](- "#fileName")

| Properties:                  |
|------------------------------|
| [user.name=vendigo][addLine] |

[fileName]: - "#fileName"
[addLine]: - "fileLine(#fileName, #TEXT)"

[ ](- "createPropertyFile(#fileName)")

When [TestApp](- "#appName") with parameters: [hello](- "#params") [runs](- "runApplication(#appName, #params)").
Then everything is OK.