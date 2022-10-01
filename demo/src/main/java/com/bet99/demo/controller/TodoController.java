package com.bet99.demo.controller;

import com.bet99.demo.model.ApiResponse;
import com.bet99.demo.model.TodoModel;
import com.bet99.demo.repository.TodoRepository;
import com.bet99.demo.service.TodoService;
import com.bet99.demo.service.impl.TodoServiceImpl;
import com.bet99.demo.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;

import java.util.Date;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping(value = "/bet99")
public class TodoController {

    @Autowired
    private TodoServiceImpl todoService;

    @GetMapping(value = "hello")
    public String getHello(){
        return "hello todo world";
    }

    @GetMapping(value = "todo")
    public ApiResponse getAll(){
        return todoService.getAllTodo();
    }

    @GetMapping(value = "todo/{id}")
    public ApiResponse getTodo(@PathVariable("id") int id){
        return todoService.getTodo(id);
    }

    @PostMapping(value = "todo")
    public ApiResponse saveTodo(@RequestBody TodoModel todo){
        return todoService.saveTodo(todo);
    }

    @PutMapping(value = "todo")
    public ApiResponse updateTodo(@RequestBody TodoModel todo){
        return todoService.updateTodo(todo);
    }

    @DeleteMapping(value = "todo/{id}")
    public ApiResponse deleteTodo(@PathVariable("id") int id){
        return todoService.deleteTodo(id);
    }
}
