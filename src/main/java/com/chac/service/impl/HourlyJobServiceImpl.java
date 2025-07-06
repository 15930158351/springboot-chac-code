package com.chac.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;

import com.chac.enums.ProductTypeEnum;
import com.chac.service.RecruitJdStrategy;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service(value = "hourlyJobServiceImpl")
@Slf4j
public class HourlyJobServiceImpl implements RecruitJdStrategy {


    @Override
    public void publish(Map<String, Object> request) {
        System.out.println("HourlyJobServiceImpl publish method called with request: " + JSON.toJSONString(request));
    }

    @Override
    public List<Integer> getProductTypes() {
        return Lists.newArrayList(ProductTypeEnum.HOURLY_JOB.getCode());
    }


}
