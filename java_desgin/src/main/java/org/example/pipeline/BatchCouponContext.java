package org.example.pipeline;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/*
 * @author huangwei
 * @emaill 1142488172@qq.com
 * @date 2025/12/16 12:54
 */
@Data
@Builder
public class BatchCouponContext extends PipelineContext {

    private Double lastIndex;

    private Integer quantity;

    private String couponContext;


    private List<String> couponCodes = new ArrayList<>();
}
