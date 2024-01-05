package com.db.dataplatform.techtest.server.persistence.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "DATA_STORE")
@Setter
@Getter
public class DataBodyEntity {

    @Id
    @SequenceGenerator(name = "dataStoreSequenceGenerator", sequenceName = "SEQ_DATA_STORE", allocationSize = 1)
    @GeneratedValue(generator = "dataStoreSequenceGenerator")
    @Column(name = "DATA_STORE_ID")
    private Long dataStoreId;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "DATA_HEADER_ID")
    private DataHeaderEntity dataHeaderEntity;

    @Column(name = "DATA_BODY")
    private String dataBody;

    @Column(name = "CREATED_TIMESTAMP")
    private Instant createdTimestamp;

    @PrePersist
    public void setTimestamps() {
        if (createdTimestamp == null) {
            createdTimestamp = Instant.now();
        }
    }

    public Long getDataStoreId() {
		return dataStoreId;
	}


	public void setDataStoreId(Long dataStoreId) {
		this.dataStoreId = dataStoreId;
	}


	public DataHeaderEntity getDataHeaderEntity() {
		return dataHeaderEntity;
	}


	public void setDataHeaderEntity(DataHeaderEntity dataHeaderEntity) {
		this.dataHeaderEntity = dataHeaderEntity;
	}


	public String getDataBody() {
		return dataBody;
	}


	public void setDataBody(String dataBody) {
		this.dataBody = dataBody;
	}


	public Instant getCreatedTimestamp() {
		return createdTimestamp;
	}


	public void setCreatedTimestamp(Instant createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}


	@Override
    public boolean equals(Object obj) {
         if (this == obj) {
              return true;
         }
         if (obj == null) { 
             return false;
         }
         if (getClass() != obj.getClass()) {
              return false;
         }
         DataBodyEntity other = (DataBodyEntity) obj;
         boolean equals = (this.dataHeaderEntity.getName().equals(other.getDataHeaderEntity().getName())
        		 && this.dataHeaderEntity.getBlocktype().equals(other.getDataHeaderEntity().getBlocktype())
        		 && this.getDataBody().equals(other.getDataBody())
        		 );
        		 
         return equals;
    }

    @Override
    public int hashCode() {
        return dataStoreId.hashCode();
    }    
    
}
