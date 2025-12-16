package org.example.pipeline;

/*
 * @author huangwei
 * @emaill 1142488172@qq.com
 * @date 2025/12/16 13:22
 */

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BatchCouponCreator implements ContextHandler<BatchCouponContext> {

    @Override
    public boolean handle(BatchCouponContext context) {
        log.info("[{}],[{}] Start to create, current dictionary {}",Thread.currentThread().getName(), context.getSimpleName(), context.getCouponContext());

        for (int i = 0; i < context.getQuantity(); i++) {
            boolean result = doSomething(i);
            if (!result) {
                return false;
            }
        }

        return true;
    }

    private boolean doSomething(int index) {
        log.info("[{}],do something to {}",Thread.currentThread().getName(),index);
        return true;
    }
}
