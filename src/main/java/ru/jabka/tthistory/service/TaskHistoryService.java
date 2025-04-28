package ru.jabka.tthistory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.jabka.tthistory.exception.BadRequestException;
import ru.jabka.tthistory.model.TaskHistory;
import ru.jabka.tthistory.repository.TaskHistoryRepository;

import java.util.List;

import static java.util.Optional.ofNullable;

@Service
@RequiredArgsConstructor
public class TaskHistoryService {

    private final TaskHistoryRepository historyRepository;

    @Transactional(rollbackFor = Throwable.class)
    public TaskHistory create(final TaskHistory taskHistory) {
        validateTaskHistory(taskHistory);
        return historyRepository.insert(taskHistory);
    }

    @Transactional(readOnly = true)
    public List<TaskHistory> getByTaskId(final Long taskId) {
        return historyRepository.getByTaskId(taskId);
    }

    private void validateTaskHistory(final TaskHistory taskHistory) {
        ofNullable(taskHistory).orElseThrow(() -> new BadRequestException("Заполните данные истории задачи"));
        ofNullable(taskHistory.taskId()).orElseThrow(() -> new BadRequestException("Заполните id задачи"));
        ofNullable(taskHistory.data()).orElseThrow(() -> new BadRequestException("Заполните данные изменений задачи"));
        ofNullable(taskHistory.createdBy()).orElseThrow(() -> new BadRequestException("Заполните id автора изменений задачи"));
        ofNullable(taskHistory.moment()).orElseThrow(() -> new BadRequestException("Заполните время изменений задачи"));
    }
}