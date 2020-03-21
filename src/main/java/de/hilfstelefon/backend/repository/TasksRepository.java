package de.hilfstelefon.backend.repository;

import javax.enterprise.context.ApplicationScoped;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import de.hilfstelefon.backend.domain.Task;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class TasksRepository implements PanacheRepository<Task> {

    // A lot of useful default methods are automatically generated
    // TODO: Add specific non-generic methods

    public List<Task> getExamples() {
        List<Task> tasks = new ArrayList<>();
        tasks.add(createTask());
        tasks.add(createTask());
        tasks.add(createTask());

        return tasks;
    }

    private Task createTask() {
        Task task = new Task();
        task.creationDate = LocalDateTime.now();
        task.transcription = "Lorem Ipsum";
        task.location = "Hamburg";

        return task;
    }
}
