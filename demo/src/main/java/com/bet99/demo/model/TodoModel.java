package com.bet99.demo.model;

import com.bet99.demo.util.Constants;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@RedisHash("Todo")
@Builder
public class TodoModel {
    public enum State {
        TODO, IN_PROGRESS, DONE
    }
    @Id
    private long taskId;
    private String userId;
    private String description;
    private String dueDate;
    private State state;
}
