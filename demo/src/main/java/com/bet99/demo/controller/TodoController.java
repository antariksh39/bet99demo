package com.bet99.demo.controller;

import com.bet99.demo.model.ApiResponse;
import com.bet99.demo.model.TodoModel;
import com.bet99.demo.service.impl.TodoServiceImpl;
import com.bet99.demo.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/bet99")
public class TodoController {

    @Autowired
    private TodoServiceImpl todoService;

    @GetMapping(value = "hello")
    @CrossOrigin
    public String getHello(){
        return "hello todo world";
    }

    @GetMapping(value = "todo")
    @CrossOrigin
    public ApiResponse getAll(){
        return todoService.getAllTodo();
    }

    @GetMapping(value = "todo/{id}")
    @CrossOrigin
    public ApiResponse getTodo(@PathVariable("id") int id){
        return todoService.getTodo(id);
    }

    @PostMapping(value = "todo")
    @CrossOrigin
    public ApiResponse saveTodo(@RequestBody TodoModel todo){
        if (!checkAuth(todo)){
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.setStatusCode(HttpStatus.UNAUTHORIZED.value());
            apiResponse.setMessage(Constants.TODO_UNAUTHORIZED);
            return apiResponse;
        }
        return todoService.saveTodo(todo);
    }

    @PutMapping(value = "todo")
    @CrossOrigin
    public ApiResponse updateTodo(@RequestBody TodoModel todo){
        if (!checkAuth(todo)){
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.setStatusCode(HttpStatus.UNAUTHORIZED.value());
            apiResponse.setMessage(Constants.TODO_UNAUTHORIZED);
            return apiResponse;
        }
        return todoService.updateTodo(todo);
    }

    @DeleteMapping(value = "todo/{id}")
    @CrossOrigin
    public ApiResponse deleteTodo(@PathVariable("id") int id){
        return todoService.deleteTodo(id);
    }

    private boolean checkAuth(TodoModel todo) {
        if(todo.getUserId().startsWith("BET99")){
            return true;
        }
        return false;
    }
}
