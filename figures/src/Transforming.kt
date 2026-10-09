interface Transforming {
    fun resize(zoom: Int)
    // TODO: величивает фигуру, не перемещая, с сохранением пропорций

    fun rotate(direction: RotateDirection, centerX: Int, centerY: Int)
    // TODO: поворот фигуры вокруг точки (centerX, centerY) на 90 градусов
}

enum class RotateDirection {
    // направление вращения фигуры на 90 градусов
    Clockwise, CounterClockwise
}

// Вспомогательная функция поворота точки вокруг центра на 90 градусов.
// Координаты экранные: x вправо, y вниз.
internal fun rotatePoint(
    px: Int,
    py: Int,
    cx: Int,
    cy: Int,
    direction: RotateDirection
): Pair<Int, Int> {
    val dx = px - cx
    val dy = py - cy
    return when (direction) {
        RotateDirection.Clockwise -> Pair(cx - dy, cy + dx)
        RotateDirection.CounterClockwise -> Pair(cx + dy, cy - dx)
    }
}
