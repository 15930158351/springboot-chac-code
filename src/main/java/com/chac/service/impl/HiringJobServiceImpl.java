package com.chac.service.impl;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.chac.enums.ProductTypeEnum;
import com.chac.service.RecruitJdStrategy;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service(value = "hiringJobServiceImpl")
@Slf4j
public class HiringJobServiceImpl implements RecruitJdStrategy {


    @Override
    public void publish(Map<String, Object> request) {
        System.out.println("HiringJobServiceImpl publish method called with request: " + JSON.toJSONString(request));
    }

    @Override
    public List<Integer> getProductTypes() {
        return Lists.newArrayList(ProductTypeEnum.HIRING_JOB.getCode());
    }


}
