fun main() {
    // val grid = Grid(20, 20)
    val panel = GraphicsPanel(40, 30)

    /*
    for (x in 0 until grid.numCols - 1) {
        for (y in 1 until grid.numRows - 1) {
            if (y % 2 == 0) {
                grid.node(x, y).crossable = false
            }
        }
    }
    grid.node(30, 178).crossable = true
    val startTime = currentTimeMillis()
    println(startTime)
    val path = grid.findPath(grid.node(0, 0), grid.node(30, 178))
    val endTime = currentTimeMillis()
    val timeElapsed = (endTime - startTime).toDouble() / 1000.0

    grid.print(path)

    println("Path length: ${path.size}")
    print("Completed in $timeElapsed s")
    */

//    for (array in grid.nodes) {
//        for (node in array) {
//            print(node.fCost)
//        }
//        println()
//    }

}