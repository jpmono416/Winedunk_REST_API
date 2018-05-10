package priceComparison.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name="tblDataSourceParams")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
public class DataSourceParam {

	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	@ManyToOne
	@JoinColumn(name="dataSource")
	private DataSource dataSource;

	@ManyToOne
	@JoinColumn(name="shop")
	private tblShops tblShops	;

	// aripe length attribute added
	@Column(name= "paramName", length = 45)
	private String paramName;

	@Column(name="pathSection")
	private Short pathSection;

	// aripe length attribute added
	@Column(name= "variablesName", length = 45)
	private String variablesName;

	public DataSourceParam() {
		super();
	}

	public DataSourceParam(Integer id, DataSource dataSource, String paramName, Short pathSection,
			String variablesName) {
		super();
		this.id = id;
		this.dataSource = dataSource;
		this.paramName = paramName;
		this.pathSection = pathSection;
		this.variablesName = variablesName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setTblShops(tblShops tblShops) {
		this.tblShops = tblShops;
	}

	public tblShops getTblShops() {
		return tblShops;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public Short getPathSection() {
		return pathSection;
	}

	public void setPathSection(Short pathSection) {
		this.pathSection = pathSection;
	}

	public String getVariablesName() {
		return variablesName;
	}

	public void setVariablesName(String variablesName) {
		this.variablesName = variablesName;
	}

	@Override
	public String toString() {
		return "DataSourceParam [id=" + id + ", dataSource=" + dataSource + ", tblShops=" + tblShops + ", paramName="
				+ paramName + ", pathSection=" + pathSection + ", variablesName=" + variablesName + "]";
	}
}
