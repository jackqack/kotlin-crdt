import crdt.counter.GCounter
import crdt.counter.PNCounter
import crdt.set.GSet
import crdt.set.TwoPhaseSet

/**
 * Created by jackqack on 20/05/17.
 */


fun testGCounter() {
    val replica1 = GCounter()
    replica1.increment("hostname1")
    replica1.increment("hostname2")
    replica1.increment("hostname2")
    replica1.increment("hostname2")
    replica1.increment("hostname3")
    println(replica1.value())

    val replica2 = GCounter()
    replica2.increment("hostname1")
    replica2.increment("hostname1")
    replica2.increment("hostname2")
    replica2.increment("hostname2")
    replica2.increment("hostname3")
    println(replica2.value())

    replica1.merge(replica2)
    println(replica1.value())

}

fun testPNCounter() {

    val replica1 = PNCounter()
    // hostname1 = -1
    replica1.increment("hostname1")
    replica1.decrement("hostname1")
    replica1.decrement("hostname1")

    // hostname2 = 2
    replica1.decrement("hostname2")
    replica1.increment("hostname2")
    replica1.increment("hostname2")
    replica1.increment("hostname2")

    // hostname3 = 0
    replica1.decrement("hostname3")
    replica1.increment("hostname3")
    replica1.decrement("hostname3")
    replica1.increment("hostname3")

    // sum = 1
    println(replica1.value())


    val replica2 = PNCounter()
    // hostname1 = 2
    replica2.increment("hostname1")
    replica2.increment("hostname1")
    // hostname2 = -2
    replica2.decrement("hostname2")
    replica2.decrement("hostname2")
    // hostname3 = 3
    replica2.increment("hostname3")
    replica2.increment("hostname3")
    replica2.increment("hostname3")
    // sum = 3
    println(replica2.value())

    replica1.merge(replica2)
    // merge = 2
    println(replica1.value())

}

fun testGSet() {
    val set1 = GSet<Int>()
    set1.add(1)
    set1.add(2)
    set1.add(2)
    set1.add(4)

    val set2 = GSet<Int>()
    set2.add(1)
    set2.add(3)
    set2.add(4)
    set2.add(5)

    set1.merge(set2)

    println(set1.value())
}

fun testTwoPhaseSet() {
    val set1 = TwoPhaseSet<Int>()
    set1.add(1)
    set1.add(2)
    set1.add(2)
    set1.add(4)

    val set2 = TwoPhaseSet<Int>()
    set2.add(1)
    set2.add(3)
    set2.add(4)
    set2.add(5)

    set1.merge(set2)

    set1.remove(2)

    set2.add(2)


    set2.merge(set1)
    set2.addAll(listOf(2, 4, 5, 6))

    println(set2.value())
}


fun main(args: Array<String>) {

    testTwoPhaseSet()

}



