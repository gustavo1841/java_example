package org.example.pipeline;

/*
 * @author huangwei
 * @emaill 1142488172@qq.com
 * @date 2025/12/16 13:24
 */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class PipelineRouteConfig {

    Map<Class<? extends PipelineContext>, List<Class<? extends ContextHandler<? extends PipelineContext>>>> maps = new HashMap<>();

    @Bean("couponExecutor")
    public PipelineExecutor getBatchCouponPipelineExecutor() {
        setBatchCouponPipeline();
        return new PipelineExecutor(maps);
    }

    public void setBatchCouponPipeline() {
        // key为上下文类型，value为处理器列表，按顺序执行
        maps.put(BatchCouponContext.class,
                Arrays.asList(
                        BatchCouponValidator.class,
                        BatchCouponCreator.class,
                        BatchCouponSaver.class,
                        BatchCouponNotifier.class
                ));
    }
}
