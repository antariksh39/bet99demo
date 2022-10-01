package com.bet99.demo.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@RedisHash("Todo")
public class TodoModel {
    public enum State {
        TODO, IN_PROGRESS, DONE
    }
    @Id
    private long taskId;
    private String userId;
    private String description;
    private Date dueDate;
    private State state;
}
