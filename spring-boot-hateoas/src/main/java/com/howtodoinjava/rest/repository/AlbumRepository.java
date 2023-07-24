package com.howtodoinjava.rest.repository;

import com.howtodoinjava.rest.entity.AlbumEntity;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;

public interface AlbumRepository extends ListCrudRepository<AlbumEntity, Long>,
    ListPagingAndSortingRepository<AlbumEntity, Long> {

}