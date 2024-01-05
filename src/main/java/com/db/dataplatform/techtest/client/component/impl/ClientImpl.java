package com.db.dataplatform.techtest.client.component.impl;

import com.db.dataplatform.techtest.client.api.model.DataEnvelope;
import com.db.dataplatform.techtest.client.component.Client;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriTemplate;

import java.net.URI;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Client code does not require any test coverage
 */

@Service
@Slf4j
@RequiredArgsConstructor
public class ClientImpl implements Client {

    public static final String URI_PUSHDATA = "http://localhost:8090/dataserver/pushdata";
    public static final UriTemplate URI_GETDATA = new UriTemplate("http://localhost:8090/dataserver/data/{blockType}");
    public static final UriTemplate URI_PATCHDATA = new UriTemplate("http://localhost:8090/dataserver/update/{name}/{newBlockType}");

    @Override
    public void pushData(DataEnvelope dataEnvelope) {
        String requestBody = null;
    	try {
            HttpPost httpPost = new HttpPost(URI_PUSHDATA);
            GsonBuilder builder = new GsonBuilder();
    		Gson gson = builder.create();
            requestBody = gson.toJson(dataEnvelope);
            StringEntity entity = new StringEntity(requestBody);
            httpPost.setEntity(entity);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            String response;
            log.info("Pushing data {} to {}", requestBody, URI_PUSHDATA);
        	CloseableHttpClient client = HttpClients.createDefault();
    		response = client.execute(httpPost, new BasicResponseHandler());
            log.info("Pushed data {} to {}", requestBody, URI_PUSHDATA);
            log.info("Received response {}", response);
    	} catch (Exception ex) {
            log.error("Error Pushing data {} to {}", requestBody, URI_PUSHDATA);
            log.error(ex.toString());
    	}
    }

    @Override
    public List<DataEnvelope> getData(String blockType) {
        log.info("Query for data with header block type {}", blockType);
        return null;
    }

    @Override
    public boolean updateData(String blockName, String newBlockType) {
        log.info("Updating blocktype to {} for block with name {}", newBlockType, blockName);
        return true;
    }


}
