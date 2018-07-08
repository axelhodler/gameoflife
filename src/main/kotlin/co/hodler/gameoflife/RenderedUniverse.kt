package co.hodler.gameoflife

class RenderedUniverse(val universe: Universe) {
    fun render(): String {
        val range = 2
        var rendered = ""
        for (row in 0..range) {
            for (column in 0..range) {
                if (universe.grid.getOrDefault(Location(column, row), Cell(Status.DEAD)).status.equals(Status.ALIVE))
                    rendered += "O"
                else
                    rendered += "#"
                if (column == range && row != range)
                    rendered += "\n"
            }
        }
        return rendered
    }

}