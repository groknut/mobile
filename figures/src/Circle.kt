import kotlin.math.PI

// TODO: дополнить определение класса размерами и позицией
class Circle(
    var x: Int = 0,
    var y: Int = 0,
    var radius: Int = 1
) : Figure(0), Movable, Transforming {
    // TODO: реализовать интерфейс Transforming
    override fun area(): Float {
        return (PI * radius * radius).toFloat()
    }

    override fun move(dx: Int, dy: Int) {
        x += dx
        y += dy
    }

    override fun resize(zoom: Int) {
        require(zoom > 0) { "zoom must be positive" }
        radius *= zoom
    }

    override fun rotate(direction: RotateDirection, centerX: Int, centerY: Int) {
        val (newX, newY) = rotatePoint(x, y, centerX, centerY, direction)
        x = newX
        y = newY
    }
}
