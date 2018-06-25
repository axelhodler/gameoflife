package co.hodler.gameoflife

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ATest {

    private fun testDataProvider() = Stream.of(
            CellTestData(currentStatus = Status.DEAD, neighborCount = 3, nextStatus = Status.ALIVE),
            CellTestData(currentStatus = Status.ALIVE, neighborCount = 1, nextStatus = Status.DEAD),
            CellTestData(currentStatus = Status.ALIVE, neighborCount = 2, nextStatus = Status.ALIVE)
    )

    @ParameterizedTest
    @MethodSource("testDataProvider")
    fun `follows transition rules`(testData: CellTestData) {
        val cell = Cell(testData.currentStatus)
        val evolvedCell = cell.evolveWithNeighborCount(testData.neighborCount)
        assertThat(evolvedCell.status).isEqualTo(testData.nextStatus)
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
