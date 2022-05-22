fun main() {
    val a = Grid(5, 5)


}

fun findPath(source: Node, target: Node) {
    val open = ArrayList<Node>()
    val closed = HashSet<Node>()
    open.add(source)
    while (open.isNotEmpty()) {

    }
    /*
    open // set of nodes to be evaluated
    closed // set of nodes already evaluated
    add start node to open

    loop
        current = node in open with lowest f_cost
        remove current from open
        add current to closed

        if current is the target node // path has been found
            return
        foreach neighbor of the current node
            if neighbor is not traversable or neighbor is in closed
                skip to the next neighbor
            if new path to neighbor is shorter OR neighbor is not in open
                set f_cost of neighbor
                set parent of neighbor to current
                if neighbor is not in open
                    add neighbor to open
     */
}