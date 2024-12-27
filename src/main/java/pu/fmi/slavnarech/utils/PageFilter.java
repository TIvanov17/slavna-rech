package pu.fmi.slavnarech.utils;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageFilter implements Pageable {

  private static final int DEFAULT_PAGE_SIZE = 10;

  private int page;

  protected int pageSize = DEFAULT_PAGE_SIZE;

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
    return 0;
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
