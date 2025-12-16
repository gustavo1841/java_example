package org.example.pipeline;

/*
 * @author huangwei
 * @emaill 1142488172@qq.com
 * @date 2025/12/16 13:24
 */

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BatchCouponSaver implements ContextHandler<BatchCouponContext> {

    @Override
    public boolean handle(BatchCouponContext context) {
        log.info("[{}],[{}] Start saving, data: {}",Thread.currentThread().getName(), context.getSimpleName(), context.getCouponCodes());
        return true;
    }
}