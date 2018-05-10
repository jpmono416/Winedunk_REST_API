package priceComparison.models;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

// import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "tblCountries")
@NamedQuery(name="tblCountryBasicData.findByName", query="SELECT t FROM tblCountries t WHERE t.name = :name")
public class tblCountryBasicData {

	@Transient
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	private Integer id;
	public Integer getId() {
		return id;
	}
	public tblCountryBasicData setId(Integer id) {
		this.id = id;
		return this;
	}

	// aripe length attribute added
	@Column(name= "name", length = 45)
	private String name;
	public String getName() {
		return name;
	}
	public tblCountryBasicData setName(String name) {
		this.name = name;
		return this;
	}

	// aripe length attribute added
	@Column(name= "`isoAlpha-2-Code`", length = 2)
	private String isoAlpha2Code;
	public String getIsoAlpha2Code() {
		return isoAlpha2Code;
	}
	public tblCountryBasicData setIsoAlpha2Code(String isoAlpha2Code) {
		this.isoAlpha2Code = isoAlpha2Code;
		return this;
	}

	// aripe length attribute added
	@Column(name= "`isoAlpha-3-Code`", length = 3)
	private String isoAlpha3Code;
	public String getIsoAlpha3Code() {
		return isoAlpha3Code;
	}
	public tblCountryBasicData setIsoAlpha3Code(String isoAlpha3Code) {
		this.isoAlpha3Code = isoAlpha3Code;
		return this;
	}

	@Column(name = "isoNumericCode")
	private Integer isoNumericCode;
	public Integer getIsoNumericCode() {
		return isoNumericCode;
	}
	public tblCountryBasicData setIsoNumericCode(Integer isoNumericCode) {
		this.isoNumericCode = isoNumericCode;
		return this;
	}

	@Column(name = "deleted")
	private Boolean deleted;
	public Boolean isDeleted() {
		return deleted;
	}
	public tblCountryBasicData setDeleted(Boolean deleted) {
		this.deleted = deleted;
		return this;
	}

	public tblCountryBasicData(Integer id) {
		this.id = id;
	}

	public tblCountryBasicData() {
		this.id = null;
		this.name = null;
		this.isoAlpha2Code = null;
		this.isoAlpha3Code = null;
		this.isoNumericCode = null;
		this.deleted = null;

	}

	public tblCountryBasicData(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "tblCountries [id=" + id + ", name=" + name + ", isoAlpha2Code=" + isoAlpha2Code + ", isoAlpha3Code="
				+ isoAlpha3Code + ", isoNumericCode=" + isoNumericCode + ", deleted=" + deleted + "]";
	}
}
