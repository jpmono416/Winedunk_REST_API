package priceComparison.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "tblCountries")
@NamedQuery(name="tblCountries.findByName", query="SELECT t FROM tblCountries t WHERE t.name = :name")
public class tblCountries {

	@Transient
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	private Integer id;
	public Integer getId() {
		return id;
	}
	public tblCountries setId(Integer id) {
		this.id = id;
		return this;
	}

	// aripe length attribute added
	@Column(name= "name", length = 45)
	private String name;
	public String getName() {
		return name;
	}
	public tblCountries setName(String name) {
		this.name = name;
		return this;
	}

	// aripe length attribute added
	@Column(name= "`isoAlpha-2-Code`", length = 2)
	private String isoAlpha2Code;
	public String getIsoAlpha2Code() {
		return isoAlpha2Code;
	}
	public tblCountries setIsoAlpha2Code(String isoAlpha2Code) {
		this.isoAlpha2Code = isoAlpha2Code;
		return this;
	}

	// aripe length attribute added
	@Column(name= "`isoAlpha-3-Code`", length = 3)
	private String isoAlpha3Code;
	public String getIsoAlpha3Code() {
		return isoAlpha3Code;
	}
	public tblCountries setIsoAlpha3Code(String isoAlpha3Code) {
		this.isoAlpha3Code = isoAlpha3Code;
		return this;
	}

	@Column(name = "isoNumericCode")
	private Integer isoNumericCode;
	public Integer getIsoNumericCode() {
		return isoNumericCode;
	}
	public tblCountries setIsoNumericCode(Integer isoNumericCode) {
		this.isoNumericCode = isoNumericCode;
		return this;
	}

	@Column(name = "deleted")
	private Boolean deleted;
	public Boolean isDeleted() {
		return deleted;
	}
	public tblCountries setDeleted(Boolean deleted) {
		this.deleted = deleted;
		return this;
	}

	@ManyToMany
	@JoinTable(name = "tblCountries_Currencies", joinColumns = @JoinColumn(name = "countryId"), inverseJoinColumns = @JoinColumn(name = "currencyId"))
	private List<tblCurrencies> currencies;

	public List<tblCurrencies> getCurrencies() {
		return currencies;
	}

	public void setCurrencies(List<tblCurrencies> currencies) {
		this.currencies = currencies;
	}

	@ManyToMany
	@JoinTable(name = "tblCountries_Languages", joinColumns = @JoinColumn(name = "countryId"), inverseJoinColumns = @JoinColumn(name = "languageId"))
	private List<tblLanguages> languages;

	public List<tblLanguages> getLanguages() {
		return languages;
	}

	public void setLanguages(List<tblLanguages> languages) {
		this.languages = languages;
	}

	@ManyToMany
	@JoinTable(name = "tblCountries_TimeZones", joinColumns = @JoinColumn(name = "countryId"), inverseJoinColumns = @JoinColumn(name = "timeZoneId"))
	private List<tblTimeZones> timeZones;

	public List<tblTimeZones> getTimeZones() {
		return timeZones;
	}

	public void setTimeZones(List<tblTimeZones> timeZones) {
		this.timeZones = timeZones;
	}

	@OneToMany(mappedBy = "countryId", targetEntity = tblUsers.class)
	@JsonBackReference("user_country")
	private List<tblUsers> users;

	public List<tblUsers> getUsers() {
		return users;
	}

	public void setUsers(List<tblUsers> users) {
		this.users = users;
	}

	@OneToMany(mappedBy = "tblCountries", targetEntity = tblRegions.class)
	@JsonBackReference("region_country")
	private List<tblRegions> regions;

	public List<tblRegions> getRegions() {
		return regions;
	}

	public void setRegions(List<tblRegions> regions) {
		this.regions = regions;
	}

	@OneToMany(mappedBy = "tblCountries", targetEntity = TblPFCountryNameMappingTable.class)
	@JsonBackReference("TblPFCountryNameMappingTable_country")
	private List<TblPFCountryNameMappingTable> tblPFCountryNameMappingTables;
	public List<TblPFCountryNameMappingTable> getTblPFCountryNameMappingTables() {
		return this.tblPFCountryNameMappingTables;
	}
	public void seTblPFCountryNameMappingTable(List<TblPFCountryNameMappingTable> tblPFCountryNameMappingTables) {
		this.tblPFCountryNameMappingTables = tblPFCountryNameMappingTables;
	}

	@OneToMany(mappedBy = "countryId", targetEntity = tblCountriesWithWines.class)
	@JsonBackReference("countryWithWine")
	private List<tblCountriesWithWines> countriesWithWines;

	public List<tblCountriesWithWines> getCountriesWithWines() {
		return countriesWithWines;
	}

	public void setCountriesWithWines(List<tblCountriesWithWines> countriesWithWines) {
		this.countriesWithWines = countriesWithWines;
	}

	@OneToMany(mappedBy = "tblCountry", targetEntity = tblWineries.class)
	@JsonBackReference("winery_coutry")
	private List<tblWineries> tblWineries;

	public List<tblWineries> getTblWineries() {
		return tblWineries;
	}

	public void setTblWineries(List<tblWineries> tblWineries) {
		this.tblWineries = tblWineries;
	}

	@OneToMany(mappedBy = "countryId", targetEntity = tblAppellations.class)
	@JsonBackReference("appellation_country")
	private List<tblAppellations> appellations;

	public List<tblAppellations> getappellations() {
		return appellations;
	}

	public void setappellations(List<tblAppellations> appellations) {
		this.appellations = appellations;
	}

	public tblCountries(Integer id) {
		this.id = id;
	}

	public tblCountries() {
		this.id = null;
		this.name = null;
		this.isoAlpha2Code = null;
		this.isoAlpha3Code = null;
		this.isoNumericCode = null;
		this.deleted = null;
		this.currencies = null;
		this.languages = null;
		this.users = null;
		this.timeZones = null;
		this.regions = null;
		this.countriesWithWines = null;
		this.appellations = null;

	}

	public tblCountries(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "tblCountries [id=" + id + ", name=" + name + ", isoAlpha2Code=" + isoAlpha2Code + ", isoAlpha3Code="
				+ isoAlpha3Code + ", isoNumericCode=" + isoNumericCode + ", deleted=" + deleted + ", currencies="
				+ currencies + ", languages=" + languages + ", timeZones=" + timeZones + ", users=" + users
				+ ", regions=" + regions + ", tblPFCountryNameMappingTables=" + tblPFCountryNameMappingTables
				+ ", countriesWithWines=" + countriesWithWines + ", tblWineries=" + tblWineries + ", appellations="
				+ appellations + "]";
	}
}
