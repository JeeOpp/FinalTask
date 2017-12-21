package service;

import entity.Page;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DNAPC on 21.12.2017.
 */
public class PaginationService <E>{
    private static final int RECORDS_PER_PAGE = 5;
    private List<Page<E>> pagesList = null;
    private List<E> entityList = null;
    private int countRecords;
    private int countPages;

    public PaginationService() {
    }

    public List<E> getPageList(int page) {
        if(page>0) {
            return pagesList.get(page - 1).getRecords();
        }
        return null;
    }

    public static int getRecordsPerPage() {
        return RECORDS_PER_PAGE;
    }

    public int getCountRecords() {
        return countRecords;
    }

    public int getCountPages() {
        return countPages;
    }

    public void buildPagination(List<E> entityList){
        this.entityList = entityList;
        this.countRecords = entityList.size();
        this.countPages = (int) Math.ceil(countRecords* 1.0 / RECORDS_PER_PAGE);
        this.pagesList = new ArrayList<>();

        Page<E> page;
        for (int i=1; i<=countPages;i++){
            page = new Page<E>();
            page.setRecords(splitList(i));
            pagesList.add(page);
        }
    }

    private List<E> splitList(int currentPage){
        int firstElement = (currentPage-1)*RECORDS_PER_PAGE;
        int lastElement = firstElement+RECORDS_PER_PAGE-1;
        int i=0;
        List<E> pageList = new ArrayList<E>();
        for(E each:entityList){
            if (i>=firstElement){
                pageList.add(each);
            }
            if (i==lastElement){
                return pageList;
            }
            i++;
        }
        return pageList;
    }
}
