package com.telstra.health.shot.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import com.telstra.health.shot.entity.ShotTempBatch;

@Transactional
public interface ShotTempBatchDAO extends CrudRepository< ShotTempBatch, Long > {

}
