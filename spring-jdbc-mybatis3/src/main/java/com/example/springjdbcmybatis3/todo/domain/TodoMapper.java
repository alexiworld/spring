package com.example.springjdbcmybatis3.todo.domain;

import org.apache.ibatis.annotations.*;
import org.springframework.data.jdbc.mybatis.MyBatisContext;

import java.util.Collection;
import java.util.Optional;

//TODO: findAll and count have been left uncommented to test the co-existence of java and xml
//MyBatis mapper for the same class - ToDo. Devs can comment all methods in java mapper or all
//methods in xml mapper, or ensure both exist without conflicting methods.
@Mapper
public interface TodoMapper {

    /*
    @Select("SELECT todo_id, todo_title, finished, created_at FROM todo WHERE todo_id = #{id}")
    @Results(id = "todoRes", value = {
            @Result(property = "todoId", column = "todo_id"),
            @Result(property = "todoTitle", column = "todo_title"),
            @Result(property = "finished", column = "finished"),
            @Result(property = "createdAt", column = "created_at")
    })
    Optional<Todo> findById(MyBatisContext context);
     */

    // @Select("SELECT 1 as todo_id, todo_title, finished, created_at FROM todo")
    @Select("SELECT todo_id, todo_title, finished, created_at FROM todo")
    @Results(id = "todosRes", value = {
            @Result(property = "todoId", column = "todo_id"),
            @Result(property = "todoTitle", column = "todo_title"),
            @Result(property = "finished", column = "finished"),
            @Result(property = "createdAt", column = "created_at")
    })
    Collection<Todo> findAll();

    /*
    // Option#1: Calling the mapper directly
    // @Insert("INSERT INTO todo (todo_title, finished, created_at) VALUES (#{instance.todoTitle}, #{instance.finished}, #{instance.createdAt})")
    // @Options(useGeneratedKeys = true, keyProperty = "instance.todoId")
    // void insert(MyBatisContext context);

    // Option#2: Calling the mapper indirectly from within Spring Data JDBC.
    @Insert("INSERT INTO todo (todo_title, finished, created_at) VALUES (#{instance.todoTitle}, #{instance.finished}, #{instance.createdAt})")
    // @Options(useGeneratedKeys = true, keyProperty = "row.id")
    // @Options(useGeneratedKeys = true, keyProperty = "instance.todoId")
    // @Options(useGeneratedKeys = true, keyProperty = "instance.todoId,id")
    // This must be set to id, which matches MyBatisContext's id field. Without this value set specifically to "id", MyBatisContext.getId() will
    // return null, an exception will be raised, and Spring Data JDBC will switch to the next in line data access strategy (JDBC's TodoRepository)
    // to perform the operation. The end result would be two tween records inserted in DB. So don't forget/ignore to set the keyProperty to id.
    // With the XML mapper things are very different, as well when the mapper is being used outside the Spring Data JDBC as can be seen in Vehicle
    // mappers.
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Todo insert(MyBatisContext context);

    //instance is member of MyBatisContext and is populated by Spring Data JDBC with Todo object to be updated.
    @Update("UPDATE todo SET todo_title = #{instance.todoTitle}, finished = #{instance.finished}, created_at = #{instance.createdAt} WHERE todo_id = #{instance.id}")
    Todo update(MyBatisContext context);

    @Delete("DELETE FROM todo WHERE todo_id = #{id}")
    void delete(MyBatisContext context);
    */

    @Select("SELECT COUNT(*) FROM todo WHERE finished = 'FALSE'")
    //@Select("SELECT COUNT(*) FROM todo WHERE finished = 'f'")
    long count(MyBatisContext context);

}