package com.db.dataplatform.techtest.server.component.impl;

import com.db.dataplatform.techtest.TechTestApplication;
import com.db.dataplatform.techtest.server.api.model.DataBody;
import com.db.dataplatform.techtest.server.api.model.DataEnvelope;
import com.db.dataplatform.techtest.server.api.model.DataHeader;
import com.db.dataplatform.techtest.server.persistence.BlockTypeEnum;
import com.db.dataplatform.techtest.server.persistence.model.DataBodyEntity;
import com.db.dataplatform.techtest.server.persistence.model.DataHeaderEntity;
import com.db.dataplatform.techtest.server.service.DataBodyService;
import com.db.dataplatform.techtest.server.component.Server;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.xml.bind.DatatypeConverter;

@Slf4j
@Service
@RequiredArgsConstructor
public class ServerImpl implements Server {

    private final DataBodyService dataBodyServiceImpl;
    private final ModelMapper modelMapper;

    /**
     * @param envelope
     * @return true if there is a match with the client provided checksum.
     */
    @Override
    public boolean saveDataEnvelope(DataEnvelope envelope) {
        ByteArrayOutputStream baos = null;
        ObjectOutputStream oos = null;
        boolean checksumPass = false;
        try {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(envelope.getDataBody());
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] thedigest = md.digest(baos.toByteArray());
            String checksum = DatatypeConverter.printHexBinary(thedigest);
            log.info("checksum: {}", checksum);
            log.info("TechTestApplication.MD5_CHECKSUM: {}", TechTestApplication.MD5_CHECKSUM);
            if (checksum.equals(TechTestApplication.MD5_CHECKSUM)) {
                // Save to persistence.
                log.info("Checkums are equal: {} = {}", checksum, TechTestApplication.MD5_CHECKSUM);
                persist(envelope);
                log.info("Data persisted successfully, data name: {}", envelope.getDataHeader().getName());
                checksumPass = true;
            } else {
                log.info("Checkums are NOT equal: {} != {}", checksum, TechTestApplication.MD5_CHECKSUM);
                log.info("Data NOT persisted successfully, data name: {}", envelope.getDataHeader().getName());
                checksumPass = false;
            }
    	} catch (Exception ex) {
            log.error("Error persisting data {}", envelope.getDataHeader().getName());
            log.error(ex.toString());
        } finally {
        	try {
	            oos.close();
	            baos.close();
        	} catch (Exception ex) {
                log.error(ex.toString());
        	}
        }
        
        return checksumPass;
    }

    private void persist(DataEnvelope envelope) {
        log.info("Persisting data with attribute name: {}", envelope.getDataHeader().getName());
        DataHeaderEntity dataHeaderEntity = modelMapper.map(envelope.getDataHeader(), DataHeaderEntity.class);

        DataBodyEntity dataBodyEntity = modelMapper.map(envelope.getDataBody(), DataBodyEntity.class);
        dataBodyEntity.setDataHeaderEntity(dataHeaderEntity);

        saveData(dataBodyEntity);
    }

    private void saveData(DataBodyEntity dataBodyEntity) {
        dataBodyServiceImpl.saveDataBody(dataBodyEntity);
    }

    public ArrayList<DataEnvelope> getDataByBlockType(BlockTypeEnum blocktype) 
    		throws IOException, NoSuchAlgorithmException {
    	ArrayList<DataBodyEntity> dataBodyEntityList = (ArrayList<DataBodyEntity>)dataBodyServiceImpl.getDataByBlockType(blocktype);
    	ArrayList<DataEnvelope> result = new ArrayList<>();
    	for (DataBodyEntity dbe : dataBodyEntityList) {
    		DataEnvelope de = new DataEnvelope();
    		de.setDataBody(new DataBody(dbe.getDataBody()));
    		result.add(de);
    	}
    	return result;
    }

}
