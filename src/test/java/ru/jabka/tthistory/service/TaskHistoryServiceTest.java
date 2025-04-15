package ru.jabka.tthistory.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.jabka.tthistory.model.TaskData;
import ru.jabka.tthistory.model.TaskHistory;
import ru.jabka.tthistory.model.TaskStatus;
import ru.jabka.tthistory.repository.TaskHistoryRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class TaskHistoryServiceTest {

    @Mock
    private TaskHistoryRepository historyRepository;

    @InjectMocks
    private TaskHistoryService taskHistoryService;

    @Test
    void getByTaskId_success() {
        long taskId = 17L;
        List<TaskHistory> historyList = new ArrayList<>();
        TaskData taskData = new TaskData(
                "Test",
                "Desc test",
                TaskStatus.TO_DO,
                LocalDate.of(2030, 11, 11),
                2L
        );
        for (int i = 0; i < 3; i++) {
            historyList.add(TaskHistory.builder()
                    .id((long) i)
                    .taskId(taskId)
                    .data(taskData)
                    .moment(LocalDateTime.now())
                    .createdBy((long) i + 2)
                    .build());
        }
        Mockito.when(historyRepository.getByTaskId(taskId)).thenReturn(historyList);
        List<TaskHistory> result = taskHistoryService.getByTaskId(taskId);
        Assertions.assertEquals(historyList, result);
        Mockito.verify(historyRepository).getByTaskId(taskId);
    }
}