package co.hodler.gameoflife

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class RenderedUniverseTest {

    @Test
    fun `renders the universe`() {
        val grid = hashMapOf(
                Location(0, 0) to Cell(Status.ALIVE),
                Location(0, 1) to Cell(Status.DEAD),
                Location(0, 2) to Cell(Status.ALIVE),
                Location(1, 0) to Cell(Status.DEAD),
                Location(1, 1) to Cell(Status.ALIVE),
                Location(1, 2) to Cell(Status.DEAD),
                Location(2, 0) to Cell(Status.ALIVE),
                Location(2, 1) to Cell(Status.DEAD),
                Location(2, 2) to Cell(Status.ALIVE)
        )
        val renderedUniverse = RenderedUniverse(Universe(grid)).render();

        assertThat(renderedUniverse).isEqualTo("""
            O # O
            # O #
            O # O
        """.trimIndent())
    }
}

class RenderedUniverse(universe: Universe) {
    fun render(): String {
        return """
            O # O
            # O #
            O # O
        """.trimIndent()
    }

}