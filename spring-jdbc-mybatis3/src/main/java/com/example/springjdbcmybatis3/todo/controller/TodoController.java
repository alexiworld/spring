package com.example.springjdbcmybatis3.todo.controller;

import com.example.springjdbcmybatis3.todo.domain.Todo;
import com.example.springjdbcmybatis3.todo.domain.TodoMapper;
import com.example.springjdbcmybatis3.todo.repository.TodoRepository;
import com.example.springjdbcmybatis3.vehicle.model.Vehicle;
import com.example.springjdbcmybatis3.vehicle.model.VehicleResponse;
import com.example.springjdbcmybatis3.vehicle.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jdbc.mybatis.MyBatisContext;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/rest/v1/todo")
public class TodoController {

    @Autowired
    TodoRepository todoRepository;

    @Autowired
    TodoMapper todoMapper;

    @PostMapping
    public Todo saveTodo(@RequestBody Todo todo) {
        // Option#1: Use MyBatis mappers directly.
        // MyBatisContext myBatisContext = new MyBatisContext(null, todo, Todo.class);
        // todoMapper.insert(myBatisContext);
        // return todo;

        // Option#2: Use MyBatis mappers from within the JDBC repositories. Since todo comes with no id, the operation
        //  would be INSERT. JDBC will call todoMapper.insert if present, or no exception. Otherwise, Spring Data JDBC
        //  will use repo's save method.
        Todo todoRet = todoRepository.save(todo);
        return todoRet;
    }

    @PutMapping("/{id}")
    public Todo updateTodo(@PathVariable Integer id, @RequestBody Todo todo) {
        todo.setTodoId(id);
        // Since todo comes with id, then the operation would be UPDATE. JDBC will call todoMapper.update if present,
        //  or no exception. Otherwise, Spring Data JDBC will use repo's save method to update the record.
        Todo todoRet = todoRepository.save(todo);
        return todoRet;
    }

    // Demonstrates fetching vehicle. The returned vehicle (if any found) must have all fields decrypted.
    @GetMapping("/")
    public List<Todo> getTodos() {
        return StreamSupport.stream(todoRepository.findAll().spliterator(), false).toList();
    }

    @GetMapping("/{id}")
    public Todo getTodo(@PathVariable("id") Integer id){
        return todoRepository.findById(id).get();
    }

    // Demonstrates fetching vehicle. The returned vehicle (if any found) must have all fields decrypted.
    @GetMapping("/count")
    public Long count() {
        return todoRepository.count();
    }

    @DeleteMapping("/{id}")
    public void deleteTodo(@PathVariable("id") Integer id){
        todoRepository.deleteById(id);
    }

}