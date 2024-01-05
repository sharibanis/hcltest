package com.db.dataplatform.techtest.server.persistence.repository;

import com.db.dataplatform.techtest.server.persistence.BlockTypeEnum;
import com.db.dataplatform.techtest.server.persistence.model.DataBodyEntity;

import java.util.ArrayList;
import javax.persistence.PersistenceContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query; 

@Repository
public interface DataStoreRepository extends JpaRepository<DataBodyEntity, Long> {
	
	@Query("SELECT c FROM DataBodyEntity c WHERE c.dataBody = ?1")
    public ArrayList<DataBodyEntity> getDataByBlockType(BlockTypeEnum blockType);
    


}
