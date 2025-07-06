package com.chac.context;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RuleContext {


    private String cityCode;

    private Map<String, Object> rule;

    private Map<String, Object> data;

    public Map<String, Object> getRule() {
        return rule;
    }

    public Map<String, Object> getData() {
        return data;
    }


}
