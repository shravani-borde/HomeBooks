package com.example.HomeBooks.Controller;

import com.example.HomeBooks.recommendation.PythonRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private PythonRunner pythonRunner;

    @GetMapping
    public List<Long> test(){

        return pythonRunner.getRecommendedBookIds(7L);

    }

}