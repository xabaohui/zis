package com.zis.trade.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "t_order_detail")
public class TOrderDetail {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer id;

	@Column(name = "item_id")
	private Integer itemId;

	@ManyToOne
	@JoinColumn(name = "order_id")
	private TOrder order;

	public TOrderDetail() {
	}

	public TOrderDetail(Integer itemId, TOrder order) {
		this.itemId = itemId;
		this.order = order;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public TOrder getOrder() {
		return order;
	}

	public void setOrder(TOrder order) {
		this.order = order;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TOrderDetail [id=");
		builder.append(id);
		builder.append(", itemId=");
		builder.append(itemId);
		builder.append("]");
		return builder.toString();
	}
}
