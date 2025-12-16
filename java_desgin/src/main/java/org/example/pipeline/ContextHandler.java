package org.example.pipeline;

/*
 * @author huangwei
 * @emaill 1142488172@qq.com
 * @date 2025/12/16 12:53
 */
public interface ContextHandler<T extends PipelineContext> {
  /**
   * Handle context
   *
   * @param context context
   * @return result
   */
  boolean handle(T context);
}
