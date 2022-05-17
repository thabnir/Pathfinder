import java.awt.Color
import java.awt.Graphics

class Dot(xstart: Double, ystart: Double, die: Die) {
    var d: Int
    var dotColor: Color? = null
    var red: Int
    var green: Int
    var blue: Int
    var alpha: Int
    var x: Double
    var y: Double
    var xvel: Double
    var yvel: Double
    val bounciness = .94 // 1 = no energy lost, .5 = half energy lost, 0 = all energy lost

    init {
        xvel = die.xVel
        yvel = die.yVel
        x = die.x + xstart
        y = die.y + ystart
        d = die.size / 5
        red = die.dieColor.red
        green = die.dieColor.green
        blue = die.dieColor.blue
        alpha = die.dieColor.alpha
    }

    fun move(die: Die, dt: Long, constant: Double, gravity: Double, hasGravity: Boolean) {
        //collision detection
        if (x + xvel / constant * dt + die.xVel / constant * dt < die.x) {
            x = die.x
            xvel = -1 * bounciness * (xvel - die.xVel)
        }
        if (x + d + xvel / constant / dt + die.xVel / constant * dt > die.x + die.size) {
            x = die.x + die.size - d
            xvel = -1 * bounciness * (xvel - die.xVel)
        }
        if (y + yvel / constant * dt + die.yVel / constant * dt < die.y) {
            y = die.y
            yvel = -1 * bounciness * (yvel - die.yVel)
        }
        if (y + d + yvel / constant * dt + die.yVel / constant * dt > die.y + die.size) {
            y = die.y + die.size - d
            yvel = -1 * bounciness * (yvel - die.yVel)
        }
        //adding movement to position
        if (hasGravity && gravity != 0.0) yvel += gravity / constant * dt
        x += xvel / constant * dt //System.out.println("x=" + x);
        y += yvel / constant * dt //System.out.println("y=" + y);
    }

    fun paintSelf(g: Graphics) {
        g.color = Color(red, green, blue, alpha).darker().darker().darker().darker()
        g.fillOval(x.toInt(), y.toInt(), d, d)
    }
}