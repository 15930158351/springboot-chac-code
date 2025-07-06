package com.chac.handler;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;
import com.chac.enums.FilterFailReasonEnum;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.chac.context.RuleContext;

import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class UserRuleHandler extends AbstractRuleHandler {
//    private final RuleMatcher matcher = new RuleMatcher();
//    private final Map<String, List<ReRuleExpression>> userFieldRulesFromDb = new ConcurrentHashMap<>(); // key: 字段名, value: QL表达式
//
//    @Resource
//    private ReRuleExpressionMapper reRuleExpressionMapper;

    @PostConstruct
    public void init() {
        log.info("本机开始刷新缓存规则数据。。。。。UserRuleHandler");
//        userFieldRulesFromDb.clear();
//
//        QueryWrapper<ReRuleExpression> queryWrapper = new QueryWrapper();
//        queryWrapper.eq("rule_domain", "user");
//        //规则类型 1小时工二筛、2小时工PK
//        queryWrapper.eq("rule_type", 1);
//        queryWrapper.eq("status", 1);
//        List<ReRuleExpression> reRuleList = reRuleExpressionMapper.selectList(queryWrapper);
//        for (ReRuleExpression reRule : reRuleList) {
//            List<ReRuleExpression> qlExpressList = userFieldRulesFromDb.getOrDefault(reRule.getFieldName(), new ArrayList<>());
//            qlExpressList.add(reRule);
//            userFieldRulesFromDb.put(reRule.getFieldName(), qlExpressList);
//        }
//        log.info("userFieldRulesFromDb: " + JSON.toJSONString(userFieldRulesFromDb));
    }

    @Override
    protected Pair<Boolean, FilterFailReasonEnum> doHandle(RuleContext context) throws Exception {
        String cityCode = context.getCityCode();
        System.out.println("UserRuleHandler doHandle cityCode: " + cityCode);
//        Map<String, Object> variables = new HashMap<>();
//        variables.put("rule", context.getRule());
//        variables.put("data", context.getData());
//
//        for (Map.Entry<String, List<ReRuleExpression>> entry : userFieldRulesFromDb.entrySet()) {
//            String field = entry.getKey();
//            List<ReRuleExpression> qlExpressList = entry.getValue();
//            //按照城市分区 优先执行cityCode相同的，如果不存在 再执行cityCode为-1的规则
//            List<ReRuleExpression> citySpecificRules = new ArrayList<>();
//            List<ReRuleExpression> defaultRules = new ArrayList<>();
//            for (ReRuleExpression rule : qlExpressList) {
//                if (rule.getCityCode().equals(cityCode)) {
//                    citySpecificRules.add(rule);
//                } else if (rule.getCityCode().equals("-1")) {
//                    defaultRules.add(rule);
//                }
//            }
//            try {
//                if (CollectionUtils.isNotEmpty(citySpecificRules)) {
//                    ReRuleExpression selectedRule = citySpecificRules.get(0); // 理论上来说 只有1个
//                    if (!matcher.match(variables, selectedRule.getQlExpress())) {
//                        log.info("User field match cityRule failed: " + field + " → variables: " + JSON.toJSONString(variables));
//                        FilterFailReasonEnum failEnum = FilterFailReasonEnum.getByFieldName(field);
//                        return Pair.of(false, failEnum);
//                    }
//                } else {
//                    ReRuleExpression defaultRule = defaultRules.get(0); // 理论上来说 只有1个
//                    if (!matcher.match(variables, defaultRule.getQlExpress())) {
//                        log.info("User field match rule failed: " + field + " → variables: " + JSON.toJSONString(variables));
//                        FilterFailReasonEnum failEnum = FilterFailReasonEnum.getByFieldName(field);
//                        return Pair.of(false, failEnum);
//                    }
//                }
//            } catch (QLException e) {
//                log.error("Error processing user rule for field: " + field + JSON.toJSONString(e));
//                return Pair.of(false, FilterFailReasonEnum.INTERNAL_ERROR);
//            }
//
//        }
        return Pair.of(true, null);
    }

    @Override
    public int getOrder() {
        return 1; // 可以根据需要调整顺序
    }
}
