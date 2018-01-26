package service.impl;

import entity.Page;
import entity.Pagination;
import service.PaginationService;

import java.util.ArrayList;
import java.util.List;

/**
 * Class provides creating a pagination specific kind of information.
 *
 * @param <E> kind of stored information (clients, cars, etc.).
 */
public class PaginationServiceImpl<E> implements PaginationService<E> {
    private static final int RECORDS_PER_PAGE = 5;
    private static final int DEFAULT_COUNT_PAGE = 1;
    private Pagination<E> pagination = null;

    public PaginationServiceImpl() {
    }

    @Override
    public Pagination<E> getPagination() {
        return pagination;
    }

    /**
     * builds pagination out of entity list
     *
     * @param entityList list stored information about specific kind of information
     */
    @Override
    public void buildPagination(List<E> entityList) {
        pagination = new Pagination<>();
        pagination.setCountRecords(entityList.size());
        int countPages;
        if ((countPages = (int) Math.ceil(pagination.getCountRecords() * 1.0 / RECORDS_PER_PAGE)) > 0) {
            pagination.setCountPages(countPages);
        } else {
            pagination.setCountPages(DEFAULT_COUNT_PAGE);
        }
        Page<E> page;
        for (int i = 1; i <= pagination.getCountPages(); i++) {
            page = new Page<>();
            page.setRecords(splitList(i, entityList));
            pagination.getPagesList().add(page);
        }
    }

    private List<E> splitList(int currentPage, List<E> entityList) {
        int firstElement = (currentPage - 1) * RECORDS_PER_PAGE;
        int lastElement = firstElement + RECORDS_PER_PAGE - 1;
        int i = 0;
        List<E> pageList = new ArrayList<>();
        for (E each : entityList) {
            if (i >= firstElement) {
                pageList.add(each);
            }
            if (i == lastElement) {
                return pageList;
            }
            i++;
        }
        return pageList;
    }
}
