import {
  SortColumn,
  SortDirection,
} from '../components/table/sortable.directive';

export type PageFilter = {
  page: number;
  pageSize: number;
  searchKeyword: string;
  sortColumn: string;
  sortDirection: SortDirection;
};

export type Page<T> = {
  content: T[];
  pageable: {
    pageSize: number;
    offset: number;
    sort: {
      empty: boolean;
      unsorted: boolean;
      sorted: boolean;
    };
    pageNumber: number;
    unpaged: boolean;
    paged: boolean;
  };
  last: boolean;
  totalPages: number;
  totalElements: number;
  first: boolean;
  numberOfElements: number;
  size: number;
  number: number;
  sort: {
    empty: boolean;
    unsorted: boolean;
    sorted: boolean;
  };
  empty: boolean;
};
