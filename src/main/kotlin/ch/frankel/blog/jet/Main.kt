package ch.frankel.blog.jet

import com.hazelcast.jet.Jet
import com.hazelcast.jet.aggregate.AggregateOperations.reducing
import com.hazelcast.jet.function.BinaryOperatorEx
import com.hazelcast.jet.function.FunctionEx
import com.hazelcast.jet.pipeline.*

fun main(args: Array<String>) {
    with(Pipeline.create()) {
        val board = Board(args[0].toInt())
        drawFrom(needles(board))
            .withoutTimestamps()
            .map { it.drop(board) }
            .rollingAggregate(pi())
            .drainTo(Sinks.logger())
        Jet.newJetInstance()
            .newJob(this)
    }
}

private fun needles(board: Board) =
    SourceBuilder
        .stream("drop") { board.size }
        .fillBufferFn<Needle> { size, buffer ->
            buffer.add(Needle(size / 2))
        }.build()

private fun pi() = reducing(
    StartAggregator,
    FunctionEx<Boolean, Aggregator> {
        if (it) IntersectAggregator
        else NonIntersectAggregator
    },
    BinaryOperatorEx<Aggregator> { a1, a2 ->
        a1 + a2
    },
    null
)