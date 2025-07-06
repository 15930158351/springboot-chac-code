package com.chac.service;

import com.chac.handler.AbstractRuleHandler;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.chac.enums.FilterFailReasonEnum;
import com.chac.context.RuleHandlerChainBuilder;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import com.chac.context.RuleContext;

@Component
public class RuleChainManager {
    private AbstractRuleHandler head;

    @Autowired
    public RuleChainManager(List<AbstractRuleHandler> handlers) {
        this.head = RuleHandlerChainBuilder.buildChain(handlers.stream()
                .sorted(Comparator.comparingInt(AbstractRuleHandler::getOrder)) // 需要你加排序方法
                .collect(Collectors.toList()));
    }

    public Pair<Boolean, FilterFailReasonEnum> execute(RuleContext context) throws Exception {
        return head.handle(context);
    }
}
