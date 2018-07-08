package co.hodler.gameoflife

class Universe(var grid: Map<Location, Cell>) {
    fun tick(): Universe {
        val updatedGrid = grid.mapValues {
            it.value.evolveWithNeighborCount(it.key.getNeighborLocations()
                    .map { grid.getOrDefault(it, Cell(Status.DEAD)) }
                    .count { it.status.equals(Status.ALIVE) })
        }
        return Universe(updatedGrid);
    }
}