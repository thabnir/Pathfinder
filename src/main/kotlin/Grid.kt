import kotlin.math.abs

class Grid(gridSizeX: Int, gridSizeY: Int) {
    var nodes: Array<Array<Node>>

    init {
        nodes = Array(gridSizeY) { y ->
            Array(gridSizeX) { x ->
                Node(x, y, true)
            }
        }
    }

    fun getNeighbors(node: Node, searchRadius: Int): ArrayList<Node> {
        var neighbors = arrayListOf<Node>()

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
            return 14 * distY + 10 * (distY - distX)
        }
        return 14 * distX + 10 * (distY - distX)
    }

    fun retracePath(startNode: Node, targetNode: Node): ArrayList<Node> {
        val path = arrayListOf<Node>()
        var currentNode = targetNode

        while (currentNode != startNode) {
            path.add(currentNode)
            currentNode = currentNode.parent!!
        }
        //path.reverse()
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