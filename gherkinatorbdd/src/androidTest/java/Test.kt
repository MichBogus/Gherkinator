import com.codefirst.bdd.gherkinatorannotation.Scenario
import com.codefirst.bdd.gherkinatorbdd.GherkinatorRobot
import com.codefirst.bdd.gherkinatorbdd.v2.GherkinatorEngine

class Test : GherkinatorEngine() {

    override var robots: MutableList<GherkinatorRobot>? = mutableListOf()

    override fun featureFileLocation(): String = "features/test.feature"

    @Scenario("test name of scenario")
    fun foo() {

    }
}