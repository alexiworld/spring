package com.example.springjdbcmybatis3.todo.repository;

import com.example.springjdbcmybatis3.todo.domain.Todo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends CrudRepository<Todo, Integer> {
}
