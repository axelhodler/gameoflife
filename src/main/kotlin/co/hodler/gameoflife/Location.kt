package co.hodler.gameoflife

data class Location(val x: Int, val y: Int) {
    fun getNeighborLocations(): List<Location> {
        return listOf(
                Location(x - 1, y), // western neighbor
                Location(x + 1, y), // eastern neighbor
                Location(x, y + 1), // northern neighbor
                Location(x - 1, y + 1), // northwestern neighbor
                Location(x + 1, y + 1), // northeaster neighbor
                Location(x, y - 1), // southern neighbor
                Location(x - 1, y - 1), // southwestern neighbor
                Location(x + 1, y - 1) // southeastern neighbor
        )
    }
}