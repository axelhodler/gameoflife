package co.hodler.gameoflife

fun main(args: Array<String>) {
    val initialGrid = hashMapOf(
            Location(0, 0) to Cell(Status.ALIVE),
            Location(0, 1) to Cell(Status.DEAD),
            Location(0, 2) to Cell(Status.ALIVE),
            Location(1, 0) to Cell(Status.ALIVE),
            Location(1, 1) to Cell(Status.ALIVE),
            Location(1, 2) to Cell(Status.DEAD),
            Location(2, 0) to Cell(Status.ALIVE),
            Location(2, 1) to Cell(Status.DEAD),
            Location(2, 2) to Cell(Status.ALIVE)
    )
    val universe = Universe(initialGrid)

    var currentUniverse = universe
    for (i in 1..10) {
        Thread.sleep(1000)
        currentUniverse = currentUniverse.tick()
        println(RenderedUniverse(currentUniverse).render())
        println("----")
    }
}
