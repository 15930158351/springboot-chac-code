package com.chac.service;


import java.util.List;
import java.util.Map;

public interface RecruitJdStrategy {

    void publish(Map<String,Object> request);

    List<Integer> getProductTypes();

}
