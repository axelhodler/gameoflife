package co.hodler.gameoflife

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GameOfLifeTest {

    private fun survivingRulesTestDataProvider() = Stream.of(
            CellTestData(currentStatus = Status.ALIVE, neighborCount = 0, nextStatus = Status.DEAD, message = "dies by lonliness"),
            CellTestData(currentStatus = Status.ALIVE, neighborCount = 1, nextStatus = Status.DEAD, message = "dies by lonliness"),
            CellTestData(currentStatus = Status.ALIVE, neighborCount = 2, nextStatus = Status.ALIVE, message = "stays alive"),
            CellTestData(currentStatus = Status.ALIVE, neighborCount = 3, nextStatus = Status.ALIVE, message = "stays alive"),
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

    private fun universeTickTestDataProvider() = Stream.of(
            UniverseTestData(
                    currentStatus = hashMapOf(Location(0, 0) to Cell(Status.DEAD)),
                    nextStatus = hashMapOf(Location(0, 0) to Cell(Status.DEAD))
            ),
            UniverseTestData(
                    currentStatus = hashMapOf(Location(0, 0) to Cell(Status.ALIVE)),
                    nextStatus = hashMapOf(Location(0, 0) to Cell(Status.DEAD))
            ),
            UniverseTestData(
                    currentStatus = hashMapOf(
                            Location(0, 0) to Cell(Status.ALIVE),
                            Location(1, 0) to Cell(Status.ALIVE),
                            Location(2, 0) to Cell(Status.ALIVE)
                    ),
                    nextStatus = hashMapOf(
                            Location(0, 0) to Cell(Status.DEAD),
                            Location(1, 0) to Cell(Status.ALIVE),
                            Location(2, 0) to Cell(Status.DEAD)
                    )
            ),
            UniverseTestData(
                    currentStatus = hashMapOf(
                            Location(0, 0) to Cell(Status.ALIVE),
                            Location(1, 0) to Cell(Status.ALIVE),
                            Location(0, 1) to Cell(Status.ALIVE),
                            Location(1, 1) to Cell(Status.ALIVE)
                    ),
                    nextStatus = hashMapOf(
                            Location(0, 0) to Cell(Status.ALIVE),
                            Location(1, 0) to Cell(Status.ALIVE),
                            Location(0, 1) to Cell(Status.ALIVE),
                            Location(1, 1) to Cell(Status.ALIVE)
                    )
            )
    )

    @ParameterizedTest
    @MethodSource("universeTickTestDataProvider")
    fun `single dead cell in universe stays dead`(testData: UniverseTestData) {
        val grid = testData.currentStatus
        val universe = Universe(grid)
        universe.tick()
        assertThat(universe.grid).isEqualTo(testData.nextStatus)
    }

    data class CellTestData(
            val message: String,
            val currentStatus: Status,
            val neighborCount: Int,
            val nextStatus: Status
    )

    data class UniverseTestData(
            val currentStatus: HashMap<Location, Cell>,
            val nextStatus: HashMap<Location, Cell>
    )
}

enum class Status {
    DEAD,
    ALIVE
}

class Universe(var grid: Map<Location, Cell>) {
    fun tick() {
        grid = grid.mapValues {
            it.value.evolveWithNeighborCount(it.key.getNeighborLocations()
                    .map { grid.getOrDefault(it, Cell(Status.DEAD)) }
                    .count { it.status.equals(Status.ALIVE) })
        }
    }
}

data class Location(val x: Int, val y: Int) {
    fun getNeighborLocations(): List<Location> {
        return listOf(
                Location(x - 1, y), // western neighbor
                Location(x + 1, y), // eastern neighbor
                Location(x, y + 1), // northern neighbor
                Location(x, y - 1) // southern neighbor
        )
    }
}

data class Cell(val status: Status) {
    fun evolveWithNeighborCount(neighborCount: Int): Cell {
        return if (isLonely(neighborCount) || isOverpopulated(neighborCount)) Cell(Status.DEAD) else Cell(Status.ALIVE)
    }

    private fun isOverpopulated(neighborCount: Int) = neighborCount > 3

    private fun isLonely(neighborCount: Int) = neighborCount < 2
}
