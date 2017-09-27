/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.oxy.s3m.notification.model.customer;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Admin
 */
public class CustomerAddress implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Integer id;
    
    private String custPh;
    
    private String custCountry;
    
    private String custState;
    
    private String custCity;
    
    private String custArea;
    
    private String address;
    
    private String email;
    
    

	private CustomerDetails customerId;

    public CustomerAddress() {
    }

    public CustomerAddress(Integer id) {
        this.id = id;
    }

    public CustomerAddress(Integer id, String custPh, String custCountry, String custState) {
        this.id = id;
        this.custPh = custPh;
        this.custCountry = custCountry;
        this.custState = custState;
    }
    
    public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCustPh() {
        return custPh;
    }

    public void setCustPh(String custPh) {
        this.custPh = custPh;
    }

    public String getCustCountry() {
        return custCountry;
    }

    public void setCustCountry(String custCountry) {
        this.custCountry = custCountry;
    }

    public String getCustState() {
        return custState;
    }

    public void setCustState(String custState) {
        this.custState = custState;
    }

    public String getCustCity() {
        return custCity;
    }

    public void setCustCity(String custCity) {
        this.custCity = custCity;
    }

    public String getCustArea() {
        return custArea;
    }

    public void setCustArea(String custArea) {
        this.custArea = custArea;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public CustomerDetails getCustomerId() {
        return customerId;
    }

    public void setCustomerId(CustomerDetails customerId) {
        this.customerId = customerId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CustomerAddress)) {
            return false;
        }
        CustomerAddress other = (CustomerAddress) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.s3m.notification.CustomerAddress[ id=" + id + " ]";
    }
    
}
