package priceComparison.models;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the tblpfmapping database table.
 * 
 */
@Entity
@Table(name="tblPFMapping")
@NamedQueries({
	@NamedQuery(name="Tblpfmapping.findAll", query="SELECT t FROM Tblpfmapping t"),
	@NamedQuery(name="Tblpfmapping.findByPFId", query="SELECT t FROM Tblpfmapping t WHERE t.tblpf.id = :id")
})
public class Tblpfmapping implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private Integer clicktagColumn;

	private Integer deliveryCostColumn;

	private Integer imageURLColumn;

	private Integer merchantNameColumn;

	private Integer merchantProductIdColumn;

	private Integer nameColumn;

	@Column(name="partnerProductDescriotionColumn")
	private Integer partnerProductDescriptionColumn;

	private Integer partnerProductIdColumn;

	private Integer priceColumn;

	private Integer wineTypeColumn;

	private Integer partnerMerchantId;

	private Integer productURLColumn;

	//bi-directional many-to-one association to Tblpf
	@ManyToOne
	@JoinColumn(name="pfId")
	private Tblpf tblpf;

	public Tblpfmapping() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getClicktagColumn() {
		return this.clicktagColumn;
	}

	public void setClicktagColumn(Integer clicktagColumn) {
		this.clicktagColumn = clicktagColumn;
	}

	public Integer getDeliveryCostColumn() {
		return this.deliveryCostColumn;
	}

	public void setDeliveryCostColumn(Integer deliveryCostColumn) {
		this.deliveryCostColumn = deliveryCostColumn;
	}

	public Integer getImageURLColumn() {
		return this.imageURLColumn;
	}

	public void setImageURLColumn(Integer imageURLColumn) {
		this.imageURLColumn = imageURLColumn;
	}

	public Integer getMerchantNameColumn() {
		return this.merchantNameColumn;
	}

	public void setMerchantNameColumn(Integer merchantNameColumn) {
		this.merchantNameColumn = merchantNameColumn;
	}

	public Integer getMerchantProductIdColumn() {
		return this.merchantProductIdColumn;
	}

	public void setMerchantProductIdColumn(Integer merchantProductIdColumn) {
		this.merchantProductIdColumn = merchantProductIdColumn;
	}

	public Integer getNameColumn() {
		return this.nameColumn;
	}

	public void setNameColumn(Integer nameColumn) {
		this.nameColumn = nameColumn;
	}

	public Integer getPartnerProductDescriptionColumn() {
		return this.partnerProductDescriptionColumn;
	}

	public void setPartnerProductDescriptionColumn(Integer partnerProductDescriptionColumn) {
		this.partnerProductDescriptionColumn = partnerProductDescriptionColumn;
	}

	public Integer getPartnerProductIdColumn() {
		return this.partnerProductIdColumn;
	}

	public void setPartnerProductIdColumn(Integer partnerProductIdColumn) {
		this.partnerProductIdColumn = partnerProductIdColumn;
	}

	public Integer getPriceColumn() {
		return this.priceColumn;
	}

	public void setPriceColumn(Integer priceColumn) {
		this.priceColumn = priceColumn;
	}

	public Integer getWineTypeColumn() {
		return this.wineTypeColumn;
	}

	public void setWineTypeColumn(Integer wineTypeColumn) {
		this.wineTypeColumn = wineTypeColumn;
	}

	public Integer getPartnerMerchantId() {
		return this.partnerMerchantId;
	}

	public void setPartnerMerchantId(Integer partnerMerchantId) {
		this.partnerMerchantId = partnerMerchantId;
	}

	public Tblpf getTblpf() {
		return this.tblpf;
	}

	public void setTblpf(Tblpf tblpf) {
		this.tblpf = tblpf;
	}

	public Integer getProductURLColumn() {
		return productURLColumn;
	}

	public void setProductURLColumn(Integer productURLColumn) {
		this.productURLColumn = productURLColumn;
	}

	@Override
	public String toString() {
		return "Tblpfmapping [id=" + id + ", clicktagColumn=" + clicktagColumn + ", deliveryCostColumn="
				+ deliveryCostColumn + ", imageURLColumn=" + imageURLColumn + ", merchantNameColumn="
				+ merchantNameColumn + ", merchantProductIdColumn=" + merchantProductIdColumn + ", nameColumn="
				+ nameColumn + ", partnerProductDescriptionColumn=" + partnerProductDescriptionColumn
				+ ", partnerProductIdColumn=" + partnerProductIdColumn + ", priceColumn=" + priceColumn
				+ ", wineTypeColumn=" + wineTypeColumn + ", partnerMerchantId=" + partnerMerchantId + ", productURL="
				+ productURLColumn + ", tblpf=" + tblpf + "]";
	}
}