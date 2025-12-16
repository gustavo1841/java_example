package org.example.pipeline;



import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/*
 * @author huangwei
 * @emaill 1142488172@qq.com
 * @date 2025/12/16 12:39
 */
@Getter
@Setter
public abstract class PipelineContext {

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private String errorMsg;

    public String getSimpleName() {
        return this.getClass().getSimpleName();
    }
}
