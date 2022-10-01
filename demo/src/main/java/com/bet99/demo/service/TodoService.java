package com.bet99.demo.service;

import com.bet99.demo.model.ApiResponse;
import com.bet99.demo.model.TodoModel;

public interface TodoService {
    public ApiResponse getAllTodo();
    public ApiResponse getTodo(int id);
    public ApiResponse saveTodo(TodoModel todo);
    public ApiResponse updateTodo(TodoModel todo);
    public ApiResponse deleteTodo(int id);
}
