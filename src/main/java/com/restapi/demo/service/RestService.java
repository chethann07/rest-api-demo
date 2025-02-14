package com.restapi.demo.service;

import com.restapi.demo.dao.DataAccessRepository;
import com.restapi.demo.entity.Datasets;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RestService {

    private final DataAccessRepository dataAccessRepository;
    private final EntityManager entityManager;

    @Autowired
    public RestService(DataAccessRepository dataAccessRepository, EntityManager entityManager) {
        this.dataAccessRepository = dataAccessRepository;
        this.entityManager = entityManager;
    }

    public List<Datasets> getAllDatasets(){
        return dataAccessRepository.findAll();
    }

    @Transactional
    public void createDataset(Datasets dataset){
        entityManager.persist(dataset);
    }

    public Datasets getDatasetById(String id){
        if(dataAccessRepository.findById(id).isPresent()) {
            return dataAccessRepository.findById(id).get();
        }
        return null;
    }
}
