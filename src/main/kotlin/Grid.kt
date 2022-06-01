import java.lang.Math.pow
import java.lang.Thread.sleep
import kotlin.math.abs
import kotlin.math.sqrt

class Grid(gridSizeX: Int, gridSizeY: Int) {
    var nodes: Array<Array<Node>>
    val numCols: Int
    val numRows: Int

    init {
        nodes = Array(gridSizeY) { y ->
            Array(gridSizeX) { x ->
                Node(x, y, true)
            }
        }
        this.numCols = gridSizeX
        this.numRows = gridSizeY
    }

    fun selectStartNode(x: Int, y: Int) {
        // nodes[y][x]. = true
    }

    fun print(path: List<Node>) {
        print("+")
        for (col in 0 until numCols) {
            print("---")
        }
        print("+")
        println()
        for (array in nodes) {
            print("|")
            for (node in array) {
                if (path.contains(node)) {
                    print(" . ")
                } else if (node.crossable == false) {
                    print(" x ")
                } else {
                    print("   ")
                }
            }
            println("|")
        }
        print("+")
        for (col in 0 until numCols) {
            print("---")
        }
        println("+")
    }

    fun showPath(path: ArrayList<Node>) {
        for (i in 0 until path.size) {
            print("\u001b[H\u001b[2J")
            System.out.flush()
            val pathSoFar = path.subList(0, i)
            this.print(pathSoFar)
            sleep(50)
        }
        println("Path length: ${path.size}")
    }

    fun node(x: Int, y: Int): Node {
        return nodes[y][x]
    }

    fun getNeighbors(node: Node, searchRadius: Int): ArrayList<Node> {
        val neighbors = arrayListOf<Node>()

        for (rowOffset in -searchRadius..searchRadius) {
            for (colOffset in -searchRadius..searchRadius) {
                if (colOffset == 0 && rowOffset == 0) {
                    continue
                }
                val targetCol = node.x + colOffset
                val targetRow = node.y + rowOffset
                if (targetRow >= 0 && targetRow < nodes.size && targetCol >= 0 && targetCol < nodes[targetRow].size) {
                    neighbors.add(nodes[targetRow][targetCol])
                }
            }
        }
        return neighbors
    }

    fun getNeighbors(node: Node): ArrayList<Node> {
        return getNeighbors(node, 1)
    }

    fun distanceBetween(nodeA: Node, nodeB: Node): Int {
        val distX = abs(nodeA.x - nodeB.x)
        val distY = abs(nodeA.y - nodeB.y)

        if (distX > distY) {
            return 14 * distY + 10 * (distX - distY)
        }
        return 14 * distX + 10 * (distY - distX)
    }

    fun distanceBetweenDouble(nodeA: Node, nodeB: Node): Double {
        // gives the same length paths as its integer counterpart and takes up to 500x as long
        val distX = abs(nodeA.x - nodeB.x)
        val distY = abs(nodeA.y - nodeB.y)

        return sqrt(pow(distX.toDouble(), 2.0) + pow(distY.toDouble(), 2.0))
    }

    fun retracePath(startNode: Node, targetNode: Node): ArrayList<Node> {
        val path = arrayListOf<Node>()
        var currentNode = targetNode

        while (currentNode != startNode) {
            path.add(currentNode)
            currentNode = currentNode.parent!!
        }
        path.reverse()
        return path
    }

    fun findPath(startNode: Node, targetNode: Node): ArrayList<Node> {
        val openSet = arrayListOf<Node>()
        val closedSet = hashSetOf<Node>()
        openSet.add(startNode)

        while (openSet.isNotEmpty()) {
            var node = openSet[0]
            for (i in 1 until openSet.size) {
                if (openSet[i].fCost < node.fCost || openSet[i].fCost == node.fCost
                    && openSet[i].hCost < node.hCost
                ) {
                    node = openSet[i]
                }
            }

            openSet.remove(node)
            closedSet.add(node)
            if (node == targetNode) {
                return retracePath(startNode, targetNode)
            }

            for (neighbor in getNeighbors(node)) {
                if (!neighbor.crossable || closedSet.contains(neighbor)) {
                    continue
                }
                val newCostToNeighbor = node.gCost + distanceBetween(node, neighbor)
                if (newCostToNeighbor < neighbor.gCost || !openSet.contains(neighbor)) {
                    neighbor.gCost = newCostToNeighbor
                    neighbor.hCost = distanceBetween(neighbor, targetNode)
                    neighbor.parent = node

                    if (!openSet.contains(neighbor)) {
                        openSet.add(neighbor)
                    }
                }
            }
        }
        val a = arrayListOf<Node>()
        return a
    }

    data class Node(val x: Int, val y: Int, var crossable: Boolean) {
        val fCost: Int
            get() = hCost + gCost
        var hCost: Int = 0
        var gCost: Int = 0
        var parent: Node? = null
    }
}