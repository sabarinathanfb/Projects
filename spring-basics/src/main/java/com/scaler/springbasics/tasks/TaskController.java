package com.scaler.springbasics.tasks;


import org.springframework.boot.autoconfigure.integration.IntegrationAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    List<Task> taskList = new ArrayList<>();
    private int nextTaskId = 1;


    @PostMapping("")
    Task createTask(@RequestBody Task task){
        task.setId(nextTaskId++);
        taskList.add(task);
        return task;
    }

    @GetMapping("")
    List<Task> getAllTasks(){
        return taskList;
    }

    @GetMapping("/{id}")
    Task getTaskById(@PathVariable("id") Integer id){
        var foundTask = taskList.stream().filter(
                task -> task.getId().equals(id)
        ).findFirst().orElse(null);

        return foundTask;
    }


    @PatchMapping("completed/{taskId}")
    void markTaskCompleted(@PathVariable Integer taskId) {
        try {
            for (Task task : taskList) {
                if (task.getId().equals(taskId)) {
                    task.setCompleted(true);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Task ID not found: " + taskId);
        }

    }
    @PatchMapping("edit/{taskId}")
    void editTaskName(@PathVariable Integer taskId, @RequestBody Task updatedTask) {
        try {
            for (Task task : taskList) {
                if (task.getId().equals(taskId)) {
                    task.setName(updatedTask.getName());
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Task ID not found: " + taskId);
        }

    }

    @DeleteMapping("/delete")
    void deleteTask(@RequestParam Integer taskId){

        boolean removed = taskList.removeIf(task -> task.getId().equals(taskId));
        if (!removed) {
            // If task with the given ID is not found, throw a 404 error
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found with ID: " + taskId);
        }
    }

    @DeleteMapping("/deleteAll")
    void deleteAllTasks() {

        taskList.clear(); // Clears all tasks in the list
    }
}
