package com.db.dataplatform.techtest.server.api.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

@JsonSerialize(as = DataBody.class)
@JsonDeserialize(as = DataBody.class)
@Getter
public class DataBody implements Serializable {

    @NotNull
    private String dataBody;
    
    public DataBody(String dataBody) {
    	this.dataBody = dataBody;
    }

	public String getDataBody() {
		return dataBody;
	}

	public void setDataBody(String dataBody) {
		this.dataBody = dataBody;
	}

}
