package ch.frankel.blog.jet

import java.io.Serializable
import java.math.BigDecimal
import java.math.MathContext

private val context = MathContext(6)

open class Aggregator(
    private val drops: Long,
    private val intersections: Long) : Serializable {
    operator fun plus(aggregator: Aggregator) =
        Aggregator(drops + aggregator.drops,
            intersections + aggregator.intersections)

    private val pi: BigDecimal
        get() = if (intersections == 0L) BigDecimal.ZERO
        else BigDecimal(drops).divide(BigDecimal(intersections), context)

    override fun toString() = "Aggregator(pi= $pi, drops= $drops)"
}

object StartAggregator : Aggregator(0L, 0L)
object IntersectAggregator : Aggregator(1L, 1L)
object NonIntersectAggregator : Aggregator(1L, 0L)