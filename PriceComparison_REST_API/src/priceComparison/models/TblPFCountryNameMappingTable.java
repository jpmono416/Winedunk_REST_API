package priceComparison.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * Entity implementation class for Entity: TblPFCountryNameMappingTable
 *
 */
@Entity
@Table(name="tblPFCountryNameMappingTable")
@NamedQueries({
	@NamedQuery(name="TblPFCountryNameMappingTable.findAll", query="SELECT t FROM TblPFCountryNameMappingTable t"),
	@NamedQuery(name="TblPFCountryNameMappingTable.findByCountry", query="SELECT t FROM TblPFCountryNameMappingTable t WHERE t.tblCountries = :country"),
	@NamedQuery(name="TblPFCountryNameMappingTable.findByCountryId", query="SELECT t FROM TblPFCountryNameMappingTable t WHERE t.tblCountries.id = :id"),
	@NamedQuery(name="TblPFCountryNameMappingTable.findByMerchantName", query="SELECT t FROM TblPFCountryNameMappingTable t WHERE t.merchantCountryName = :name"),
	@NamedQuery(name="TblPFCountryNameMappingTable.findByCountryAndMerchantName", query="SELECT t FROM TblPFCountryNameMappingTable t WHERE t.tblCountries = :country AND t.merchantCountryName = :name"),
	@NamedQuery(name="TblPFCountryNameMappingTable.findByCountryIdAndMerchantName", query="SELECT t FROM TblPFCountryNameMappingTable t WHERE t.tblCountries.id = :id AND t.merchantCountryName = :name"),
	@NamedQuery(name="TblPFCountryNameMappingTable.findByMerchantNameInsensitive", query="SELECT t FROM TblPFCountryNameMappingTable t WHERE LOWER(t.merchantCountryName) = LOWER(:name)"),
	@NamedQuery(name="TblPFCountryNameMappingTable.findByCountryAndMerchantNameInsensitive", query="SELECT t FROM TblPFCountryNameMappingTable t WHERE t.tblCountries = :country AND LOWER(t.merchantCountryName) = LOWER(:name)"),
	@NamedQuery(name="TblPFCountryNameMappingTable.findByCountryIdAndMerchantNameInsensitive", query="SELECT t FROM TblPFCountryNameMappingTable t WHERE t.tblCountries.id = :id AND LOWER(t.merchantCountryName) = LOWER(:name)")
})
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class TblPFCountryNameMappingTable implements Serializable {
	private static final long serialVersionUID = 1L;
	   
	@Id
	private Integer id;
	@ManyToOne
	@JoinColumn(name="countryId")
	private tblCountries tblCountries;

	// aripe length attribute added
	@Column(name= "mechantName", length = 200)
	private String merchantCountryName;

	public TblPFCountryNameMappingTable() {
		super();
	}
	public TblPFCountryNameMappingTable(tblCountries country, String merchantCountryName) {
		super();
		this.tblCountries = country;
		this.merchantCountryName = merchantCountryName;
	}

	public Integer getId() {
		return this.id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public tblCountries getTblCountries() {
		return this.tblCountries;
	}
	public void setTblCountries(tblCountries tblCountries) {
		this.tblCountries = tblCountries;
	}

	public String getMerchantCountryName() {
		return this.merchantCountryName;
	}
	public void setMerchantCountryName(String merchantCountryName) {
		this.merchantCountryName = merchantCountryName;
	}
}
