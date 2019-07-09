package com.mini.jdbc;

public final class Paging {
    private int page;
    private int rows;
    private int total;

    public Paging() {}

    public Paging(int page, int rows) {
        this.page = page;
        this.rows = rows;
    }

    public int getPage() {
        return Math.max(1, page);
    }

    public Paging setPage(int page) {
        this.page = page;
        return this;
    }

    public int getRows() {
        return Math.max(1, rows);
    }

    public Paging setRows(int rows) {
        this.rows = rows;
        return this;
    }

    public int getTotal() {
        return Math.max(0, total);
    }

    public Paging setTotal(int total) {
        this.total = total;
        return this;
    }

    public int getStart() {
        return (page - 1) * rows;
    }

    public int getMaxPage() {
        return (int) Math.ceil(total * 1.0 / rows);
    }

}
