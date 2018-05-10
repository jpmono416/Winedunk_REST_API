package priceComparison.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;


/**
 * The persistent class for the tblpfmerchanthtmlparsing database table.
 * 
 */
@Entity
@Table(name="tblPFMerchantHTMLParsing")
@NamedQueries({
	@NamedQuery(name="Tblpfmerchanthtmlparsing.findAll", query="SELECT t FROM Tblpfmerchanthtmlparsing t"),
	@NamedQuery(name="Tblpfmerchanthtmlparsing.findByTblShops", query="SELECT t FROM Tblpfmerchanthtmlparsing t WHERE t.tblShops = :tblShops")
})
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class Tblpfmerchanthtmlparsing implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	@JoinColumn(name="merchantId")
	private tblShops tblShops;

	// aripe annotation added
	@Column(name= "nameInWeb", length = 45)
	private String nameInWeb;

	//bi-directional many-to-one association to Tblpfparsingextractionmethod
	@ManyToOne
	@JoinColumn(name="parsingExtractionMethodsId")
	private Tblpfparsingextractionmethod tblpfparsingextractionmethod;

	//bi-directional many-to-one association to Tblpfextractioncolumn
	@ManyToOne
	@JoinColumn(name="extractionColumnsId")
	private Tblpfextractioncolumn tblpfextractioncolumn;

	// aripe annotation added
	@Column(name= "specificTag", length = 45)
	private String specificTag;

	private Integer numberOfTags;

	private Boolean mustMatch;

	// aripe annotation added
	@Column(name= "htmlTagType", length = 50)
	private String htmlTagType;

	// aripe annotation added
	@Column(name= "attribute", length = 50)
	private String attribute;

	public Tblpfmerchanthtmlparsing() {}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public tblShops getTblShops() {
		return this.tblShops;
	}

	public void setTblShops(tblShops tblShops) {
		this.tblShops = tblShops;
	}

	public String getNameInWeb() {
		return this.nameInWeb;
	}

	public void setNameInWeb(String nameInWeb) {
		this.nameInWeb = nameInWeb;
	}

	public Tblpfparsingextractionmethod getTblpfparsingextractionmethod() {
		return this.tblpfparsingextractionmethod;
	}

	public void setTblpfparsingextractionmethod(Tblpfparsingextractionmethod tblpfparsingextractionmethod) {
		this.tblpfparsingextractionmethod = tblpfparsingextractionmethod;
	}

	public Tblpfextractioncolumn getTblpfextractioncolumn() {
		return this.tblpfextractioncolumn;
	}

	public void setTblpfextractioncolumn(Tblpfextractioncolumn tblpfextractioncolumn) {
		this.tblpfextractioncolumn = tblpfextractioncolumn;
	}

	public String getSpecificTag() {
		return specificTag;
	}

	public void setSpecificTag(String specificTag) {
		this.specificTag = specificTag;
	}

	public Integer getNumberOfTags() {
		return this.numberOfTags;
	}

	public void setNumberOfTags(Integer numberOfTags) {
		this.numberOfTags = numberOfTags;
	}

	public Boolean getMustMatch() {
		return mustMatch;
	}

	public void setMustMatch(Boolean mustMatch) {
		this.mustMatch = mustMatch;
	}

	public String getHtmlTagType() {
		return htmlTagType;
	}

	public void setHtmlTagType(String htmlTagType) {
		this.htmlTagType = htmlTagType;
	}

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	@Override
	public String toString() {
		return "Tblpfmerchanthtmlparsing [id=" + id + ", tblShops=" + tblShops + ", nameInWeb=" + nameInWeb
				+ ", tblpfparsingextractionmethod=" + tblpfparsingextractionmethod + ", tblpfextractioncolumn="
				+ tblpfextractioncolumn + ", specificTag=" + specificTag + ", numberOfTags=" + numberOfTags
				+ ", mustMatch=" + mustMatch + ", htmlTagType=" + htmlTagType + "]";
	}
}