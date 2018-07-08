package co.hodler.gameoflife

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CellTest {

    private fun survivingRulesTestDataProvider() = Stream.of(
            CellTestData(currentStatus = Status.ALIVE, neighborCount = 0, nextStatus = Status.DEAD, message = "dies by lonliness"),
            CellTestData(currentStatus = Status.ALIVE, neighborCount = 1, nextStatus = Status.DEAD, message = "dies by lonliness"),
            CellTestData(currentStatus = Status.ALIVE, neighborCount = 2, nextStatus = Status.ALIVE, message = "stays alive"),
            CellTestData(currentStatus = Status.ALIVE, neighborCount = 3, nextStatus = Status.ALIVE, message = "stays alive"),
            CellTestData(currentStatus = Status.DEAD, neighborCount = 2, nextStatus = Status.DEAD, message = "stays dead"),
            CellTestData(currentStatus = Status.DEAD, neighborCount = 3, nextStatus = Status.ALIVE, message = "is reborn"),
            CellTestData(currentStatus = Status.ALIVE, neighborCount = 4, nextStatus = Status.DEAD, message = "dies by overpopulation"),
            CellTestData(currentStatus = Status.ALIVE, neighborCount = 5, nextStatus = Status.DEAD, message = "dies by overpopulation")
    )

    @ParameterizedTest
    @MethodSource("survivingRulesTestDataProvider")
    fun `a cell follows transition rules`(testData: CellTestData) {
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