package co.hodler.gameoflife

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ATest {
    @Test
    fun `dead cell is reborn if it has three neighbors`() {
        val cell = Cell(Status.DEAD)
        val evolvedCell = cell.evolveWithNeighborCount(3)
        assertThat(evolvedCell.status).isEqualTo(Status.ALIVE)
    }

    @Test
    fun `alive cell with one neighbor dies in next generation`() {
        val cell = Cell(Status.ALIVE)
        val evolvedCell = cell.evolveWithNeighborCount(1)
        assertThat(evolvedCell.status).isEqualTo(Status.DEAD)
    }

    @Test
    fun `alive cell with two neighbors stays alive`() {
        val cell = Cell(Status.ALIVE)
        val evolvedCell = cell.evolveWithNeighborCount(2)
        assertThat(evolvedCell.status).isEqualTo(Status.ALIVE)
    }
}

enum class Status {
    DEAD,
    ALIVE
}

class Cell(val status: Status) {
    fun evolveWithNeighborCount(neighborCount: Int): Cell {
        return if (neighborCount == 1) Cell(Status.DEAD) else Cell(Status.ALIVE)
    }
}
