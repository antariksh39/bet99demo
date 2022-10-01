package com.bet99.demo.service.impl;

import com.bet99.demo.model.ApiResponse;
import com.bet99.demo.model.TodoModel;
import com.bet99.demo.repository.TodoRepository;
import com.bet99.demo.service.TodoService;
import com.bet99.demo.util.Constants;
import com.bet99.demo.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

@Service
public class TodoServiceImpl implements TodoService {


    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private int redisPort;

    @Autowired
    private TodoRepository todoRepository;

    @Override
    public ApiResponse getAllTodo() {
        Jedis jedis = new Jedis(redisHost, redisPort);
        Set<String> keys = jedis.keys("Todo:"+"*");
        List<TodoModel> todos = new ArrayList<>();
        for (String key : keys) {
            TodoModel todoModel = todoRepository.findById(String.valueOf(key.replaceAll("Todo:",""))).get();
            todos.add(todoModel);
        }
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setStatusCode(HttpStatus.OK.value());
        apiResponse.setMessage(Constants.TODO_FOUND_NUMBER + todos.size());
        apiResponse.setData(todos);
        return apiResponse;
    }

    @Override
    public ApiResponse getTodo(int id) {
        ApiResponse apiResponse = new ApiResponse();
        if(!todoRepository.existsById(String.valueOf(id))){
            apiResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
            apiResponse.setMessage(Constants.TODO_NOT_FOUND_WITH_ID + id );
            return apiResponse;
        }
        TodoModel todoModel = todoRepository.findById(String.valueOf(id)).get();
        apiResponse.setStatusCode(HttpStatus.OK.value());
        apiResponse.setMessage(Constants.TODO_FOUND_WITH_ID + id);
        apiResponse.setData(todoModel);
        return apiResponse;
    }

    @Override
    public ApiResponse saveTodo(TodoModel todo) {
        ApiResponse apiResponse = new ApiResponse();

        //Checking if essential data is present
        if ( ! (StringUtils.hasLength(todo.getUserId())
                && StringUtils.hasLength(todo.getDescription())) ) {
            apiResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
            apiResponse.setMessage(Constants.TODO_INCOMPLETE_DATA_FOR_SAVE);
            apiResponse.setData(todo);
            return apiResponse;
        }

        //checking if the ID already exists
        if(todoRepository.existsById(String.valueOf(todo.getTaskId()))){
            apiResponse.setStatusCode(HttpStatus.CONFLICT.value());
            apiResponse.setMessage(Constants.TODO_ALREADY_EXISTS_WITH_ID + todo.getTaskId());
            apiResponse.setData(todo);
            return apiResponse;
        }

        //Sanity check on date format
        if(!DateUtil.isDateValid(todo.getDueDate())){
            apiResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
            apiResponse.setMessage(Constants.TODO_WRONG_DATE);
            apiResponse.setData(todo);
            return apiResponse;
        }

        todo.setTaskId(getMaxID()+1);
        todoRepository.save(todo);

        apiResponse.setData(todo);
        apiResponse.setStatusCode(HttpStatus.CREATED.value());
        apiResponse.setMessage(Constants.TODO_SAVED_WITH_ID +todo.getTaskId());
        return apiResponse;
    }

    @Override
    public ApiResponse updateTodo(TodoModel todo) {
        ApiResponse apiResponse = new ApiResponse();
        if(todoRepository.existsById(String.valueOf(todo.getTaskId()))){

            //Checking if essential data is present
            if ( ! (StringUtils.hasLength(String.valueOf(todo.getTaskId())))
                    && (StringUtils.hasLength(todo.getUserId())
                    && StringUtils.hasLength(todo.getDescription())) ) {
                apiResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
                apiResponse.setMessage(Constants.TODO_INCOMPLETE_DATA_FOR_UPDATE);
                apiResponse.setData(todo);
                return apiResponse;
            }

            todoRepository.save(todo);
            apiResponse.setData(todo);
            apiResponse.setStatusCode(HttpStatus.ACCEPTED.value());
            apiResponse.setMessage(Constants.TODO_SAVED_WITH_ID +todo.getTaskId());
            return apiResponse;
        }
        return new ApiResponse(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.toString(),null);
    }

    @Override
    public ApiResponse deleteTodo(int id) {
        if(todoRepository.existsById(String.valueOf(id))){
            todoRepository.deleteById(String.valueOf(id));
            return new ApiResponse(200,"OK - Deleted",null);
        }
        return new ApiResponse(404,"Not Found",null);
    }

    public int getMaxID() {
        Jedis jedis = new Jedis(redisHost, redisPort);
        Set<String> keys = jedis.keys("Todo:"+"*");
        List<TodoModel> todos = new ArrayList<>();
        int max = 0 ;
        for (String key : keys) {
            int intKey = Integer.parseInt(key.replace("Todo:",""));
            if(intKey > max){
                max = intKey;
            }
        }
        return max;
    }
}
