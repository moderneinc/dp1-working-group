package io.moderne.dp1

import org.junit.jupiter.api.Test
import org.openrewrite.Recipe
import org.openrewrite.java.JavaRecipeTest

class AddWebclientFactoryTest : JavaRecipeTest {
    override val recipe: Recipe
        get() = AddWebclientFactory()

    @Test
    fun addWebclientFactoryToEmptyConstructor() = assertChanged(
        dependsOn = arrayOf("""
            package dp1;
            public class WebclientFactory {
            }
        """),
        before = """
            class Test {
                public Test() {
                }
            }
        """,
        after = """
            import dp1.WebclientFactory;
            
            class Test {
                public Test(WebclientFactory webclientFactory) {
                }
            }
        """
    )
}
