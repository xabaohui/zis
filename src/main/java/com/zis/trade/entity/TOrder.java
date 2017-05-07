package com.zis.trade.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "t_order")
public class TOrder implements Serializable {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer id;

	@Column(name = "name")
	private String name;

	@Column(name = "addr")
	private String addr;
	
	@Column(name = "serial")
	private String oSerial;
	
	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "order_id", referencedColumnName="id")
	private Set<TOrderDetail> orderDetails;

	public Set<TOrderDetail> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(Set<TOrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}

	public String getOSerial() {
		return oSerial;
	}

	public void setoSerial(String oSerial) {
		this.oSerial = oSerial;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}
}
