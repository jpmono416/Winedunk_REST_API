package priceComparison.models;

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

@Entity
@Table(name="tblWinesWineTypes")
@NamedQueries(value = {
		@NamedQuery(name="TblWinesWineType.findAll", query="SELECT t FROM TblWinesWineType t"),
		@NamedQuery(name="TblWinesWineType.findById", query="SELECT t FROM TblWinesWineType t WHERE t.id = :id"),
		@NamedQuery(name="TblWinesWineType.findByWine", query="SELECT t FROM TblWinesWineType t WHERE t.tblWines = :wine"),
		@NamedQuery(name="TblWinesWineType.findByWineType", query="SELECT t FROM TblWinesWineType t WHERE t.tblWineTypes = :tblWineTypes"),
		@NamedQuery(name="TblWinesWineType.findByWineAndWineType", query="SELECT t FROM TblWinesWineType t WHERE t.tblWines = :tblWines AND t.tblWineTypes = :tblWineTypes"),
		@NamedQuery(name="TblWinesWineType.findByWineId", query="SELECT t FROM TblWinesWineType t WHERE t.tblWines.id = :id"),
		@NamedQuery(name="TblWinesWineType.findByWineTypeId", query="SELECT t FROM TblWinesWineType t WHERE t.tblWineTypes.id = :id"),
		@NamedQuery(name="TblWinesWineType.findByWineIdAndWineTypeId", query="SELECT t FROM TblWinesWineType t WHERE t.tblWines.id = :wineId AND t.tblWineTypes.id = :wineTypeId")
})
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class TblWinesWineType {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@JoinColumn(name="wineId")
	private tblWines tblWines;

	@ManyToOne
	@JoinColumn(name="typeId")
	private tblWineTypes tblWineTypes;
	/**
	 *
	 */
	public TblWinesWineType() {}
	/**
	 * 
	 * @param tblWines
	 * @param tblWineTypes
	 */
	public TblWinesWineType(tblWines tblWines, tblWineTypes tblWineTypes) 
	{
		this.tblWines = tblWines;
		this.tblWineTypes = tblWineTypes;
	}

	/**
	 * 
	 * @return
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 
	 * @param id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * 
	 * @return
	 */
	public tblWines getTblWines() {
		return tblWines;
	}
	/**
	 * 
	 * @param tblWines
	 */
	public void setTblWines(tblWines tblWines) {
		this.tblWines = tblWines;
	}

	/**
	 * 
	 * @return
	 */
	public tblWineTypes getTblWineTypes() {
		return tblWineTypes;
	}
	/**
	 * 
	 * @param tblWineTypes
	 */
	public void setTblWineTypes(tblWineTypes tblWineTypes) {
		this.tblWineTypes = tblWineTypes;
	}

	
	/**
	 * 
	 */
	@Override
	public String toString() {
		return "TblWinesWineType [id=" + id + ", tblWines=" + tblWines + ", tblWineTypes=" + tblWineTypes + "]";
	}
}
