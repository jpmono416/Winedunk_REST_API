package priceComparison.models;

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
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;


@Entity
@Table(name = "tblGrapeVarieties")
@NamedQueries({
	@NamedQuery(name="tblGrapeVarieties.findByName", query="SELECT g FROM tblGrapeVarieties g WHERE g.name = :name")
})
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class tblGrapeVarieties {

    @Transient
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    // aripe length attribute added
    @Column(name= "name", length = 45)
    private String name;
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @Column(name= "deleted")
    private Boolean deleted;
    public Boolean isDeleted() {return deleted;}
    public void setDeleted(Boolean deleted) {this.deleted = deleted;}

    @OneToMany(mappedBy = "grapeVariety", targetEntity = TblWinesGrapeVariety.class)
    @JsonBackReference("variety_winesvarieties")
    private List<TblWinesGrapeVariety> tblWinesGrapeVariety;
    public List<TblWinesGrapeVariety> getTblWinesGrapeVariety() { return tblWinesGrapeVariety; }
	public void setTblWinesGrapeVariety(List<TblWinesGrapeVariety> TblWinesGrapeVariety) { this.tblWinesGrapeVariety = TblWinesGrapeVariety; }

    public tblGrapeVarieties(Integer id) { this.id = id; }
    public tblGrapeVarieties()
    {
        this.id = null;
        this.name = null;
        this.deleted = null;
        this.tblWinesGrapeVariety = null;
    }
    public tblGrapeVarieties(String name) {
        this.name = name;
    }

    @Override
	public String toString() {
		return "tblGrapeVarieties [id=" + id + ", name=" + name + ", deleted=" + deleted + "]";
	}
}