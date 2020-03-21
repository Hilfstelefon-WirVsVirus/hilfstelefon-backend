package org.wirvsvirushackathon.repository;

import javax.enterprise.context.ApplicationScoped;

import java.time.LocalDateTime;
import java.util.List;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.wirvsvirushackathon.domain.Task;

@ApplicationScoped
public class TasksRepository implements PanacheRepository<Task> {

    // A lot of useful default methods are automatically generated
    // TODO: Add specific non-generic methods

    public List<Task> getExamples() {
        return List.of(
                createTask(),
                createTask(),
                createTask()
        );
    }

    private Task createTask() {
        Task task = new Task();
        task.creationDate = LocalDateTime.now();
        task.transcription = "Lorem Ipsum";
        task.location = "Hamburg";

        return task;
    }
}
