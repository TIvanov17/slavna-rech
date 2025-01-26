package pu.fmi.slavnarech.utils;

import lombok.Data;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Data
public class PageFilter implements Pageable {

  public static final int DEFAULT_PAGE_SIZE = 10;

  private String searchKeyword;

  private int page;

  protected int pageSize;

  @Override
  public int getPageNumber() {
    return page;
  }

  @Override
  public int getPageSize() {
    return pageSize;
  }

  @Override
  public long getOffset() {
    long page = this.page - 1;
    return (page < 0 ? 0 : page) * this.pageSize;
  }

  @Override
  public Sort getSort() {
    return Sort.unsorted();
  }

  @Override
  public Pageable next() {
    return null;
  }

  @Override
  public Pageable previousOrFirst() {
    return null;
  }

  @Override
  public Pageable first() {
    return null;
  }

  @Override
  public Pageable withPage(int pageNumber) {
    return null;
  }

  @Override
  public boolean hasPrevious() {
    return false;
  }
}
