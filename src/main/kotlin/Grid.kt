class Grid(width: Int, height: Int) {
    val grid = arrayOf<Array<Node>>()
    val hCost
    val gCost
    init {
        for (x in 0 until width) {
            for (y in 0 until height) {
                grid[x][y] = Node(false)
            }
        }
    }

    fun getFCost(x: Int, y: Int) {
        return
    }
}
