package priceComparison.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name="tblDataSources")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
public class DataSource {

	@Id
	@Column
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	// aripe length attribute added
	@Column(name= "loginUrl", length = 200)
	private String loginUrl;

	// aripe length attribute added
	@Column(name= "tokenField", length = 50)
	private String tokenField;

	// aripe length attribute added
	@Column(name= "authField", length = 50)
	private String authField;

	// aripe length attribute added
	@Column(name= "dataUrl", length = 400)
	private String dataUrl;

	@NotNull
	@Column(name="contentType")
	@Enumerated(EnumType.STRING)
	private ContentTypes contentType;

	@OneToMany(mappedBy="dataSource", targetEntity=tblShops.class)
	private List<tblShops> shops;

	@OneToMany(mappedBy="dataSource", targetEntity=DataSourceParam.class)
	private List<DataSourceParam> dataSourceParams;

	public DataSource()
	{
		super();
	}

	public DataSource(Integer id, String loginUrl, String tokenField, String authField, String dataUrl, ContentTypes contentType) {
		super();
		this.id = id;
		this.loginUrl = loginUrl;
		this.tokenField = tokenField;
		this.authField= authField;
		this.dataUrl = dataUrl;
		this.contentType = contentType;
	}

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public String getLoginUrl()
	{
		return loginUrl;
	}

	public void setLoginUrl(String loginUrl)
	{
		this.loginUrl = loginUrl;
	}

	public String getTokenField() {
		return tokenField;
	}

	public void setTokenField(String tokenField) {
		this.tokenField = tokenField;
	}

	public String getAuthField() {
		return authField;
	}

	public void setAuthField(String authField) {
		this.authField = authField;
	}

	public String getDataUrl()
	{
		return dataUrl;
	}

	public void setDataUrl(String dataUrl)
	{
		this.dataUrl = dataUrl;
	}

	public ContentTypes getContentType()
	{
		return contentType;
	}

	public void setContentType(ContentTypes contentType)
	{
		this.contentType = contentType;
	}

	public List<tblShops> getShops() {
		return shops;
	}

	public void setShops(List<tblShops> shops) {
		this.shops = shops;
	}

	public List<DataSourceParam> getDataSourceParams() {
		return dataSourceParams;
	}

	public void setDataSourceParams(List<DataSourceParam> dataSourceParams) {
		this.dataSourceParams = dataSourceParams;
	}

	@Override
	public String toString() {
		return "DataSource [id=" + id + ", loginUrl=" + loginUrl + ", tokenField=" + tokenField + ", authField="
				+ authField + ", dataUrl=" + dataUrl + ", contentType=" + contentType + "]";
	}
}
