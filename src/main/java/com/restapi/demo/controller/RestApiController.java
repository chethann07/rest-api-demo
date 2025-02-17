package com.restapi.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restapi.demo.JavaUtil;
import java.time.LocalDateTime;
import java.util.*;
import com.restapi.demo.entity.Datasets;
import com.restapi.demo.entity.Status;
import com.restapi.demo.service.RestService;
import org.springframework.dao.DataIntegrityViolationException;
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

    @PostMapping("/create")
    public ResponseEntity<?> createDataset(@RequestBody Map<String, Object> requestBody) {
        Map<String, String> message = new LinkedHashMap<>();
        try{
            Datasets tempDataset = new Datasets();
            tempDataset.setDataSchema(JavaUtil.Deserialization(requestBody.get("dataSchema"), Map.class));
            tempDataset.setRouteConfig(JavaUtil.Deserialization(requestBody.get("routeConfig"), Map.class));
            tempDataset.setStatus(Status.valueOf((String) requestBody.get("status")));
            tempDataset.setCreatedBy(requestBody.get("createdBy").toString());
            tempDataset.setUpdatedBy(requestBody.get("updatedBy").toString());
            Datasets savedDataset = restService.createDataset(tempDataset);
            message.put("message", "Dataset created successfully");
            message.put("status", HttpStatus.OK.toString());
            message.put("insertedId", savedDataset.getId());
            message.put("timeStamp", LocalDateTime.now().toString());
            return ResponseEntity.ok(message);
        } catch (DataIntegrityViolationException e){
            message.put("message", "Dataset already exists");
            message.put("status", HttpStatus.CONFLICT.toString());
            return ResponseEntity.badRequest().body(message);
        } catch (IllegalArgumentException e) {
            message.put("message", "Status is invalid");
            message.put("status", HttpStatus.BAD_REQUEST.toString());
            return ResponseEntity.badRequest().body(message);
        }
        catch (NullPointerException e) {
            message.put("message", "Some of the fields are missing");
            message.put("status", HttpStatus.INTERNAL_SERVER_ERROR.toString());
            return ResponseEntity.internalServerError().body(message);
        }
        catch (Exception e){
            message.put("message", "Something went wrong");
            return  ResponseEntity.internalServerError().body(message);
        }
    }

    @GetMapping("/get/{id}")
    public Datasets getDatasetById(@PathVariable String id) {
        if(restService.getDatasetById(id) != null){
            return restService.getDatasetById(id);
        }
        return null;
    }

}