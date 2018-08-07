package com.telstra.health.shot.dao;


import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.telstra.health.shot.entity.ShotOrder;
@Transactional
public interface ShotOrderDAO extends CrudRepository<ShotOrder,Long>{

}
