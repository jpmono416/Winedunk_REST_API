package priceComparison.models;

import java.io.Serializable;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * The persistent class for the tblpfs database table.
 * 
 */
@Entity
@Table(name = "tblPFs")
@NamedQuery(name = "Tblpf.findAll", query = "SELECT t FROM Tblpf t")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
public class Tblpf implements Serializable {
	private static final long serialVersionUID = 1L;

	public Tblpf() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	// aripe name and length attributes added
	@Column(name= "description", length = 45, nullable = false)
	private String description;

	public String getDescription() {
		return this.description;
	}

	public Tblpf setDescription(String description) {
		this.description = description;
		return this;
	}

	// aripe name and length attributes added
	@Column(name= "downloadURL", length = 600, nullable = false)
	private String downloadURL;

	public String getDownloadURL() {
		return this.downloadURL;
	}

	public Tblpf setDownloadURL(String downloadURL) {
		this.downloadURL = downloadURL;
		return this;
	}

	@Column(nullable = false)
	private Integer importPriority;

	public Integer getImportPriority() {
		return this.importPriority;
	}

	public Tblpf setImportPriority(Integer importPriority) {
		this.importPriority = importPriority;
		return this;
	}

	private Timestamp lastImportation;

	public Timestamp getLastImportation() {
		return this.lastImportation;
	}

	public Tblpf setLastImportation(Timestamp lastImportation) {
		this.lastImportation = lastImportation;
		return this;
	}

	private Timestamp lastStandardisation;

	public Timestamp getLastStandardisation() {
		return this.lastStandardisation;
	}

	public Tblpf setLastStandardisation(Timestamp lastStandardisation) {
		this.lastStandardisation = lastStandardisation;
		return this;
	}

	@ManyToOne
	@JoinColumn(name = "partnerId")
	@JsonInclude(Include.NON_NULL)
	private tblPartners partnerId;

	public tblPartners getPartnerId() {
		return this.partnerId;
	}

	public Tblpf setPartnerId(tblPartners partnerId) {
		this.partnerId = partnerId;
		return this;
	}

	private Time startTime;

	public Time getStartTime() {
		return this.startTime;
	}

	public Tblpf setStartTime(Time startTime) {
		this.startTime = startTime;
		return this;
	}

	// aripe length attribute added
	@Column(name= "timePeriod", length = 13, nullable = false)
	private String timePeriod;

	public String getTimePeriod() {
		return this.timePeriod;
	}

	public Tblpf setTimePeriod(String timePeriod) {
		this.timePeriod = timePeriod;
		return this;
	}

	// bi-directional many-to-one association to Tblpfmapping
	@OneToMany(mappedBy = "tblpf")
	private List<Tblpfmapping> tblpfmappings;

	public List<Tblpfmapping> getTblpfmappings() {
		return this.tblpfmappings;
	}

	public Tblpf setTblpfmappings(List<Tblpfmapping> tblpfmappings) {
		this.tblpfmappings = tblpfmappings;
		return this;
	}

	public Tblpfmapping addTblpfmapping(Tblpfmapping tblpfmapping) {
		getTblpfmappings().add(tblpfmapping);
		tblpfmapping.setTblpf(this);

		return tblpfmapping;
	}

	public Tblpfmapping removeTblpfmapping(Tblpfmapping tblpfmapping) {
		getTblpfmappings().remove(tblpfmapping);
		tblpfmapping.setTblpf(null);

		return tblpfmapping;
	}

	// bi-directional many-to-one association to Tblpfproduct
	@OneToMany(mappedBy = "tblpf", targetEntity = Tblpfproduct.class)
	private List<Tblpfproduct> tblpfproducts;

	public List<Tblpfproduct> getTblpfproducts() {
		return this.tblpfproducts;
	}

	public void setTblpfproducts(List<Tblpfproduct> tblpfproducts) {
		this.tblpfproducts = tblpfproducts;
	}

	public Tblpfproduct addTblpfproduct(Tblpfproduct tblpfproduct) {
		getTblpfproducts().add(tblpfproduct);
		tblpfproduct.setTblpf(this);

		return tblpfproduct;
	}

