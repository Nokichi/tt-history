package ru.jabka.tthistory.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.jabka.tthistory.exception.BadRequestException;
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
    void create_success() {
        final TaskHistory taskHistory = TaskHistory.builder()
                .id(1L)
                .taskId(2L)
                .data(new TaskData(
                        "title",
                        "description",
                        TaskStatus.IN_PROGRESS,
                        LocalDate.now(),
                        3L))
                .createdBy(4L)
                .moment(LocalDateTime.now())
                .build();
        Mockito.when(historyRepository.insert(taskHistory)).thenReturn(taskHistory);
        TaskHistory result = taskHistoryService.create(taskHistory);
        Assertions.assertEquals(taskHistory, result);
        Mockito.verify(historyRepository).insert(taskHistory);
    }

    @Test
    void create_error_nullTaskHistory() {
        final TaskHistory taskHistory = null;
        final BadRequestException exception = Assertions.assertThrows(
                BadRequestException.class,
                () -> taskHistoryService.create(taskHistory)
        );
        Assertions.assertEquals("Заполните данные истории задачи", exception.getMessage());
        Mockito.verify(historyRepository, Mockito.never()).insert(Mockito.any());
    }

    @Test
    void create_error_nullTaskId() {
        final TaskHistory taskHistory = TaskHistory.builder()
                .data(new TaskData(
                        "title",
                        "description",
                        TaskStatus.IN_PROGRESS,
                        LocalDate.now(),
                        3L))
                .createdBy(4L)
                .moment(LocalDateTime.now())
                .build();
        ;
        final BadRequestException exception = Assertions.assertThrows(
                BadRequestException.class,
                () -> taskHistoryService.create(taskHistory)
        );
        Assertions.assertEquals("Заполните id задачи", exception.getMessage());
        Mockito.verify(historyRepository, Mockito.never()).insert(Mockito.any());
    }

    @Test
    void create_error_nullData() {
        final TaskHistory taskHistory = TaskHistory.builder()
                .taskId(2L)
                .createdBy(4L)
                .moment(LocalDateTime.now())
                .build();
        final BadRequestException exception = Assertions.assertThrows(
                BadRequestException.class,
                () -> taskHistoryService.create(taskHistory)
        );
        Assertions.assertEquals("Заполните данные изменений задачи", exception.getMessage());
        Mockito.verify(historyRepository, Mockito.never()).insert(Mockito.any());
    }

    @Test
    void create_error_nullCreatedBy() {
        final TaskHistory taskHistory = TaskHistory.builder()
                .taskId(2L)
                .data(new TaskData(
                        "title",
                        "description",
                        TaskStatus.IN_PROGRESS,
                        LocalDate.now(),
                        3L))
                .moment(LocalDateTime.now())
                .build();
        final BadRequestException exception = Assertions.assertThrows(
                BadRequestException.class,
                () -> taskHistoryService.create(taskHistory)
        );
        Assertions.assertEquals("Заполните id автора изменений задачи", exception.getMessage());
        Mockito.verify(historyRepository, Mockito.never()).insert(Mockito.any());
    }

    @Test
    void create_error_nullMoment() {
        final TaskHistory taskHistory = TaskHistory.builder()
                .taskId(2L)
                .data(new TaskData(
                        "title",
                        "description",
                        TaskStatus.IN_PROGRESS,
                        LocalDate.now(),
                        3L))
                .createdBy(4L)
                .build();
        final BadRequestException exception = Assertions.assertThrows(
                BadRequestException.class,
                () -> taskHistoryService.create(taskHistory)
        );
        Assertions.assertEquals("Заполните время изменений задачи", exception.getMessage());
        Mockito.verify(historyRepository, Mockito.never()).insert(Mockito.any());
    }

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