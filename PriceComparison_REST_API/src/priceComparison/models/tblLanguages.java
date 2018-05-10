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
@Table(name = "tblLanguages")
public class tblLanguages {

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
    @Column(name= "shortName", length = 45)
    private String shortName;
    public String getShortName() { return shortName; }
    public void setShortName(String shortName) { this.shortName = shortName; }

    @Column(name= "deleted")
    private Boolean deleted;
    public Boolean isDeleted() { return deleted; }
    public void setDeleted(Boolean deleted) { this.deleted = deleted; }

    @ManyToMany(mappedBy = "languages")
    @JsonBackReference
    private List<tblCountries> countries;
    public List<tblCountries> getCountries() { return countries; }
    public void setCountries(List<tblCountries> countries) { this.countries = countries; }

    @OneToMany(mappedBy = "preferredLanguageId")
    @JsonBackReference("user_language")
    private List<tblUsers> users;
    public List<tblUsers> getUsers() { return users; }
    public void setUsers(List<tblUsers> users) { this.users = users; }


    public tblLanguages(Integer id) { this.id = id; }
    public tblLanguages()
    {
        this.id = null;
        this.name = null;
        this.shortName = null;
        this.deleted = null;
        this.countries = null;
        this.users = null;
    }
    public tblLanguages(String name) { this.name = name; }
}
