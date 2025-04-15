package ru.jabka.tthistory.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.postgresql.util.PGobject;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.jabka.tthistory.model.TaskHistory;
import ru.jabka.tthistory.repository.mapper.TaskHistoryMapper;

import java.sql.SQLException;
import java.util.List;

@Log4j2
@Repository
@RequiredArgsConstructor
public class TaskHistoryRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final ObjectMapper objectMapper;
    private final TaskHistoryMapper historyMapper;

    private static final String INSERT = """
            INSERT INTO tt.task_history (task_id, data, created_by, moment)
            VALUES (:task_id, :data, :created_by, :moment)
            RETURNING *
            """;

    private static final String GET_BY_TASK_ID = """
            SELECT th.*
            FROM tt.task_history th
            WHERE th.task_id = :task_id
            ORDER BY moment DESC
            """;

    public TaskHistory insert(final TaskHistory taskHistory) {
        try {
            return jdbcTemplate.queryForObject(INSERT, taskHistoryToSql(taskHistory), historyMapper);
        } catch (Throwable e) {
            log.error("Ошибка при записи истории задачи");
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<TaskHistory> getByTaskId(final Long taskId) {
        return jdbcTemplate.query(GET_BY_TASK_ID, new MapSqlParameterSource("task_id", taskId), historyMapper);
    }

    private MapSqlParameterSource taskHistoryToSql(TaskHistory taskHistory) throws JsonProcessingException, SQLException {
        PGobject pGobject = new PGobject();
        pGobject.setType("jsonb");
        pGobject.setValue(objectMapper.writeValueAsString(taskHistory.data()));
        return new MapSqlParameterSource()
                .addValue("id", taskHistory.id())
                .addValue("task_id", taskHistory.taskId())
                .addValue("data", pGobject)
                .addValue("created_by", taskHistory.createdBy())
                .addValue("moment", taskHistory.moment());
    }
}