	public Tblpfproduct removeTblpfproduct(Tblpfproduct tblpfproduct) {
		getTblpfproducts().remove(tblpfproduct);
		tblpfproduct.setTblpf(null);

		return tblpfproduct;
	}

	// bi-directional many-to-one association to Tblpfstatus
	@ManyToOne
	@JoinColumn(name = "latestStatus")
	@JsonInclude(Include.NON_NULL)
	private Tblpfstatus latestStatus;

	public Tblpfstatus getLatestStatus() {
		return this.latestStatus;
	}

	public Tblpf setLatestStatus(Tblpfstatus latestStatus) {
		this.latestStatus = latestStatus;
		return this;
	}

	// bi-directional many-to-one association to Tblpfstatus
	@ManyToOne
	@JoinColumn(name = "standardisationStatus")
	@JsonInclude(Include.NON_NULL)
	private Tblpfstatus standardisationStatus;

	public Tblpfstatus getStandardisationStatus() {
		return this.standardisationStatus;
	}

	public Tblpf setStandardisationStatus(Tblpfstatus standardisationStatus) {
		this.standardisationStatus = standardisationStatus;
		return this;
	}

	// bi-directional many-to-one association to Tblpfstatus
	@ManyToOne
	@JoinColumn(name = "importationStatus")
	@JsonInclude(Include.NON_NULL)
	private Tblpfstatus importationStatus;

	public Tblpfstatus getImportationStatus() {
		return this.importationStatus;
	}

	public Tblpf setImportationStatus(Tblpfstatus importationStatus) {
		this.importationStatus = importationStatus;
		return this;
	}

	// bi-directional many-to-one association to Tblpfstatuschangelog
	@OneToMany(mappedBy = "tblpf", targetEntity = Tblpfstatuschangelog.class)
	private List<Tblpfstatuschangelog> tblpfstatuschangelogs;

	public List<Tblpfstatuschangelog> getTblpfstatuschangelogs() {
		return this.tblpfstatuschangelogs;
	}

	public void setTblpfstatuschangelogs(List<Tblpfstatuschangelog> tblpfstatuschangelogs) {
		this.tblpfstatuschangelogs = tblpfstatuschangelogs;
	}

	public Tblpfstatuschangelog addTblpfstatuschangelog(Tblpfstatuschangelog tblpfstatuschangelog) {
		getTblpfstatuschangelogs().add(tblpfstatuschangelog);
		tblpfstatuschangelog.setTblpf(this);

		return tblpfstatuschangelog;
	}

	public Tblpfstatuschangelog removeTblpfstatuschangelog(Tblpfstatuschangelog tblpfstatuschangelog) {
		getTblpfstatuschangelogs().remove(tblpfstatuschangelog);
		tblpfstatuschangelog.setTblpf(null);

		return tblpfstatuschangelog;
	}

	@Column(nullable = false)
	private Boolean isZip;

	public Boolean getIsZip() {
		return isZip;
	}

	public void setIsZip(Boolean isZip) {
		this.isZip = isZip;
	}

	@Column(nullable = false)
	private Boolean hasHeader;

	public Boolean getHasHeader() {
		return hasHeader;
	}

	public void setHasHeader(Boolean hasHeader) {
		this.hasHeader = hasHeader;
	}

	// force back ticks as "separator" is a reserved word by MySQL
	// aripe length attribute added
	@Column(name = "`separator`", length = 10, nullable = false)
	private String separator;

	public String getSeparator() {
		return separator;
	}

	public void setSeparator(String separator) {
		this.separator = separator;
	}

	@Override
	public String toString() {
		return "Tblpf [id=" + id + ", description=" + description + ", downloadURL=" + downloadURL + ", importPriority="
				+ importPriority + ", lastImportation=" + lastImportation + ", lastStandardisation="
				+ lastStandardisation + ", partnerId=" + partnerId + ", startTime=" + startTime + ", timePeriod="
				+ timePeriod + ", latestStatus=" + latestStatus + ", standardisationStatus=" + standardisationStatus
				+ ", importationStatus=" + importationStatus + ", isZip=" + isZip + ", hasHeader=" + hasHeader
				+ ", separator=" + separator + "]";
	}
}