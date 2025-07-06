package com.chac.context;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chac.service.RecruitJdStrategy;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class JdPublishStrategyContext {

    private final Map<Integer, RecruitJdStrategy> strategyMap = new ConcurrentHashMap<>();

    @Autowired
    private List<RecruitJdStrategy> recruitJdStrategyList;

    @PostConstruct
    public void initMap() {
        this.strategyMap.clear();
        recruitJdStrategyList.forEach(recruitJdStrategy -> {
            List<Integer> productTypes = recruitJdStrategy.getProductTypes();
            for (Integer productType : productTypes) {
                strategyMap.put(productType, recruitJdStrategy);
            }
        });
    }

    public void publish(Map<String, Object> request) {
        Integer productType = (Integer) request.get("productType");
        strategyMap.get(productType).publish(request);
    }

}
