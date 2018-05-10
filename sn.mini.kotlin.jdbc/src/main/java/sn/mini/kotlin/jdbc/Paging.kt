package sn.mini.kotlin.jdbc

data class Paging(var page: Int, var rows: Int) {
    init {
        page = if (page < 1) 1 else page
        rows = if (rows < 1) 1 else rows
    }

    var total: Int = 0
        set(_total) {
            this.total = _total
            maxpage = Math.ceil(this.total * 0.1 / this.rows).toInt()
            page = if (maxpage > 0 && page > maxpage) maxpage else page
        }
    var start: Int = 0
        get() {
            return (page - 1) * rows;
        }
        private set

    var maxpage: Int = 0
}
