package com.restapi.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restapi.demo.JavaUtil;
import java.util.*;
import com.restapi.demo.entity.Datasets;
import com.restapi.demo.entity.Status;
import com.restapi.demo.service.RestService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.crypto.Data;
import java.util.List;

@RestController
@RequestMapping("/datasets")
public class RestApiController {

    private final RestService restService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public RestApiController(RestService restService) {
        this.restService = restService;
    }

    @GetMapping("/get")
    public List<Datasets> getDatasets() {
        return restService.getAllDatasets();
    }

//    @PostMapping("/create")
//    public Datasets createDataset(@RequestBody String requestBody) {
//        try{
//            Datasets tempDataset = JavaUtil.Deserialization(requestBody, Datasets.class);
//            return restService.createDataset(tempDataset);
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to parse and save dataset", e);
//        }
//    }

    @PostMapping("/create")
    public ResponseEntity<?> createDataset(@RequestBody Map<String, Object> requestBody) {
        Map<String, String> message = new LinkedHashMap<>();
        try{
            Datasets tempDataset = new Datasets();
            tempDataset.setId(requestBody.get("id").toString());
            tempDataset.setDataSchema(JavaUtil.Deserialization(requestBody.get("dataSchema"), Map.class));
            tempDataset.setRouteConfig(JavaUtil.Deserialization(requestBody.get("routeConfig"), Map.class));
            tempDataset.setStatus(Status.valueOf((String) requestBody.get("status")));
            tempDataset.setCreatedBy(requestBody.get("createdBy").toString());
            tempDataset.setUpdatedBy(requestBody.get("updatedBy").toString());
            message.put("message", "Dataset created successfully");
            message.put("status", HttpStatus.OK.toString());
            message.put("Inserted ID", requestBody.get("id").toString());
            restService.createDataset(tempDataset);
            return ResponseEntity.ok(message);
        } catch (DataIntegrityViolationException e){
            return ResponseEntity.status(409).body("Dataset already exists");
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