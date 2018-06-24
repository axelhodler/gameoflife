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
}

enum class Status {
    DEAD,
    ALIVE
}

class Cell(val status: Status) {
    fun evolveWithNeighborCount(neighborCount: Int): Cell {
        return Cell(Status.ALIVE)
    }
}
