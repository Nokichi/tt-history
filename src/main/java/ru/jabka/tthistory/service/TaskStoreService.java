package ru.jabka.tthistory.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import ru.jabka.tthistory.model.TaskHistory;
import ru.jabka.tthistory.repository.TaskHistoryRepository;

@Log4j2
@Service
@RequiredArgsConstructor
public class TaskStoreService {

    private final TaskHistoryRepository historyRepository;

    @RabbitListener(queues = "${app.rabbitmq.queue-tasks}")
    public void receiveAndStoreHistory(TaskHistory taskHistory) {
        log.info("Сохранение изменений по задаче id={}", taskHistory.taskId());
        historyRepository.insert(taskHistory);
    }
}