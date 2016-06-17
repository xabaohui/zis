package com.zis.purchase.bean;

import java.sql.Timestamp;
import java.util.Date;


/**
 * Storesales entity. @author MyEclipse Persistence Tools
 */

public class Storesales  implements java.io.Serializable {


    // Fields    

     private Integer id;
     private Date captureDate;
     private Integer bookId;
     private Integer sales;
     private Timestamp gmtCreate;
     private Timestamp gmtModify;
     private Integer version;
     private String outId;
     private String isbn;


    // Constructors

    /** default constructor */
    public Storesales() {
    }

	/** minimal constructor */
    public Storesales(String outId, String isbn) {
        this.outId = outId;
        this.isbn = isbn;
    }
    
    /** full constructor */
    public Storesales(Date captureDate, Integer bookId, Integer sales, Timestamp gmtCreate, Timestamp gmtModify, Integer version, String outId, String isbn) {
        this.captureDate = captureDate;
        this.bookId = bookId;
        this.sales = sales;
        this.gmtCreate = gmtCreate;
        this.gmtModify = gmtModify;
        this.version = version;
        this.outId = outId;
        this.isbn = isbn;
    }

   
    // Property accessors

    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCaptureDate() {
        return this.captureDate;
    }
    
    public void setCaptureDate(Date captureDate) {
        this.captureDate = captureDate;
    }

    public Integer getBookId() {
        return this.bookId;
    }
    
    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public Integer getSales() {
        return this.sales;
    }
    
    public void setSales(Integer sales) {
        this.sales = sales;
    }

    public Timestamp getGmtCreate() {
        return this.gmtCreate;
    }
    
    public void setGmtCreate(Timestamp gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Timestamp getGmtModify() {
        return this.gmtModify;
    }
    
    public void setGmtModify(Timestamp gmtModify) {
        this.gmtModify = gmtModify;
    }

    public Integer getVersion() {
        return this.version;
    }
    
    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getOutId() {
        return this.outId;
    }
    
    public void setOutId(String outId) {
        this.outId = outId;
    }

    public String getIsbn() {
        return this.isbn;
    }
    
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
   








}