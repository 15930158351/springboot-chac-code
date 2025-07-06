package com.chac.context;


import com.chac.handler.AbstractRuleHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class RuleHandlerChainBuilder {
    public static AbstractRuleHandler buildChain(List<AbstractRuleHandler> handlers) {
        for (int i = 0; i < handlers.size() - 1; i++) {
            handlers.get(i).setNext(handlers.get(i + 1));
            log.info("设置责任链: " + handlers.get(i).getClass().getSimpleName() + " -> " + handlers.get(i + 1).getClass().getSimpleName());
        }
        return handlers.isEmpty() ? null : handlers.get(0);
    }
}
