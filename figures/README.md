# Kotlin Figures Demo

Демонстрационный проект на Kotlin: абстрактный класс `Figure`, интерфейсы `Movable` и `Transforming`, классы `Rect`, `Circle`, `Square`.

## Возможности

- Площадь фигуры — `area()`
- Перемещение — `move(dx, dy)`
- Масштабирование — `resize(zoom)`
- Поворот на 90° — `rotate(direction, centerX, centerY)`

## Демонстрация

### Rect
```kotlin
val rect = Rect(0, 0, 4, 2)
rect.move(1, 1)                                  // перемещение
rect.resize(2)                                   // масштаб
rect.rotate(RotateDirection.Clockwise, 0, 0)     // поворот
```
```text
before:      x=0, y=0, w=4, h=2, area=8.0
after move:  x=1, y=1, w=4, h=2
after resize:x=1, y=1, w=8, h=4, area=32.0
after rotate:x=-2, y=1, w=4, h=8
```

### Circle
```kotlin
val circle = Circle(1, 2, 5)
circle.move(2, 3)
circle.resize(2)
circle.rotate(RotateDirection.CounterClockwise, 0, 0)
```
```text
before:      x=1, y=2, r=5,  area=78.54
after move:  x=3, y=5, r=5
after resize:x=3, y=5, r=10, area=314.16
after rotate:x=-5, y=3, r=10
```

### Square
```kotlin
val square = Square(0, 0, 2)
square.move(1, 1)
square.resize(3)
square.rotate(RotateDirection.Clockwise, 0, 0)
```
```text
before:      x=0, y=0, side=2, area=4.0
after move:  x=1, y=1, side=2
after resize:x=1, y=1, side=6, area=36.0
after rotate:x=-2, y=1, side=6
```
