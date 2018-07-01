package co.hodler.gameoflife

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ATest {

    private fun testDataProvider() = Stream.of(
            CellTestData(currentStatus = Status.ALIVE, neighborCount = 0, nextStatus = Status.DEAD, message = "dies by lonliness"),
            CellTestData(currentStatus = Status.ALIVE, neighborCount = 1, nextStatus = Status.DEAD, message = "dies by lonliness"),
            CellTestData(currentStatus = Status.ALIVE, neighborCount = 2, nextStatus = Status.ALIVE, message = "stays alive"),
            CellTestData(currentStatus = Status.ALIVE, neighborCount = 3, nextStatus = Status.ALIVE, message = "stays alive"),
            CellTestData(currentStatus = Status.DEAD, neighborCount = 3, nextStatus = Status.ALIVE, message = "is reborn"),
            CellTestData(currentStatus = Status.ALIVE, neighborCount = 4, nextStatus = Status.DEAD, message = "dies by overpopulation"),
            CellTestData(currentStatus = Status.ALIVE, neighborCount = 5, nextStatus = Status.DEAD, message = "dies by overpopulation")
    )

    @ParameterizedTest
    @MethodSource("testDataProvider")
    fun `follows transition rules`(testData: CellTestData) {
        val cell = Cell(testData.currentStatus)
        val evolvedCell = cell.evolveWithNeighborCount(testData.neighborCount)
        assertThat(evolvedCell.status)
                .`as`(testData.message)
                .isEqualTo(testData.nextStatus)
    }

    data class CellTestData(
            val message: String,
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
        return if (isLonely(neighborCount) || isOverpopulated(neighborCount)) Cell(Status.DEAD) else Cell(Status.ALIVE)
    }

    private fun isOverpopulated(neighborCount: Int) = neighborCount > 3

    private fun isLonely(neighborCount: Int) = neighborCount < 2
}
