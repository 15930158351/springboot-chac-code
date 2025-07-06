package com.chac.handler;


import com.chac.context.RuleContext;
import com.chac.enums.FilterFailReasonEnum;
import org.apache.commons.lang3.tuple.Pair;

public abstract class AbstractRuleHandler {
    protected AbstractRuleHandler next;

    public void setNext(AbstractRuleHandler next) {
        this.next = next;
    }

    public Pair<Boolean, FilterFailReasonEnum> handle(RuleContext context) throws Exception {
        Pair<Boolean, FilterFailReasonEnum> handleRes = doHandle(context);
        if (!handleRes.getLeft()) {
            return Pair.of(false, handleRes.getRight());
        }
        if (next != null) {
            return next.handle(context);
        }
        return Pair.of(true, null);
    }

    protected abstract Pair<Boolean, FilterFailReasonEnum> doHandle(RuleContext context) throws Exception;

    public int getOrder() {
        return 0; // 默认顺序，可以在子类中重写
    }
}
