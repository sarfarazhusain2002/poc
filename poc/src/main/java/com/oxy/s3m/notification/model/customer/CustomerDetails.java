/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.oxy.s3m.notification.model.customer;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 *
 * @author Admin
 */

public class CustomerDetails implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Integer custId;
    
    private String custName;
    
    private String deviceId;
    
    private String fcmToken;
   
    private Timestamp dateCreated;
    
    private Boolean active;
    
    private CustomerAddress customerAddress;
    
    private Collection<CustomerCategories> custCategoriesCollection;
    
	private Set<CustomerInterests> customerInterests = new HashSet<CustomerInterests>(0);
	
	private String status;
	
	
    
    public Set<CustomerInterests> getCustomerInterests() {
		return customerInterests;
	}

	public void setCustomerInterests(Set<CustomerInterests> customerInterests) {
		this.customerInterests = customerInterests;
	}

	
    
    
    

    public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public CustomerDetails() {
    }

    public CustomerDetails(Integer custId) {
        this.custId = custId;
    }

    public CustomerDetails(Integer custId, String custName, Timestamp dateCreated) {
        this.custId = custId;
        this.custName = custName;
        this.dateCreated = dateCreated;
    }

    public Integer getCustId() {
        return custId;
    }

    public void setCustId(Integer custId) {
        this.custId = custId;
    }
    @NotNull(message = "First name is compulsory")    
    @Pattern(regexp = "[a-z-A-Z]*", message = "First name has invalid characters")
    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    public Timestamp getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public CustomerAddress getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(CustomerAddress customerAddress) {
        this.customerAddress = customerAddress;
    }

    
    public Collection<CustomerCategories> getCustCategoriesCollection() {
        return custCategoriesCollection;
    }

    public void setCustCategoriesCollection(Collection<CustomerCategories> custCategoriesCollection) {
        this.custCategoriesCollection = custCategoriesCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (custId != null ? custId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CustomerDetails)) {
            return false;
        }
        CustomerDetails other = (CustomerDetails) object;
        if ((this.custId == null && other.custId != null) || (this.custId != null && !this.custId.equals(other.custId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.s3m.notification.CustomerDetails[ custId=" + custId + " ]";
    }
    
}
