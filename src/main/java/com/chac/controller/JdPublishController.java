package com.chac.controller;

import com.chac.context.JdPublishStrategyContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/api/jd")
public class JdPublishController {

    @Resource
    private JdPublishStrategyContext jdPublishStrategyContext;

    @PostMapping("/publish")
    public ResponseEntity<?> publishJD(@RequestBody Map<String, Object> request) {
        // Logic to publish JD
        jdPublishStrategyContext.publish(request);
        return ResponseEntity.ok("JD published successfully");
    }
}
