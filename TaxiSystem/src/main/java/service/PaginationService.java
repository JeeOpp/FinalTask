package service;

import entity.Page;
import entity.Pagination;

import java.util.List;

/**
 * Created by DNAPC on 26.01.2018.
 */
public interface PaginationService<E> {
    Pagination<E> getPagination();

    /**
     * builds pagination out of entity list
     *
     * @param entityList list stored information about specific kind of information
     */
    void buildPagination(List<E> entityList);
}
