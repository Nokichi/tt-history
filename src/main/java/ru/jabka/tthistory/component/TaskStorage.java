package ru.jabka.tthistory.component;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ru.jabka.tthistory.model.TaskHistory;
import ru.jabka.tthistory.service.TaskHistoryService;

@Log4j2
@Component
@RequiredArgsConstructor
public class TaskStorage {

    private final TaskHistoryService historyService;

    @RabbitListener(queues = "${app.rabbitmq.queue-tasks}")
    public void receiveAndStoreHistory(TaskHistory taskHistory) {
        log.info("Сохранение изменений по задаче id={}", taskHistory.taskId());
        historyService.create(taskHistory);
    }
}