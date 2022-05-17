import java.awt.Color
import java.awt.Dimension
import java.awt.Graphics
import java.util.*

class Die(
    s: Int,
    xloc: Int,
    yloc: Int,
    xvell: Double,
    yvell: Double,
    dimensions: Int,
    c: Color,
    b: Double,
    fx: Array<String>,
    HST: Double
) {
    //(numberOfSides)
    var numSides = 0

    //(currentSideUp)
    var sideUp = 0
        private set
    var x: Double
    var y: Double
    var size: Int
    var xVel: Double
    var yVel: Double
    var gravity = 2.0
    var bounciness // 1 = no energy lost, .5 = half energy lost, 0 = all energy lost
            : Double
    var xOld = 0.0
    var yOld = 0.0
    var xHit = false
    var yHit = false
    var hasGravity = false
    var gen = Random()
    var sfx: Array<String>
    var hitSoundThresh: Double
    var dieColor: Color
    var dots = arrayOfNulls<Dot>(6)

    init {
        if (s > 3) numSides = s else numSides = 6
        x = xloc.toDouble()
        y = yloc.toDouble()
        xVel = xvell
        yVel = yvell
        size = dimensions
        dieColor = c
        bounciness = b
        sfx = fx
        hitSoundThresh = HST
        roll()
    }

    fun roll() {
        val generator = Random()
        sideUp = generator.nextInt(numSides) + 1
        //initializes the dots on the die
        when (sideUp) {
            1 -> dots[0] = Dot((size * 4 / 10).toDouble(), (4 * size / 10).toDouble(), this)
            2 -> {
                dots[0] = Dot((size / 10).toDouble(), (size / 10).toDouble(), this)
                dots[1] = Dot((size * 7 / 10).toDouble(), (size * 7 / 10).toDouble(), this)
            }
            3 -> {
                dots[0] = Dot((size / 10).toDouble(), (size / 10).toDouble(), this)
                dots[1] = Dot((size * 7 / 10).toDouble(), (size * 7 / 10).toDouble(), this)
                dots[2] = Dot((size * 4 / 10).toDouble(), (size * 4 / 10).toDouble(), this)
            }
            4 -> {
                dots[0] = Dot((size / 10).toDouble(), (size / 10).toDouble(), this)
                dots[1] = Dot((size * 7 / 10).toDouble(), (size / 10).toDouble(), this)
                dots[2] = Dot((size / 10).toDouble(), (size * 7 / 10).toDouble(), this)
                dots[3] = Dot((size * 7 / 10).toDouble(), (size * 7 / 10).toDouble(), this)
            }
            5 -> {
                dots[0] = Dot((size / 10).toDouble(), (size / 10).toDouble(), this)
                dots[1] = Dot((size * 7 / 10).toDouble(), (size / 10).toDouble(), this)
                dots[2] = Dot((size / 10).toDouble(), (size * 7 / 10).toDouble(), this)
                dots[3] = Dot((size * 7 / 10).toDouble(), (size * 7 / 10).toDouble(), this)
                dots[4] = Dot((size * 4 / 10).toDouble(), (size * 4 / 10).toDouble(), this)
            }
            6 -> {
                dots[0] = Dot((size / 10).toDouble(), (size / 10).toDouble(), this)
                dots[1] = Dot((size * 7 / 10).toDouble(), (size / 10).toDouble(), this)
                dots[2] = Dot((size / 10).toDouble(), (size * 7 / 10).toDouble(), this)
                dots[3] = Dot((size * 7 / 10).toDouble(), (size * 7 / 10).toDouble(), this)
                dots[4] = Dot((size / 10).toDouble(), (size * 4 / 10).toDouble(), this)
                dots[5] = Dot((size * 7 / 10).toDouble(), (size * 4 / 10).toDouble(), this)
            }
            else -> {
                throw IllegalArgumentException("Die has too many dots! ($x)")
            }
        }
    }

    fun paintSelf(g: Graphics, hasDropShadow: Boolean) {
        if (hasDropShadow) {
            g.color =
                Color(dieColor.red, dieColor.green, dieColor.blue, dieColor.alpha * 3 / 4).darker().darker().darker()
                    .darker().darker()
            g.fillRoundRect(
                x.toInt() + size / 10, y.toInt() + size / 10,
                size,
                size, size / 10, size / 10
            ) //x, y, width, height, arcWidth, arcHeight
        }
        g.color = dieColor
        g.fillRoundRect(
            x.toInt(),
            y.toInt(),
            size,
            size,
            size / 10,
            size / 10
        ) //x, y, width, height, arcWidth, arcHeight
        g.color = Color.BLACK
        for (i in 0 until sideUp) dots[i]!!.paintSelf(g)
    }

    fun move(dt: Long, constant: Double, size: Dimension, hasSound: Boolean, gravity: Double, hasG: Boolean) {
        hasGravity = hasG
        if (x < 0) {
            x = 0.0
            xVel = -1 * bounciness * xVel
        } else if (x + this.size > size.getWidth()) {
            x = size.getWidth() - this.size
            xVel = -1 * bounciness * xVel
        }
        if (y < 0) {
            y = 0.0
            yVel = -1 * bounciness * yVel
        } else if (y + this.size > size.getHeight()) {
            y = size.getHeight() - this.size
            yVel = -1 * bounciness * yVel
        }
        if (hasGravity && gravity != 0.0) yVel += gravity / constant * dt
        x += xVel / constant * dt
        y += yVel / constant * dt
        for (i in 0 until sideUp) {
            dots[i]!!.move(this, dt, constant, gravity, hasGravity)
        }
    }

    fun freeze(xt: Double, yt: Double) {
        xVel = 0.0
        yVel = 0.0
        xOld = xt
        yOld = yt
    }

    fun unfreeze() {
        xVel = xOld
        yVel = yOld
    }

    fun jump(jumpSide: Double, jumpUp: Double) {
        yVel -= jumpUp
        xVel += jumpSide
    }

    fun setX(xloc: Int) {
        x = xloc.toDouble()
    }

    fun setY(yloc: Int) {
        y = yloc.toDouble()
    }
}