import com.formdev.flatlaf.FlatDarkLaf
import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.GridLayout
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.JScrollPane

class GraphicsPanel(gridSizeX: Int, gridSizeY: Int, val isTaxicab: Boolean, val isScrolly: Boolean) : JPanel() {
    val frame: JFrame
    val grid: Grid
    val buttons = ArrayList<ArrayList<FancyButton>>()
    var path: ArrayList<Grid.Node> = ArrayList()
    lateinit var buttonGrid: JPanel

    init {
        FlatDarkLaf.setup()
        System.setProperty("sun.java2d.uiScale", "1.0")
        grid = Grid(gridSizeX, gridSizeY, isTaxicab)
        frame = JFrame("Pathfinder")
        frame.layout = BorderLayout()
        frame.preferredSize = Dimension(1920/2, 1080/2)
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE

        if (isScrolly) {
            val scrollPanel = JScrollPane(createButtonGrid(gridSizeX, gridSizeY))
            frame.add(scrollPanel)
        } else {
            frame.add(createButtonGrid(gridSizeX, gridSizeY))
        }

        frame.pack()
        frame.setLocationRelativeTo(null)
        // * checkerboards the grid
        for (x in 0 until grid.gridSizeX) {
            for (y in 0 until grid.gridSizeY) {
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