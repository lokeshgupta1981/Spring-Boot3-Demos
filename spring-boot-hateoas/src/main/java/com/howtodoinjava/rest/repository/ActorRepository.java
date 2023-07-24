package com.howtodoinjava.rest.repository;

import com.howtodoinjava.rest.entity.ActorEntity;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;

public interface ActorRepository extends ListCrudRepository<ActorEntity, Long>,
    ListPagingAndSortingRepository<ActorEntity, Long> {

}
