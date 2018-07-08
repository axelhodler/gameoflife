package co.hodler.gameoflife

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UniverseTest {

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
            ),
            UniverseTestData(
                    currentStatus = hashMapOf(
                            /**
                             * X 0 0
                             * 0 X 0
                             * X 0 0
                             */
                            Location(0, 0) to Cell(Status.ALIVE), // northwest
                            Location(0, 1) to Cell(Status.DEAD),
                            Location(0, 2) to Cell(Status.ALIVE), // southwest
                            Location(1, 0) to Cell(Status.DEAD),
                            Location(1, 1) to Cell(Status.ALIVE), // center
                            Location(1, 2) to Cell(Status.DEAD),
                            Location(2, 0) to Cell(Status.DEAD),
                            Location(2, 1) to Cell(Status.DEAD),
                            Location(2, 2) to Cell(Status.DEAD)
                    ),
                    nextStatus = hashMapOf(
                            /**
                             * 0 0 0
                             * X X 0
                             * 0 0 0
                             */
                            Location(0, 0) to Cell(Status.DEAD), // northwest
                            Location(0, 1) to Cell(Status.ALIVE), // reborn
                            Location(0, 2) to Cell(Status.DEAD), // southwest
                            Location(1, 0) to Cell(Status.DEAD), // DEAD
                            Location(1, 1) to Cell(Status.ALIVE), // center
                            Location(1, 2) to Cell(Status.DEAD), // DEAD
                            Location(2, 0) to Cell(Status.DEAD),
                            Location(2, 1) to Cell(Status.DEAD),
                            Location(2, 2) to Cell(Status.DEAD)
                    )
            ),
            UniverseTestData(
                    currentStatus = hashMapOf(
                            /**
                             * Glider
                             * X O X
                             * X X O
                             * O O O
                             */
                            Location(0, 0) to Cell(Status.DEAD), // northwest
                            Location(0, 1) to Cell(Status.DEAD),
                            Location(0, 2) to Cell(Status.ALIVE), // southwest
                            Location(1, 0) to Cell(Status.ALIVE),
                            Location(1, 1) to Cell(Status.DEAD), // center
                            Location(1, 2) to Cell(Status.ALIVE),
                            Location(2, 0) to Cell(Status.DEAD),
                            Location(2, 1) to Cell(Status.ALIVE),
                            Location(2, 2) to Cell(Status.ALIVE)
                    ),
                    nextStatus = hashMapOf(
                            /**
                             * X X X
                             * O X O
                             * X O O
                             */
                            Location(0, 0) to Cell(Status.DEAD),
                            Location(0, 1) to Cell(Status.ALIVE),
                            Location(0, 2) to Cell(Status.DEAD),
                            Location(1, 0) to Cell(Status.DEAD),
                            Location(1, 1) to Cell(Status.DEAD),
                            Location(1, 2) to Cell(Status.ALIVE),
                            Location(2, 0) to Cell(Status.DEAD),
                            Location(2, 1) to Cell(Status.ALIVE),
                            Location(2, 2) to Cell(Status.ALIVE)
                    )
            )
    )

    @ParameterizedTest
    @MethodSource("universeTickTestDataProvider")
    fun `single dead cell in universe stays dead`(testData: UniverseTestData) {
        val grid = testData.currentStatus
        val updatedUniverse = Universe(grid).tick()
        assertThat(updatedUniverse.grid).isEqualTo(testData.nextStatus)
    }

    data class UniverseTestData(
            val currentStatus: HashMap<Location, Cell>,
            val nextStatus: HashMap<Location, Cell>
    )
}

