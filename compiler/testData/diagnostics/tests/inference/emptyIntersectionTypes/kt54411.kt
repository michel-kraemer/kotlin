// FIR_IDENTICAL
// !RENDER_DIAGNOSTICS_FULL_TEXT
class AtomicRef<T>(val value: T)

inline fun <F : Segment<F>> AtomicRef<F>.findSegmentAndMoveForward(createNewSegment: (prev: F?) -> F) = null

interface Queue<Q> {
    val tail: AtomicRef<OneElementSegment<Q>>

    fun enqueue(element: Q) {
        // F <: Segment<F> from upper bound
        // F <: OneElementSegment<Segment<F>>? from ::createSegment argument. ? is questionable here
        //     (F?) -> F <: (OneElementSegment<C>?) -> OneElementSegment<C>
        tail.<!INFERRED_TYPE_VARIABLE_INTO_POSSIBLE_EMPTY_INTERSECTION!>findSegmentAndMoveForward<!>(::createSegment)
    }
}

private fun <C> createSegment(prev: OneElementSegment<C>?) = OneElementSegment<C>()

class OneElementSegment<O>() : Segment<OneElementSegment<O>>()

abstract class Segment<S : Segment<S>>
