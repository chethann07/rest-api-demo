package com.restapi.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restapi.demo.JavaUtil;
import com.restapi.demo.entity.Datasets;
import com.restapi.demo.service.RestService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/datasets")
public class RestApiController {

    private final RestService restService;

    public RestApiController(RestService restService) {
        this.restService = restService;
    }

    @GetMapping("/get")
    public List<Datasets> getDatasets() {
        return restService.getAllDatasets();
    }

    @PostMapping("/create")
    public Datasets createDataset(@RequestBody String requestBody) {
        try{
            Datasets tempDataset = JavaUtil.Deserialization(requestBody, Datasets.class);
            return restService.createDataset(tempDataset);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse and save dataset", e);
        }
    }

    @GetMapping("/get{id}")
    public Datasets getDatasetById(@PathVariable String id) {
        if(restService.getDatasetById(id) != null){
            return restService.getDatasetById(id);
        }
        return null;
    }

}