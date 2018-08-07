package com.telstra.health.shot.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.telstra.health.shot.entity.Trial;

public interface TrialsDAO extends JpaRepository<Trial,Integer >,TrialsDAOCustom{

}
