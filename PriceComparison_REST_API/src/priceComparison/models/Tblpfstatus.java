package priceComparison.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;


/**
 * The persistent class for the tblpfstatus database table.
 * 
 */
@Entity()
@Table(name="tblPFStatus")
@NamedQueries(value = {
		@NamedQuery(name="Tblpfstatus.findAll", query="SELECT t FROM Tblpfstatus t"),
		@NamedQuery(name="Tblpfstatus.findByName", query="SELECT t FROM Tblpfstatus t WHERE t.name = :name")
})
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class Tblpfstatus implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	// aripe annotation added
	@Column(name= "name", length = 45)
	private String name;

	//bi-directional many-to-one association to Tblpf
	@OneToMany(mappedBy="latestStatus", targetEntity=Tblpf.class)
	private List<Tblpf> latestStatusList;

	//bi-directional many-to-one association to Tblpf
	@OneToMany(mappedBy="standardisationStatus", targetEntity=Tblpf.class)
	private List<Tblpf> standardisationStatusList;

	//bi-directional many-to-one association to Tblpf
	@OneToMany(mappedBy="importationStatus", targetEntity=Tblpf.class)
	private List<Tblpf> importationStatusList;

	//bi-directional many-to-one association to Tblpfstatuschangelog
	@OneToMany(mappedBy="tblpfstatus", targetEntity=Tblpfstatuschangelog.class)
	private List<Tblpfstatuschangelog> tblpfstatuschangelogs;

	public Tblpfstatus() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Tblpf> getLatestStatusList() {
		return this.latestStatusList;
	}

	public void setLatestStatusList(List<Tblpf> latestStatusList) {
		this.latestStatusList = latestStatusList;
	}

	public Tblpf addTblpfs1(Tblpf tblpfs1) {
		getLatestStatusList().add(tblpfs1);
		tblpfs1.setLatestStatus(this);

		return tblpfs1;
	}

	public Tblpf removeTblpfs1(Tblpf tblpfs1) {
		getLatestStatusList().remove(tblpfs1);
		tblpfs1.setLatestStatus(null);

		return tblpfs1;
	}

	public List<Tblpf> getStandardisationStatusList() {
		return this.standardisationStatusList;
	}

	public void setStandardisationStatusList(List<Tblpf> standardisationStatusList) {
		this.standardisationStatusList = standardisationStatusList;
	}

	public Tblpf addStandardisationStatusList(Tblpf standardisationStatusList) {
		getStandardisationStatusList().add(standardisationStatusList);
		standardisationStatusList.setStandardisationStatus(this);

		return standardisationStatusList;
	}

	public Tblpf removeStandardisationStatusList(Tblpf standardisationStatusList) {
		getStandardisationStatusList().remove(standardisationStatusList);
		standardisationStatusList.setStandardisationStatus(null);

		return standardisationStatusList;
	}

	public List<Tblpf> getImportationStatus() {
		return this.importationStatusList;
	}

	public void setImportationStatus(List<Tblpf> importationStatusList) {
		this.importationStatusList = importationStatusList;
	}

	public Tblpf addImportationStatus(Tblpf importationStatusList) {
		getImportationStatus().add(importationStatusList);
		importationStatusList.setImportationStatus(this);

		return importationStatusList;
	}

	public Tblpf removeImportationStatus(Tblpf importationStatus) {
		getImportationStatus().remove(importationStatus);
		importationStatus.setImportationStatus(null);

		return importationStatus;
	}

	public List<Tblpfstatuschangelog> getTblpfstatuschangelogs() {
		return this.tblpfstatuschangelogs;
	}

	public void setTblpfstatuschangelogs(List<Tblpfstatuschangelog> tblpfstatuschangelogs) {
		this.tblpfstatuschangelogs = tblpfstatuschangelogs;
	}

	public Tblpfstatuschangelog addTblpfstatuschangelog(Tblpfstatuschangelog tblpfstatuschangelog) {
		getTblpfstatuschangelogs().add(tblpfstatuschangelog);
		tblpfstatuschangelog.setTblpfstatus(this);

		return tblpfstatuschangelog;
	}

	public Tblpfstatuschangelog removeTblpfstatuschangelog(Tblpfstatuschangelog tblpfstatuschangelog) {
		getTblpfstatuschangelogs().remove(tblpfstatuschangelog);
		tblpfstatuschangelog.setTblpfstatus(null);

		return tblpfstatuschangelog;
	}

	@Override
	public String toString() {
		return "Tblpfstatus [id=" + id + ", name=" + name + "]";
	}


}