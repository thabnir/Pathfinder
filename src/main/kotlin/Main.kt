import java.lang.System.currentTimeMillis

fun main() {
    val grid = Grid(4000, 4000)

    for (x in 0 until grid.numCols - 1) {
        for (y in 1 until grid.numRows - 1) {
            if (y % 2 == 0) {
                grid.node(x, y).crossable = false
            }
        }
    }
    grid.node(0, 3971).crossable = true
    val t1 = currentTimeMillis()
    println(t1)
    val path = grid.findPath(grid.node(0, 0), grid.node(0, 3971))
    val t2 = currentTimeMillis()
    val t3 = (t2 - t1).toDouble() / 1000.0

    //grid.print(path)

    println("Path length: ${path.size}")
    print("Completed in $t3 s")

//    for (array in grid.nodes) {
//        for (node in array) {
//            print(node.fCost)
//        }
//        println()
//    }

}