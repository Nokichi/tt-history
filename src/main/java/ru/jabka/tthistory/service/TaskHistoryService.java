package ru.jabka.tthistory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.jabka.tthistory.model.TaskHistory;
import ru.jabka.tthistory.repository.TaskHistoryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskHistoryService {

    private final TaskHistoryRepository historyRepository;

    @Transactional(readOnly = true)
    public List<TaskHistory> getByTaskId(final Long taskId) {
        return historyRepository.getByTaskId(taskId);
    }
}