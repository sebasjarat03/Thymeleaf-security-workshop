package com.example.demo.model.prchasing;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.example.demo.model.groups.Add;

/**
 * The persistent class for the purchaseorderdetail database table.
 *
 */
@Entity
@NamedQuery(name = "Purchaseorderdetail.findAll", query = "SELECT p FROM Purchaseorderdetail p")
public class Purchaseorderdetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "PURCHASEORDERDETAIL_PURCHASEORDERID_GENERATOR", allocationSize = 1, sequenceName = "PURCHASEORDERDETAIL_SEQ")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PURCHASEORDERDETAIL_PURCHASEORDERID_GENERATOR")
	private Integer id;

	private Timestamp duedate;

	private Timestamp modifieddate;

	@NotNull(groups = Add.class)
	@Min(value = 1, message = "Order quantity must be greater than zero", groups = Add.class)
	private Integer orderqty;

	private Integer productid;

	private BigDecimal receivedqty;

	private BigDecimal rejectedqty;

	@NotNull(groups = Add.class)
	@Min(value = 1, message = "Unit price must be greater than zero", groups = Add.class)
	private BigDecimal unitprice;

	// bi-directional many-to-one association to Purchaseorderheader

	// @JoinColumn(name = "purchaseorderid", insertable = false, updatable = false)
	@NotNull(groups = Add.class)
	@ManyToOne
	private Purchaseorderheader purchaseorderheader;

	public Purchaseorderdetail() {
	}

	public Timestamp getDuedate() {
		return this.duedate;
	}

	public Integer getId() {
		return this.id;
	}

	public Timestamp getModifieddate() {
		return this.modifieddate;
	}

	public Integer getOrderqty() {
		return this.orderqty;
	}

	public Integer getProductid() {
		return this.productid;
	}

	public Purchaseorderheader getPurchaseorderheader() {
		return this.purchaseorderheader;
	}

	public BigDecimal getReceivedqty() {
		return this.receivedqty;
	}

	public BigDecimal getRejectedqty() {
		return this.rejectedqty;
	}

	public BigDecimal getUnitprice() {
		return this.unitprice;
	}

	public void setDuedate(Timestamp duedate) {
		this.duedate = duedate;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setModifieddate(Timestamp modifieddate) {
		this.modifieddate = modifieddate;
	}

	public void setOrderqty(Integer orderqty) {
		this.orderqty = orderqty;
	}

	public void setProductid(Integer productid) {
		this.productid = productid;
	}

	public void setPurchaseorderheader(Purchaseorderheader purchaseorderheader) {
		this.purchaseorderheader = purchaseorderheader;
	}

	public void setReceivedqty(BigDecimal receivedqty) {
		this.receivedqty = receivedqty;
	}

	public void setRejectedqty(BigDecimal rejectedqty) {
		this.rejectedqty = rejectedqty;
	}

	public void setUnitprice(BigDecimal unitprice) {
		this.unitprice = unitprice;
	}

}