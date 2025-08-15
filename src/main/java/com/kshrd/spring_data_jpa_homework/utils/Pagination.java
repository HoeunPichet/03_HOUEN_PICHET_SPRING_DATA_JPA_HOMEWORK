package com.kshrd.spring_data_jpa_homework.utils;

import com.kshrd.spring_data_jpa_homework.model.dto.PaginationDto;
import com.kshrd.spring_data_jpa_homework.model.response.PaginatedResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


@RequiredArgsConstructor
public class Pagination<T, R extends JpaRepository<T, ?>> {
    private final R repo;

    public PaginatedResponse<List<T>> getAllWithPagination(Integer page, Integer size, String field, Sort.Direction direction) {
        Sort sort = Sort.by(new Sort.Order(direction, field).ignoreCase());
        Pageable pageable = PageRequest.of(page-1, size, sort);
        Page<T> pages = repo.findAll(pageable);
        List<T> payload = pages.getContent();
        PaginationDto paginationDto = PaginationDto.builder()
                .totalElements(pages.getTotalElements())
                .pageSize(pages.getSize())
                .currentPage(pages.getNumber() + 1)
                .totalPages(pages.getTotalPages())
                .build();

        return new PaginatedResponse<>(payload, paginationDto);
    }
}
