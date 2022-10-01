package com.bet99.demo.repository;

import com.bet99.demo.model.TodoModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository  extends CrudRepository<TodoModel, String> {
}
