package com.restapi.demo.dao;

import com.restapi.demo.entity.Datasets;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataAccessRepository extends JpaRepository<Datasets, String> {
}
