import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.GridLayout
import javax.swing.JFrame
import javax.swing.JPanel

class GraphicsPanel(gridSizeX: Int, gridSizeY: Int) : JPanel() {
    val frame: JFrame
    val grid: Grid
    val buttons = ArrayList<ArrayList<FancyButton>>()
    var path: ArrayList<Grid.Node> = ArrayList()
    lateinit var buttonGrid: JPanel

    init {
        //FlatIntelliJLaf.setup()
        grid = Grid(gridSizeX, gridSizeY)
        frame = JFrame("Pathfinder")
        frame.layout = BorderLayout()
        frame.preferredSize = Dimension(1600, 900)
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.add(createButtonGrid(gridSizeX, gridSizeY))
        frame.pack()
        frame.setLocationRelativeTo(null)

        for (x in 0 until grid.numCols - 1) {
            for (y in 1 until grid.numRows - 1) {
                if ((y + x) % 2 == 0) {
                    grid.node(x, y).isCrossable = false
                }
            }
        }
        buttons[0][0].matchToGrid()
        frame.isVisible = true

    }

    private fun createButtonGrid(gridSizeX: Int, gridSizeY: Int): JPanel {
        buttonGrid = JPanel()
        buttonGrid.layout = GridLayout(gridSizeY, gridSizeX)
        for (y in 0 until gridSizeY) {
            buttons.add(ArrayList())
            for (x in 0 until gridSizeX) {
                val b = FancyButton(x, y, buttons, grid, this)
                buttonGrid.add(b)
                buttons[y].add(b)
            }
        }
        return buttonGrid
    }

    fun findPath() {
        path = grid.findPath()
        for (button in buttonGrid.components) {
            if (button is FancyButton) {
                button.matchToGrid(path)
            }
        }
    }
}