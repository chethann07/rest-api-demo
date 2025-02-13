package com.restapi.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restapi.demo.entity.Datasets;
import com.restapi.demo.service.RestService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/datasets")
public class RestApiController {

    private final RestService restService;
    private final ObjectMapper objectMapper;

    public RestApiController(RestService restService,  ObjectMapper objectMapper) {
        this.restService = restService;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/get")
    public List<Datasets> getDatasets() {
        return restService.getAllDatasets();
    }

    @PostMapping("/create")
    public Datasets createDataset(@RequestBody String json) {
        Datasets tempDataset = new Datasets();
        try{
            tempDataset = objectMapper.readValue(json, Datasets.class);
            return restService.createDataset(tempDataset);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse and save dataset", e);
        }
    }

}