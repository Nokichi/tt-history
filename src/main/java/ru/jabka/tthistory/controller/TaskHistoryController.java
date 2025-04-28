package ru.jabka.tthistory.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.jabka.tthistory.model.TaskHistory;
import ru.jabka.tthistory.service.TaskHistoryService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "История задач")
@RequestMapping("/api/v1/task")
public class TaskHistoryController {

    private final TaskHistoryService taskHistoryService;

    @GetMapping("/{id}")
    public List<TaskHistory> getByTaskId(@PathVariable final Long id) {
        return taskHistoryService.getByTaskId(id);
    }
}