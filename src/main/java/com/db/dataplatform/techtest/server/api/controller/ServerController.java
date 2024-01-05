package com.db.dataplatform.techtest.server.api.controller;

import com.db.dataplatform.techtest.server.api.model.DataEnvelope;
import com.db.dataplatform.techtest.server.component.Server;
import com.db.dataplatform.techtest.server.persistence.BlockTypeEnum;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;

import javax.validation.Valid;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import com.google.gson.Gson;

@Slf4j
@Controller
@RequestMapping("/dataserver")
@RequiredArgsConstructor
@Validated
public class ServerController {

    private final Server server;

    @PostMapping(value = "/pushdata", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> pushData(@RequestBody String dataEnvelopeJsonString) 
    	throws IOException, NoSuchAlgorithmException {
        log.info("Data envelope received: {}", dataEnvelopeJsonString);
        Gson gson = new Gson();
        DataEnvelope dataEnvelope = gson.fromJson(dataEnvelopeJsonString, DataEnvelope.class);
        boolean checksumPass = server.saveDataEnvelope(dataEnvelope);

        log.info("Data envelope persisted. Attribute name: {}", dataEnvelope.getDataHeader().getName());
        return ResponseEntity.ok(checksumPass);
    }

    @GetMapping(value = "/getalldata/{blocktype}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ArrayList<DataEnvelope>> getData(@PathVariable String blocktype) 
    	throws IOException, NoSuchAlgorithmException {
        log.info("getalldata/{}", blocktype);
        ArrayList<DataEnvelope> result;
        if (blocktype.equals("blocktypea")) {
            result = server.getDataByBlockType(BlockTypeEnum.BLOCKTYPEA);
        } else {
            result = server.getDataByBlockType(BlockTypeEnum.BLOCKTYPEB);
        }

        log.info("Got All data.");
        return ResponseEntity.ok(result);
    }

}
