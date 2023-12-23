package com.univrouen.ollcalamaison.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchResultDto<T> {

    private List<T> data;
    private int itemsPerPage;
    private long itemCount;
    private int page;
    private int pageCount;

    public static <E> SearchResultDto<E> from(Page<E> page) {
        return SearchResultDto.<E>builder()
                .data(page.toList())
                .itemsPerPage(page.getNumberOfElements())
                .itemCount(page.getTotalElements())
                .page(page.getNumber())
                .pageCount(page.getTotalPages())
                .build();
    }
}
