package com.zis.youzan.response;

import java.util.Date;

public class GoodsImage {

	private String thumbnail;
	private Date created;
	private Number id;
	private String medium;
	private String url;
	private String combine;
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public Number getId() {
		return id;
	}
	public void setId(Number id) {
		this.id = id;
	}
	public String getMedium() {
		return medium;
	}
	public void setMedium(String medium) {
		this.medium = medium;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getCombine() {
		return combine;
	}
	public void setCombine(String combine) {
		this.combine = combine;
	}
	@Override
	public String toString() {
		return "GoodsImage [thumbnail=" + thumbnail + ", created=" + created + ", id=" + id + ", medium=" + medium
				+ ", url=" + url + ", combine=" + combine + "]";
	}
}
