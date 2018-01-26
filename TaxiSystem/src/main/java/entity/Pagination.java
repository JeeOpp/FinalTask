package entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores information about pages, their count and the stored records count.
 * @param <E> kind of stored information (clients, cars, etc.)
 */
public class Pagination<E> {
    private List<Page<E>> pagesList = null;
    private int countRecords;
    private int countPages;

    public Pagination() {
        pagesList = new ArrayList<>();
    }

    public List<Page<E>> getPagesList() {
        return pagesList;
    }

    public List<E> getPage(int page) {
        if (page > 0) {
            return pagesList.get(page - 1).getRecords();
        }
        return null;
    }

    public int getCountRecords() {
        return countRecords;
    }

    public int getCountPages() {
        return countPages;
    }

    public void setCountRecords(int countRecords) {
        this.countRecords = countRecords;
    }

    public void setCountPages(int countPages) {
        this.countPages = countPages;
    }
}