package org.example.pipeline;

/*
 * @author huangwei
 * @emaill 1142488172@qq.com
 * @date 2025/12/16 13:24
 */

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BatchCouponNotifier implements ContextHandler<BatchCouponContext> {

    @Override
    public boolean handle(BatchCouponContext context) {
        log.info("[{}],[{}] Start notice, data: {}",Thread.currentThread().getName(), context.getSimpleName(), context.getCouponCodes());
        return true;
    }
}