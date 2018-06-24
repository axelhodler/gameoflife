package co.hodler.gameoflife

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ATest {

    private fun testDataProvider() = Stream.of(
            CellTestData(currentStatus = Status.DEAD, neighborCount = 3, nextStatus = Status.ALIVE)
    )

    @ParameterizedTest
    @MethodSource("testDataProvider")
    fun `dead cell is reborn if it has three neighbors`(testData: CellTestData) {
        val cell = Cell(testData.currentStatus)
        val evolvedCell = cell.evolveWithNeighborCount(testData.neighborCount)
        assertThat(evolvedCell.status).isEqualTo(testData.nextStatus)
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

    data class CellTestData(
            val currentStatus: Status,
            val neighborCount: Int,
            val nextStatus: Status
    )
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
