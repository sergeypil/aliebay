package com.epam.aliebay.dto;


public class ProductsDto {
    private String numberOfPageParam;
    private String sortParam;
    private String searchParam;
    private String idCategoryParam;
    private String idProducerParam;

    public ProductsDto() {
    }

    public String getNumberOfPageParam() {
        return numberOfPageParam;
    }

    public void setNumberOfPageParam(String numberOfPageParam) {
        this.numberOfPageParam = numberOfPageParam;
    }

    public String getSortParam() {
        return sortParam;
    }

    public void setSortParam(String sortParam) {
        this.sortParam = sortParam;
    }

    public String getSearchParam() {
        return searchParam;
    }

    public void setSearchParam(String searchParam) {
        this.searchParam = searchParam;
    }

    public String getIdCategoryParam() {
        return idCategoryParam;
    }

    public void setIdCategoryParam(String idCategoryParam) {
        this.idCategoryParam = idCategoryParam;
    }

    public String getIdProducerParam() {
        return idProducerParam;
    }

    public void setIdProducerParam(String idProducerParam) {
        this.idProducerParam = idProducerParam;
    }
}
