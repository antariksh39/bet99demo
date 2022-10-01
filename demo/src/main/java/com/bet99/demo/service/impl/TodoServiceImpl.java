package com.bet99.demo.service.impl;

import com.bet99.demo.model.ApiResponse;
import com.bet99.demo.model.TodoModel;
import com.bet99.demo.repository.TodoRepository;
import com.bet99.demo.service.TodoService;
import com.bet99.demo.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import redis.clients.jedis.Jedis;

import java.util.Date;
import java.util.Set;

public class TodoServiceImpl implements TodoService {

    @Autowired
    private TodoRepository todoRepository;

    @Override
    public ApiResponse getAllTodo() {
        Jedis jedis = new Jedis("localhost", 6379);
        String pattern = "Todo:"+"*";
        Set<String> keys = jedis.keys(pattern);

        for (String key : keys) {
            jedis.keys(key);
        }
        return new ApiResponse(200,"OK",keys);
    }

    @Override
    public ApiResponse getTodo(int id) {
        TodoModel todoModel = todoRepository.findById(String.valueOf(id)).get();
        return new ApiResponse(200,"Done",todoModel);
    }

    @Override
    public ApiResponse saveTodo(TodoModel todo) {
        TodoModel todoModel = new TodoModel(todo.getTaskId(),todo.getUserId(),todo.getDescription(),new Date(), TodoModel.State.TODO);
        todoRepository.save(todo);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setData(todo);
        apiResponse.setStatusCode(HttpStatus.OK.value());
        apiResponse.setMessage(Constants.TODO_SAVED_WITH_ID +todo.getTaskId());
        return apiResponse;
    }

    @Override
    public ApiResponse updateTodo(TodoModel todo) {
        return null;
    }

    @Override
    public ApiResponse deleteTodo(int id) {
        return null;
    }
}
