package co.hodler.gameoflife

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class LocationTest {

    @Test
    fun `location knows its neighboring locations`() {
        val location = Location(1, 1)

        assertThat(location.getNeighborLocations()).containsExactlyInAnyOrder(
                Location(0, 0),
                Location(0, 1),
                Location(0, 2),
                Location(1, 0),
                // center
                Location(1, 2),
                Location(2, 0),
                Location(2, 1),
                Location(2, 2)
        )
    }

}