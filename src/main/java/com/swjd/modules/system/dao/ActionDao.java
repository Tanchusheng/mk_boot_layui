package com.swjd.modules.system.dao;
import com.swjd.modules.system.entity.Action;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ActionDao extends MongoRepository<Action,String>  {

}
