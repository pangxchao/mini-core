
package sn.mini.java.jdbc;


public final class Paging {
    private int page;
    private int rows;
    private int total;
    private int maxPage;

    public int getPage() {
        return Math.max(1, page);
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return Math.max(1, rows);
    }

    public void setRows(int rows) {
        this.rows = rows;
    }


    public int getTotal() {
        return Math.max(0, total);
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getStart() {
        return (page - 1) * rows;
    }

    public int getMaxPage() {
        return (int) Math.ceil(this.total * 1.0 / this.rows);
    }


    public Paging() {
    }

    public Paging(int page, int rows) {
        this.page = page;
        this.rows = rows;
    }
}
