package com.telstra.health.shot.dao;

import org.springframework.data.repository.CrudRepository;
import com.telstra.health.shot.entity.Batch;

public interface BatchDAO extends CrudRepository<Batch,String > {

}
