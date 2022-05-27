fun main() {
    val grid = Grid(8, 4)
    grid.nodes[3][3].crossable = false
    grid.nodes[2][3].crossable = false
    grid.nodes[1][1].crossable = false
    grid.nodes[1][0].crossable = false
    grid.nodes[0][6].crossable = false

    val list = grid.findPath(grid.nodes[0][0], grid.nodes[3][0])
    for (array in grid.nodes) {
        for (node in array) {
            if (list.contains(node)) {
                print("[    ] ")
            } else if (node.crossable == false) {
                print("++++++ ")
            } else {
                print("(${node.x}, ${node.y}) ")
            }
        }
        println()
    }
}