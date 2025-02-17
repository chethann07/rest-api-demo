package com.restapi.demo.service;

import com.restapi.demo.JavaUtil;
import com.restapi.demo.dao.DataAccessRepository;
import com.restapi.demo.entity.Datasets;
import com.restapi.demo.entity.Status;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class RestService {

    private final DataAccessRepository dataAccessRepository;
    private final EntityManager entityManager;
    Map<String, String> message = new HashMap<>();

    @Autowired
    public RestService(DataAccessRepository dataAccessRepository,  EntityManager entityManager) {
        this.dataAccessRepository = dataAccessRepository;
        this.entityManager = entityManager;
    }

    public List<Datasets> getAllDatasets() throws Exception{
        if(dataAccessRepository.findAll().isEmpty()){
            throw new Exception("Dataset is empty");
        }
        return dataAccessRepository.findAll();
    }

    @Transactional
    public Map<String, String> createDataset(Map<String, Object> requestBody) throws InputMismatchException {
        Datasets tempDataset = new Datasets();
        tempDataset.setId(requestBody.get("id").toString());
        tempDataset.setDataSchema(JavaUtil.Deserialization(requestBody.get("dataSchema"), Map.class));
        tempDataset.setRouteConfig(JavaUtil.Deserialization(requestBody.get("routeConfig"), Map.class));
        tempDataset.setStatus(Status.valueOf((String) requestBody.get("status")));
        tempDataset.setCreatedBy(requestBody.get("createdBy").toString());
        tempDataset.setUpdatedBy(requestBody.get("updatedBy").toString());
        if(!(requestBody.get("createdBy") instanceof String && requestBody.get("updatedBy") instanceof String)) {
            throw new InputMismatchException("createdBy and updatedBy must be strings");
        }

        if(requestBody.get("createdBy").toString().isBlank() && requestBody.get("createdBy").toString().isBlank()) {
            throw new InputMismatchException("createdBy and updatedBy cannot be empty");
        }
        entityManager.persist(tempDataset);
        message.put("message", "Dataset created successfully");
        message.put("status", HttpStatus.OK.toString());
        message.put("insertedId", requestBody.get("id").toString());
        message.put("timeStamp", LocalDateTime.now().toString());
        return message;
    }

    public Datasets getDatasetById(String id)  throws Exception {
        if(dataAccessRepository.findById(id).isEmpty()) {
            throw new Exception("Dataset with id " + id + " not found");
        }
        return dataAccessRepository.findById(id).get();
    }
}
