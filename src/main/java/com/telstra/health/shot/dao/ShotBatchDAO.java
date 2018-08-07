package com.telstra.health.shot.dao;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.telstra.health.shot.entity.ShotBatch;

@Transactional
public interface ShotBatchDAO extends CrudRepository<ShotBatch,Long>, ShotBatchDAOCustom {

}
