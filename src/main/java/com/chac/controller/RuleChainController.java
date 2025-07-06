package com.chac.controller;


import com.chac.context.RuleContext;
import com.chac.enums.FilterFailReasonEnum;
import com.chac.service.RuleChainManager;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/rule")
public class RuleChainController {

    @Resource
    private RuleChainManager ruleChainManager;

    @PostMapping("/execute")
    public String executeRuleChain() {
        try {
            // 创建一个 RuleContext 对象并设置必要的参数
            RuleContext context = new RuleContext();
            // 设置上下文参数...

            // 执行规则链
            Pair<Boolean, FilterFailReasonEnum> result = ruleChainManager.execute(context);

            // 根据结果返回相应的响应
            if (result.getLeft()) {
                return "规则链执行成功";
            } else {
                return "规则链执行失败，原因：" + result.getRight().getDesc();
            }
        } catch (Exception e) {
            return "规则链执行异常：" + e.getMessage();
        }
    }

}
