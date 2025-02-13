package com.restapi.demo.service;

import com.restapi.demo.dao.DataAccessRepository;
import com.restapi.demo.entity.Datasets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RestService {

    private final DataAccessRepository dataAccessRepository;

    @Autowired
    public RestService(DataAccessRepository dataAccessRepository) {
        this.dataAccessRepository = dataAccessRepository;
    }

    public List<Datasets> getAllDatasets(){
        return dataAccessRepository.findAll();
    }

    public Datasets createDataset(Datasets dataset){
        return dataAccessRepository.save(dataset);
    }

    public Datasets getDatasetById(String id){
        if(dataAccessRepository.findById(id).isPresent()) {
            return dataAccessRepository.findById(id).get();
        }
        return null;
    }
}
