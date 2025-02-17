package com.restapi.demo.controller;

import java.util.*;
import com.restapi.demo.entity.Datasets;
import com.restapi.demo.service.RestService;
import org.springframework.http.ResponseEntity;
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
    public List<Datasets> getDatasets() throws Exception {
        return restService.getAllDatasets();
    }

    @PostMapping("/create")
    public ResponseEntity<?> createDataset(@RequestBody Map<String, Object> requestBody) throws InputMismatchException {
        return ResponseEntity.ok(restService.createDataset(requestBody));
    }

    @GetMapping("/get/{id}")
    public Datasets getDatasetById(@PathVariable String id) throws Exception {
        return restService.getDatasetById(id);
    }

}