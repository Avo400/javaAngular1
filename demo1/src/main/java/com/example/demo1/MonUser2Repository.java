package com.example.demo1;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonUser2Repository extends CrudRepository<MonUser2, Long> {
}