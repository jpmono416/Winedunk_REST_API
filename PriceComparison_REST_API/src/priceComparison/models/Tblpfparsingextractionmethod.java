package priceComparison.models;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the tblpfparsingextractionmethods database table.
 * 
 */
@Entity
@Table(name="tblPFParsingExtractionMethods")
@NamedQuery(name="Tblpfparsingextractionmethod.findAll", query="SELECT t FROM Tblpfparsingextractionmethod t")
public class Tblpfparsingextractionmethod implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	// aripe annotation added
	@Column(name= "method", length = 45)
	private String method;

	//bi-directional many-to-one association to Tblpfmerchanthtmlparsing
	@OneToMany(mappedBy="tblpfparsingextractionmethod")
	private List<Tblpfmerchanthtmlparsing> tblpfmerchanthtmlparsings;

	public Tblpfparsingextractionmethod() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMethod() {
		return this.method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public List<Tblpfmerchanthtmlparsing> getTblpfmerchanthtmlparsings() {
		return this.tblpfmerchanthtmlparsings;
	}

	public void setTblpfmerchanthtmlparsings(List<Tblpfmerchanthtmlparsing> tblpfmerchanthtmlparsings) {
		this.tblpfmerchanthtmlparsings = tblpfmerchanthtmlparsings;
	}

	public Tblpfmerchanthtmlparsing addTblpfmerchanthtmlparsing(Tblpfmerchanthtmlparsing tblpfmerchanthtmlparsing) {
		getTblpfmerchanthtmlparsings().add(tblpfmerchanthtmlparsing);
		tblpfmerchanthtmlparsing.setTblpfparsingextractionmethod(this);

		return tblpfmerchanthtmlparsing;
	}

	public Tblpfmerchanthtmlparsing removeTblpfmerchanthtmlparsing(Tblpfmerchanthtmlparsing tblpfmerchanthtmlparsing) {
		getTblpfmerchanthtmlparsings().remove(tblpfmerchanthtmlparsing);
		tblpfmerchanthtmlparsing.setTblpfparsingextractionmethod(null);

		return tblpfmerchanthtmlparsing;
	}

}