package entity;

import java.util.List;

public class Page <E>{
    private int page;
    private List<E> records = null;

    public Page() {
    }

    public Page(int page) {
        this.page = page;
    }

    public Page(List<E> records, int page) {
        this(page);
        this.records = records;
    }

    public List<E> getRecords() {
        return records;
    }

    public int getPage() {
        return page;
    }

    public void setRecords(List<E> records) {
        this.records = records;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
