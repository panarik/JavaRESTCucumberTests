# Setup
1. type: `git clone https://github.com/panarik/JavaRESTCucumberTests.git`
2. put your token into ./settings.json file. Token can be used from https://gorest.co.in/my-account/access-tokens

# Features
1. GET, POST, UPDATE, DELETE requests examples.
2. HTTPS connection.
3. Positive and negative tests.
4. All tests can be run separated. After each test all test data is deleted.
5. MVC style architecture.
6. JSON parser for grabbing token and another staff.
7. Response body parser.
8. Errors handler. (401, 422, etc.)
9. Different types of assertions: String, Map, NotNull etc.

# Run
1. Run by default jUnit task. Type: `./gradlew test`
2. Run by custom Gradle task. Type: `./gradlew cucumber`