package sn.mini.kotlin.util


import kotlin.math.ceil
import kotlin.math.max
import kotlin.math.min


fun main(args: Array<String>) {
    val ag = arrayOf(1, 2, 3)

    ag.let {
        if (it.isNotEmpty()) {
            println(it[0])
            return@let Unit
        }
        println(it.size)
    }
//
//    ag.forEach {
//        if (it == 2) {
//            println(it)
//            return@forEach Unit
//        }
//        println(it)
//    }
}

data class Paging(private val mPage: Int, private val mRows: Int) {

    var page: Int = mPage
        get() = max(1, min(field, maxPage))

    var rows: Int = mRows
        get() = max(1, field)

    var total: Int = 0
        get() = max(0, field)

    val start = (page - 1) * rows

    val maxPage = ceil(this.total * 1.0 / this.rows).toInt()
}

