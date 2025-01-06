package com.ecommerce.demo.dto;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageRequestDTO {
    private int pageNo = 0;
    private int pageSize = 10;
    private Sort.Direction sortDirection = Sort.Direction.ASC;
    private String sortBy = "id";

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public Sort.Direction getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(Sort.Direction sortDirection) {
        this.sortDirection = sortDirection;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public Pageable getPageable(PageRequestDTO pageRequestDTO) {
        int currentSize = (pageRequestDTO != null) ? pageRequestDTO.getPageSize() : this.pageSize;
        int currentNo = (pageRequestDTO != null) ? pageRequestDTO.getPageNo() : this.pageNo;
        Sort.Direction currentSortDirection = (pageRequestDTO.getSortDirection() != null)   ?
                        pageRequestDTO.getSortDirection() : this.sortDirection;
        String sortByColumn = (pageRequestDTO.getSortBy() != null || pageRequestDTO.getSortBy() != "") ?
                                    pageRequestDTO.getSortBy() : this.getSortBy();
        PageRequest req = PageRequest.of(currentNo,currentSize,currentSortDirection,sortByColumn);
        return req;
    }
}
