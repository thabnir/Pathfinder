class Grid(gridSizeX: Int, gridSizeY: Int) {
    var grid: Array<Array<Node>>

    init {
        grid = Array(gridSizeY) { x ->
            Array(gridSizeX) { y ->
                Node(x, y, false)
            }
        }
    }

    fun getFCost(x: Int, y: Int) {
        return
    }

    fun getGCost() {
        return
    }

    fun getHCost() {
        return
    }

    data class Node(val x: Int, val y: Int, var walkable: Boolean)
}