package com.db.dataplatform.techtest.client.api.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

@JsonSerialize(as = DataEnvelope.class)
@JsonDeserialize(as = DataEnvelope.class)
@Getter
@AllArgsConstructor
public class DataEnvelope {

    @NotNull
    private DataHeader dataHeader;

    @NotNull
    private DataBody dataBody;

	public DataHeader getDataHeader() {
		return dataHeader;
	}

	public void setDataHeader(DataHeader dataHeader) {
		this.dataHeader = dataHeader;
	}

	public DataBody getDataBody() {
		return dataBody;
	}

	public void setDataBody(DataBody dataBody) {
		this.dataBody = dataBody;
	}
}
