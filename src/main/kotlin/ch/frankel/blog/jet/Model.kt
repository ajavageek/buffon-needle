package ch.frankel.blog.jet

import java.io.Serializable
import kotlin.math.sin
import kotlin.random.Random

class Board(val size: Int) : Serializable

class Needle(private val size: Int) : Serializable {

    fun drop(board: Board): Boolean {
        val angle = Math.toRadians(Random.nextDouble(90.0))
        val position = Random.nextDouble(board.size.toDouble())
        val normalizedSize = (size / 2.toDouble()) * sin(angle)
        val bottom = position - normalizedSize
        val top = position + normalizedSize
        return bottom <= 0 || top >= board.size
    }

    override fun toString() = "Needle(size= $size)"
}
