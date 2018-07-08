package co.hodler.gameoflife

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RenderedUniverseTest {

    private fun renderingTestData() = Stream.of(
            UniverseRenderingTestData(
                    grid = hashMapOf(
                            Location(0, 0) to Cell(Status.ALIVE),
                            Location(0, 1) to Cell(Status.DEAD),
                            Location(0, 2) to Cell(Status.ALIVE),
                            Location(1, 0) to Cell(Status.DEAD),
                            Location(1, 1) to Cell(Status.ALIVE),
                            Location(1, 2) to Cell(Status.DEAD),
                            Location(2, 0) to Cell(Status.ALIVE),
                            Location(2, 1) to Cell(Status.DEAD),
                            Location(2, 2) to Cell(Status.ALIVE)
                    ),
                    rendered = """
            O#O
            #O#
            O#O
                    """.trimIndent()
            ),
            UniverseRenderingTestData(
                    grid = hashMapOf(
                            Location(0, 0) to Cell(Status.DEAD),
                            Location(0, 1) to Cell(Status.ALIVE),
                            Location(0, 2) to Cell(Status.DEAD),
                            Location(1, 0) to Cell(Status.ALIVE),
                            Location(1, 1) to Cell(Status.DEAD),
                            Location(1, 2) to Cell(Status.ALIVE),
                            Location(2, 0) to Cell(Status.DEAD),
                            Location(2, 1) to Cell(Status.ALIVE),
                            Location(2, 2) to Cell(Status.DEAD)
                    ),
                    rendered = """
            #O#
            O#O
            #O#
                    """.trimIndent()
            )
    )

    @ParameterizedTest
    @MethodSource("renderingTestData")
    fun `renders the universe`(testData: UniverseRenderingTestData) {
        val renderedUniverse = RenderedUniverse(Universe(testData.grid)).render();

        assertThat(renderedUniverse).isEqualTo(testData.rendered)
    }

    data class UniverseRenderingTestData(
            val grid: HashMap<Location, Cell>,
            val rendered: String
    )
}

class RenderedUniverse(val universe: Universe) {
    fun render(): String {
        val range = 2
        var rendered = ""
        for (row in 0..range) {
            for (column in 0..range) {
                if (universe.grid.getOrDefault(Location(column, row), Cell(Status.DEAD)).status.equals(Status.ALIVE))
                    rendered += "O"
                else
                    rendered += "#"
                if (column == range && row != range)
                    rendered += "\n"
            }
        }
        return rendered
    }

}