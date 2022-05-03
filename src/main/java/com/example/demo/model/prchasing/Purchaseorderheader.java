package com.example.demo.model.prchasing;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import com.example.demo.model.groups.Add;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * The persistent class for the purchaseorderheader database table.
 * 
 */
@Entity
@NamedQuery(name = "Purchaseorderheader.findAll", query = "SELECT p FROM Purchaseorderheader p")
public class Purchaseorderheader implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "PURCHASEORDERHEADER_PURCHASEORDERID_GENERATOR", allocationSize = 1, sequenceName = "PURCHASEORDERHEADER_SEQ")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PURCHASEORDERHEADER_PURCHASEORDERID_GENERATOR")
	private Integer purchaseorderid;

	@NotNull(groups = Add.class)
	private Integer employeeid;

	private BigDecimal freight;

	private Timestamp modifieddate;

	@PastOrPresent(groups = Add.class, message = "Order date must be the actual date")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@FutureOrPresent(groups = Add.class, message = "Order date must be the actual date")
	@NotNull(groups = Add.class)
	private LocalDate orderdate;

	private Integer revisionnumber;

	private Timestamp shipdate;

	private Integer status;

	@Min(value = 1, message = "The subtotal must be greater than zero", groups = Add.class)
	@NotNull(groups = Add.class)
	private BigDecimal subtotal;

	private BigDecimal taxamt;

	// bi-directional many-to-one association to Purchaseorderdetail
	@OneToMany(mappedBy = "purchaseorderheader")
	private List<Purchaseorderdetail> purchaseorderdetails;

	// bi-directional many-to-one association to Shipmethod
	@ManyToOne
	@JoinColumn(name = "shipmethodid")
	private Shipmethod shipmethod;

	// bi-directional many-to-one association to Vendor
	@ManyToOne
	@JoinColumn(name = "vendorid")
	private Vendor vendor;

	public Purchaseorderheader() {
	}

	public Integer getPurchaseorderid() {
		return this.purchaseorderid;
	}

	public void setPurchaseorderid(Integer purchaseorderid) {
		this.purchaseorderid = purchaseorderid;
	}

	public Integer getEmployeeid() {
		return this.employeeid;
	}

	public void setEmployeeid(Integer employeeid) {
		this.employeeid = employeeid;
	}

	public BigDecimal getFreight() {
		return this.freight;
	}

	public void setFreight(BigDecimal freight) {
		this.freight = freight;
	}

	public Timestamp getModifieddate() {
		return this.modifieddate;
	}

	public void setModifieddate(Timestamp modifieddate) {
		this.modifieddate = modifieddate;
	}

	public LocalDate getOrderdate() {
		return this.orderdate;
	}

	public void setOrderdate(LocalDate orderdate) {
		this.orderdate = orderdate;
	}

	public Integer getRevisionnumber() {
		return this.revisionnumber;
	}

	public void setRevisionnumber(Integer revisionnumber) {
		this.revisionnumber = revisionnumber;
	}

	public Timestamp getShipdate() {
		return this.shipdate;
	}

	public void setShipdate(Timestamp shipdate) {
		this.shipdate = shipdate;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public BigDecimal getSubtotal() {
		return this.subtotal;
	}

	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}

	public BigDecimal getTaxamt() {
		return this.taxamt;
	}

	public void setTaxamt(BigDecimal taxamt) {
		this.taxamt = taxamt;
	}

	public List<Purchaseorderdetail> getPurchaseorderdetails() {
		return this.purchaseorderdetails;
	}

	public void setPurchaseorderdetails(List<Purchaseorderdetail> purchaseorderdetails) {
		this.purchaseorderdetails = purchaseorderdetails;
	}

	public Purchaseorderdetail addPurchaseorderdetail(Purchaseorderdetail purchaseorderdetail) {
		getPurchaseorderdetails().add(purchaseorderdetail);
		purchaseorderdetail.setPurchaseorderheader(this);

		return purchaseorderdetail;
	}

	public Purchaseorderdetail removePurchaseorderdetail(Purchaseorderdetail purchaseorderdetail) {
		getPurchaseorderdetails().remove(purchaseorderdetail);
		purchaseorderdetail.setPurchaseorderheader(null);

		return purchaseorderdetail;
	}

	public Shipmethod getShipmethod() {
		return this.shipmethod;
	}

	public void setShipmethod(Shipmethod shipmethod) {
		this.shipmethod = shipmethod;
	}

	public Vendor getVendor() {
		return this.vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

}