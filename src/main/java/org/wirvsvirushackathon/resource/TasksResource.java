package org.wirvsvirushackathon.resource;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import io.quarkus.panache.common.Page;
import org.wirvsvirushackathon.domain.Task;
import org.wirvsvirushackathon.repository.TasksRepository;

@Path("/tasks")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TasksResource {

    @Inject
    TasksRepository tasksRepository;

    @GET
    public java.util.List<Task> getAll(@QueryParam("index") Integer index) {
        Page page = Page.of(index != null ? index : 0, 20);

        return tasksRepository.getExamples();
        //return tasksRepository.findAll().page(page).list();
    }
}
