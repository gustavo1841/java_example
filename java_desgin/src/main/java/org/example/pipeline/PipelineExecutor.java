package org.example.pipeline;

/*
 * @author huangwei
 * @emaill 1142488172@qq.com
 * @date 2025/12/16 13:25
 */

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

@Slf4j
public class PipelineExecutor implements ApplicationContextAware, InitializingBean {

    // 配置表
    private Map<Class<? extends PipelineContext>, List<Class<? extends ContextHandler<? extends PipelineContext>>>> contextMaps;
    // 路由表
    private Map<Class<? extends PipelineContext>, List<? extends ContextHandler<? super PipelineContext>>> routeMaps;

    private ApplicationContext applicationContext;

    public PipelineExecutor(Map<Class<? extends PipelineContext>, List<Class<? extends ContextHandler<? extends PipelineContext>>>> contextMaps) {
        this.contextMaps = contextMaps;
    }

    private Map<Class<? extends PipelineContext>, List<? extends ContextHandler<? super PipelineContext>>> generateRouteMaps(Map<Class<? extends PipelineContext>, List<Class<? extends ContextHandler<? extends PipelineContext>>>> contextClassMaps) {
        return contextClassMaps.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, this::getHandlerBean));
    }

    public List<? extends ContextHandler<? super PipelineContext>> getHandlerBean(Map.Entry<Class<? extends PipelineContext>, List<Class<? extends ContextHandler<? extends PipelineContext>>>> entry) {
        return entry.getValue()
                .stream()
                .map(item -> (ContextHandler<PipelineContext>) applicationContext.getBean(item))
                .collect(Collectors.toList());
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterPropertiesSet() {
        // 根据配置生成路由表
        routeMaps = generateRouteMaps(contextMaps);
    }

    public boolean acceptSync(PipelineContext context) {
        Assert.notNull(context, "Pipeline context is required");

        // 获取处理类型
        Class<? extends PipelineContext> contextType = context.getClass();
        // 获取该类型所有管道
        List<? extends ContextHandler<? super PipelineContext>> contextHandlers = routeMaps.get(contextType);

        if (CollectionUtils.isEmpty(contextHandlers)) {
            log.error("Pipeline {} is null ", contextType);
            return false;
        }

        boolean lastSuccess = true;

        for (ContextHandler<? super PipelineContext> handler : contextHandlers) {
            try {
                // 拿到当前管道处理结果
                lastSuccess = handler.handle(context);
            } catch (Throwable ex) {
                lastSuccess = false;
                log.error("[{}] occur error，handler={}", context.getSimpleName(), handler.getClass().getSimpleName(), ex);
            }

            if (!lastSuccess) {
                break;
            }
        }

        return lastSuccess;
    }

    @Resource
    private ThreadPoolTaskExecutor pipelineThreadPool;

    public void acceptAsync(PipelineContext context, BiConsumer<PipelineContext, Boolean> callback) {
        pipelineThreadPool.execute(() -> {
            boolean success = acceptSync(context);

            if (callback != null) {
                callback.accept(context, success);
            }
        });
    }

}
