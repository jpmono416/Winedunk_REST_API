package priceComparison.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;


@Entity
@Table(name = "tblCurrencies")
public class tblCurrencies {

    @Transient
    private static final long serialVersionUID = 1L;

    @Id
    private Integer id;
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    // aripe length attribute added
    @Column(name= "name", length = 45)
    private String name;
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    // aripe length attribute added
    @Column(name= "code", length = 3)
    private String code;
    public String getCode() { return code; }
	public void setCode(String code) { this.code = code; }
	
    @Column(name = "decimalPositions")
    private Integer decimalPositions;
    public Integer getDecimalPositions() { return decimalPositions; }
	public void setDecimalPositions(Integer decimalPositions) { this.decimalPositions = decimalPositions; }
	
    @Column(name = "numericCode")
    private Integer numericCode;
    public Integer getNumericCode() { return numericCode; }
	public void setNumericCode(Integer numericCode) { this.numericCode = numericCode; }

	@Column(name = "deleted")
    private Boolean deleted;
    public Boolean isDeleted() {return deleted;}
    public void setDeleted(Boolean deleted) {this.deleted = deleted;}

    @ManyToMany(mappedBy = "currencies")
    @JsonBackReference//("currency")
    private List<tblCountries> countries;
    public List<tblCountries> getCountries() { return countries; }
    public void setCountries(List<tblCountries> countries) { this.countries = countries; }

    @OneToMany(mappedBy = "preferredCurrencyId")
    @JsonBackReference("user_currency")
    private List<tblUsers> users;
    public List<tblUsers> getUsers() { return users; }
    public void setUsers(List<tblUsers> users) { this.users = users; }


    public tblCurrencies(Integer id) { this.id = id; }
    public tblCurrencies()
    {
        this.id = null;
        this.name = null;
        this.code = null;
        this.numericCode = null;
        this.decimalPositions = null;
        this.deleted = null;
        this.countries = null;
        this.users = null;
    }
    public tblCurrencies(String name) {
        this.name = name;
    }
}