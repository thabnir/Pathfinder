import com.formdev.flatlaf.FlatDarkLaf
import java.awt.*
import java.awt.event.*
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JPanel


class GraphicsPanel(gridSizeX: Int, gridSizeY: Int) : JPanel() {
    val frame: JFrame
    val grid: Grid
    lateinit var buttonGrid: JPanel
    init {
        FlatDarkLaf.setup();
        grid = Grid(gridSizeX, gridSizeY)
        frame = JFrame("Pathfinder")
        frame.layout = BorderLayout()
        frame.preferredSize = Dimension(800, 600)
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.add(createButtonGrid(gridSizeX, gridSizeY))
        frame.pack()
        frame.setLocationRelativeTo(null)
        frame.isVisible = true
    }

    private fun createButtonGrid(gridSizeX: Int, gridSizeY: Int): JPanel {
        val buttons = ArrayList<ArrayList<FancyButton>>()
        buttonGrid = JPanel()
        buttonGrid.layout = GridLayout(gridSizeY, gridSizeX)
        for (y in 0 until gridSizeY) {
            buttons.add(ArrayList())
            for (x in 0 until gridSizeX) {
                val b = FancyButton(x, y, buttons, grid)
                buttonGrid.add(b)
                buttons[y].add(b)
            }
        }
        return buttonGrid
    }

    fun findPath() {
        // grid.findPath()
    }

    class FancyButton(val xLoc: Int, val yLoc: Int, val buttons: ArrayList<ArrayList<FancyButton>>, val grid: Grid): JButton() {
        val wallColor = Color(0, 0, 0)
        val clearColor = Color(255, 255, 255)
        val startColor = Color(0, 255, 0)
        val pathColor = Color(255, 255, 0)
        val endColor = Color(255, 0, 0)
        init {
            super.setLayout(GridBagLayout())
            this.background = clearColor
            addKeyListener(object: KeyListener {
                override fun keyPressed(e: KeyEvent) {
                    var button = e.source as FancyButton
                    when(e.keyCode) {
                        KeyEvent.VK_UP -> buttons[Math.floorMod(button.yLoc - 1, buttons.size)][button.xLoc].requestFocus()
                        KeyEvent.VK_DOWN -> buttons[Math.floorMod(button.yLoc + 1, buttons.size)][button.xLoc].requestFocus()
                        KeyEvent.VK_LEFT -> buttons[button.yLoc][Math.floorMod(button.xLoc - 1, buttons[0].size)].requestFocus()
                        KeyEvent.VK_RIGHT -> buttons[button.yLoc][Math.floorMod(button.xLoc + 1, buttons[0].size)].requestFocus()
                        KeyEvent.VK_SPACE -> {
                            button.background = wallColor
                            grid.node(button.xLoc, button.yLoc).crossable = false
                        }
                        KeyEvent.VK_SHIFT -> {
                            button.background = clearColor
                            grid.node(button.xLoc, button.yLoc).crossable = true
                        }
                    }
                }
                override fun keyTyped(e: KeyEvent) {
                    // TODO("Not yet implemented")
                }
                override fun keyReleased(e: KeyEvent) {
                    // TODO("Not yet implemented")
                }
            })
            addMouseListener(object: MouseListener {
                override fun mouseClicked(e: MouseEvent) {
                    var button = e.source as FancyButton
                    button.requestFocus()
                    when (e.button) {
                        MouseEvent.BUTTON1 -> {
                            button.background = wallColor
                            grid.node(button.xLoc, button.yLoc).crossable = false
                        }
                        MouseEvent.BUTTON3 -> {
                            button.background = clearColor
                            grid.node(button.xLoc, button.yLoc).crossable = true
                        }
                    }
                }
                override fun mousePressed(e: MouseEvent) {
                    // TODO("Not yet implemented")
                }
                override fun mouseReleased(e: MouseEvent) {
                    // TODO("Not yet implemented")
                }
                override fun mouseEntered(e: MouseEvent) {
                    // TODO("Not yet implemented")
                }
                override fun mouseExited(e: MouseEvent) {
                    // TODO("Not yet implemented")
                }
            })
            validate()
        }
    }
}