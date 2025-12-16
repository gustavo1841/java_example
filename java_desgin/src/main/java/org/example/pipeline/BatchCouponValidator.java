package org.example.pipeline;

/*
 * @author huangwei
 * @emaill 1142488172@qq.com
 * @date 2025/12/16 13:06
 */


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BatchCouponValidator implements ContextHandler<BatchCouponContext> {

    @Override
    public boolean handle(BatchCouponContext context) {
        log.info("[{}],[{}] Start validation", Thread.currentThread().getName(),context.getSimpleName());
        boolean condition = false;

        if (condition) {
            context.setErrorMsg("Validation failure");
            return false;
        }
        return true;
    }
}
