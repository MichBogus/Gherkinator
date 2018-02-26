# Gherkinator

This is pure concept of library to provide BDD testing. </br>
BDD stance fo Behavioral Driven Development, which besically means that it is largely </br>
facilitated through the use of a simple domain-specific language (DSL) using natural language </br> 
constructs (e.g., English-like sentences) that can express the behavior and the expected outcomes. </br> 

Ever wonder how world would look like if you as a technical person can provide all the information </br> 
about logic in application to person who doesn't understands programming languages? </br> 

If the answer for that question is "There is no possibility of that", than you should check for better </br> 
approach in doing your tests first. </br> 
</br> 
## Tests are important! </br> 

They provides us as a programmers with necessary logic about application we are working on. They behave as a documentation. </br>

## Gherkin language as BDD best option </br>

Gherkin is all about using few keywords and typical langaugage that you use everyday (like english). </br>
Those keywords are: </br>
* Feature - this is just keyword to name your test suite (like Feature: Testing login view)</br>
* Scenario - this is keyword used to name your one test scenario (like Scenario: Check if login button show progress after pressing)</br>
* Background - this is keyword that gives predefinition to every single scenario it can only occur once in single feature file</br>
* Given - this is predefinition to your test (like Given: I insert stuff to database) </br>
* When - with this keyword you want to use something that user do to get certain behavior (like When: I click on login button)</br>
* Then - with this keyword you do any asserts for your test (like Then: I can see progress bar) </br>
* And - with this keyword you can make many predefinition, actions or asserts without constant typing of given, when or then </br>

## Using library </br>

Add dependency to your project (into project build.gradle)

```
maven { url  "https://bintray.com/codefirst/maven" }
```

And to module (into module build.gradle)
```
compile "com.mbogus.bdd:gherkinatorbdd:<current-version>"
```
current-version = 1.0

1. Creating feature file for your test class (this file should be located in androidTest/assets/features/<filename.feature></br>

```Gherkin
Feature: Test login view

  Background:
    Given: Insert default values
    And: Insert stuff to database

  Scenario: This is first test to check if progress view is visible after pressing login button

    Given: Open Login view
    When: Pressing login button
    Then: I can see progress view
```

2. Create test class in androidTest package

```Kotlin
@RunWith(AndroidJUnit4::class)
class TestInstrumentedTest : GherkinatorEngine(InstrumentationRegistry.getTargetContext()) {

    override fun featureFileLocation(): String = "features/test.feature"

    @Before
    fun setUp() {
        robots = mutableListOf(DatabaseRobot(), LoginViewRobot())
    }

    @Test
    @ScenarioTest("This is first test to check if progress view is visible after pressing login button")
    fun thisIsTestRun() {
        runScenario()
    }
}
```

3. Create many test robots 

```Kotlin
class LoginViewRobot : GherkinatorRobot() {

    @Given("Open Login view")
    fun openLoginViewStep() {
        //activityRule from constructor
    }

    @When("Pressing login button")
    fun actionPressLoginButton() {
        //espresso onView
    }

    @Then("I can see progress view")
    fun assertVisiiblityOfProgressView(nameOfDrugstore: String) {
        //espresso onView
    }
}
```

```Kotlin
class DatabaseRobot: GherkinatorRobot() {

    @Given("Insert default values")
    fun insertDefaultValues() {
        //database insert from constructor
    }

    @And("Insert stuff to database")
    fun insertStuffToDatabase() {
        //database insert from constructor
    }
}
```

And that is it, you can now run your test class as usual.
