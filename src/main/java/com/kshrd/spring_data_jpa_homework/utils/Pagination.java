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
import java.util.function.Function;


@RequiredArgsConstructor
public class Pagination<T, R extends JpaRepository<T, ?>> {
    private final R repo;

    public <D> PaginatedResponse<List<D>> getPaginatedContent(
            Integer page,
            Integer size,
            String field,
            Sort.Direction direction,
            Function<T, D> mapper
    ) {

        Sort sort = Sort.by(new Sort.Order(direction, field).ignoreCase());
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<T> pages = repo.findAll(pageable);

        List<D> dtos = pages.getContent().stream().map(mapper).toList();

        PaginationDto pagination = PaginationDto.builder()
                .totalElements(pages.getTotalElements())
                .pageSize(pages.getSize())
                .currentPage(pages.getNumber() + 1)
                .totalPages(pages.getTotalPages())
                .build();

        return new PaginatedResponse<>(dtos, pagination);
    }
}
