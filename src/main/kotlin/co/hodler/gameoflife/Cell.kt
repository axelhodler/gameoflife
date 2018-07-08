package co.hodler.gameoflife

data class Cell(val status: Status) {
    fun evolveWithNeighborCount(neighborCount: Int): Cell {
        return if (isReborn(neighborCount) || neitherOverpopulatedNorLonely(neighborCount)) Cell(Status.ALIVE) else Cell(Status.DEAD)
    }

    private fun neitherOverpopulatedNorLonely(neighborCount: Int) = status.equals(Status.ALIVE) && (neighborCount == 2 || neighborCount == 3)

    private fun isReborn(neighborCount: Int) = status.equals(Status.DEAD) && neighborCount == 3
}

enum class Status {
    DEAD,
    ALIVE
}