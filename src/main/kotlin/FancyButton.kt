import java.awt.Color
import java.awt.Dimension
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import javax.swing.JButton

class FancyButton(
    val xLoc: Int,
    val yLoc: Int,
    val buttons: ArrayList<ArrayList<FancyButton>>,
    val grid: Grid,
    val graphicsPanel: GraphicsPanel
) : JButton() {

    val wallColor = Color(0, 0, 0)
    val clearColor = Color(255, 255, 255)
    val startColor = Color(0, 0, 255)
    val pathColor = Color(0,255,0)
    val endColor = Color(255, 0, 0)
    val gridNode: Grid.Node = grid.node(xLoc, yLoc)

    init {
        //isBorderPainted = false
        //isRolloverEnabled = false
        //isFocusPainted = false
        preferredSize = Dimension(40,40)
        addKeyListener(object : KeyListener {
            override fun keyPressed(e: KeyEvent) {
                val button = e.source as FancyButton
                when (e.keyCode) {
                    KeyEvent.VK_UP -> {
                        buttons[Math.floorMod(button.yLoc - 1, buttons.size)][button.xLoc].requestFocusInWindow()
                    }
                    KeyEvent.VK_DOWN -> {
                        buttons[Math.floorMod(button.yLoc + 1, buttons.size)][button.xLoc].requestFocusInWindow()
                    }
                    KeyEvent.VK_LEFT -> {
                        buttons[button.yLoc][Math.floorMod(button.xLoc - 1, buttons[0].size)].requestFocusInWindow()
                    }
                    KeyEvent.VK_RIGHT -> {
                        buttons[button.yLoc][Math.floorMod(button.xLoc + 1, buttons[0].size)].requestFocusInWindow()
                    }
                    KeyEvent.VK_SHIFT -> {
                        setStartNode()
                    }
                    KeyEvent.VK_F -> {
                        setTargetNode()
                    }
                    KeyEvent.VK_SPACE -> setWall()
                    KeyEvent.VK_BACK_SPACE -> setClear()
                }
            }

            override fun keyTyped(e: KeyEvent) {}
            override fun keyReleased(e: KeyEvent) {}
        })

        addMouseListener(object : MouseListener {
            override fun mousePressed(e: MouseEvent) {
                val button = e.source as FancyButton
                button.requestFocus()
                when (e.button) {
                    MouseEvent.BUTTON1 -> setWall()
                    MouseEvent.BUTTON3 -> setClear()
                }
            }

            override fun mouseClicked(e: MouseEvent) {}
            override fun mouseReleased(e: MouseEvent) {}
            override fun mouseEntered(e: MouseEvent) {
                val button = e.source as FancyButton
                button.requestFocus()
                if (e.getModifiersEx() == MouseEvent.BUTTON1_DOWN_MASK) {
                    setWall()
                } else if (e.getModifiersEx() == MouseEvent.BUTTON3_DOWN_MASK) {
                    setClear()
                }
            }

            override fun mouseExited(e: MouseEvent) {}
        })
        validate()
    }

    fun matchToGrid(path: ArrayList<Grid.Node>) {
        if (!gridNode.isCrossable) {
            this.background = wallColor
        } else if (gridNode == grid.startNode) {
            this.background = startColor
        } else if (gridNode == grid.targetNode) {
            this.background = endColor
        } else if (path.contains(gridNode)) {
            this.background = pathColor
        } else {
            this.background = clearColor
        }
    }

    fun matchToGrid() {
        for (row in buttons) {
            for (button in row) {
                if (!button.gridNode.isCrossable) {
                    button.background = wallColor
                } else if (button.gridNode == grid.startNode) {
                    button.background = startColor
                } else if (button.gridNode == grid.targetNode) {
                    button.background = endColor
                } else {
                    button.background = clearColor
                }
            }
        }
    }

    fun setWall() {
        grid.setWall(xLoc, yLoc, true)
        graphicsPanel.findPath()
        // matchToGrid()
    }

    fun setClear() {
        grid.setWall(xLoc, yLoc, false)
        graphicsPanel.findPath()
        // matchToGrid()
    }

    fun setStartNode() {
        grid.setStartNode(xLoc, yLoc)
        graphicsPanel.findPath()
        // matchToGrid()
    }

    fun setTargetNode() {
        grid.setTargetNode(xLoc, yLoc)
        graphicsPanel.findPath()
        // matchToGrid()
    }
}