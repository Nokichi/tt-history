package ru.jabka.tthistory.repository.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.postgresql.util.PGobject;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.jabka.tthistory.model.TaskData;
import ru.jabka.tthistory.model.TaskHistory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

@Component
@RequiredArgsConstructor
public class TaskHistoryMapper implements RowMapper<TaskHistory> {

    private final ObjectMapper objectMapper;

    @Override
    public TaskHistory mapRow(ResultSet rs, int rowNum) throws SQLException {
        PGobject data = rs.getObject("data", PGobject.class);
        TaskData taskData;
        try {
            taskData = objectMapper.readValue(data.getValue(), TaskData.class);
        } catch (JsonProcessingException e) {
            throw new SQLException("Ошибка чтения данных из поля data с типом JSONB", e);
        }
        return TaskHistory.builder()
                .id(rs.getLong("id"))
                .taskId(rs.getLong("task_id"))
                .data(taskData)
                .createdBy(rs.getLong("created_by"))
                .moment(rs.getObject("moment", Timestamp.class).toLocalDateTime())
                .build();
    }
}