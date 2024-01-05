package com.db.dataplatform.techtest.server.component;

import com.db.dataplatform.techtest.server.api.model.DataEnvelope;
import com.db.dataplatform.techtest.server.persistence.BlockTypeEnum;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public interface Server {
    boolean saveDataEnvelope(DataEnvelope envelope) throws IOException, NoSuchAlgorithmException;
    //ArrayList<DataEnvelope> getAllDataEnvelopes(BlockTypeEnum blocktype) throws IOException, NoSuchAlgorithmException;
	ArrayList<DataEnvelope> getDataByBlockType(BlockTypeEnum blocktypea) throws IOException, NoSuchAlgorithmException ;
}
