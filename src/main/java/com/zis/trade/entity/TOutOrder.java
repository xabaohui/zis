package com.zis.trade.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "t_out_order")
public class TOutOrder {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer id;

	@Column(name = "number")
	private Integer number;

	@Column(name = "serial")
	private String serial;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}
	
	public String getSerial() {
		return serial;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TOutOrder [id=");
		builder.append(id);
		builder.append(", number=");
		builder.append(number);
		builder.append("]");
		return builder.toString();
	}
}
