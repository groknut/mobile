// TODO: дополнить определение класса размерами и позицией
class Square(
    var x: Int = 0,
    var y: Int = 0,
    var side: Int = 1
) : Figure(0), Movable, Transforming {
    // TODO: унаследовать от Figure, реализовать area()
    override fun area(): Float = (side * side).toFloat()

    // TODO: реализовать интерфейс Transforming
    override fun move(dx: Int, dy: Int) {
        x += dx
        y += dy
    }

    override fun resize(zoom: Int) {
        require(zoom > 0) { "zoom must be positive" }
        side *= zoom
    }

    override fun rotate(direction: RotateDirection, centerX: Int, centerY: Int) {
        val cx = x + side / 2
        val cy = y + side / 2
        val (newCx, newCy) = rotatePoint(cx, cy, centerX, centerY, direction)

        x = newCx - side / 2
        y = newCy - side / 2
    }
}
