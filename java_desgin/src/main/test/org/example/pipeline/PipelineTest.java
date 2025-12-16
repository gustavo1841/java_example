package org.example.pipeline;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Resource;

/*
 * @author huangwei
 * @emaill 1142488172@qq.com
 * @date 2025/12/16 13:32
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@Slf4j
public class PipelineTest {
    @Resource(name = "couponExecutor")
    private PipelineExecutor couponExecutor;

    @Test
    public void testCouponExecute() {
        BatchCouponContext couponContext = build();
        log.info("=========开始同步执行======");
        boolean result1 = couponExecutor.acceptSync(couponContext);

        if (!result1){
            log.error("[{}] occur error，error message={}", couponContext.getSimpleName(), couponContext.getErrorMsg());
        }
        log.info("1号方法同步执行完毕");

        log.info("=========开始异步执行======");
        couponExecutor.acceptAsync(couponContext, (context, success) -> {
            if (!success) {
                log.error("[{}] occur error，error message={}", couponContext.getSimpleName(), couponContext.getErrorMsg());
            }else{
                log.info("1号异步执行完毕");
            }
        });
        couponExecutor.acceptAsync(couponContext, (context, success) -> {
            if (!success) {
                log.error("[{}] occur error，error message={}", couponContext.getSimpleName(), couponContext.getErrorMsg());
            }else{
                log.info("2号异步执行完毕");
            }
        });
        log.info("异步执行完毕！");
    }

    private BatchCouponContext build() {
        BatchCouponContext couponContext = BatchCouponContext.builder()
                .quantity(10)
                .couponContext("{\"dictionary\":\"123\"}")
                .build();
        return couponContext;

    }
}
