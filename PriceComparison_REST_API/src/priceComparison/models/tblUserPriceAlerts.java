package priceComparison.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;



@Entity
@Table(name = "tblUserPriceAlerts")
public class tblUserPriceAlerts {

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
	
    @ManyToOne
    @JoinColumn(name = "userId")
    private tblUsers userId;
    public tblUsers getUserId() { return userId; }
    public void setUserId(tblUsers userId) { this.userId = userId; }

    @ManyToOne
    @JoinColumn(name = "wineId")
    private tblWines wineId;
    public tblWines getWineId() { return wineId; }
    public void setWineId(tblWines wineId) { this.wineId = wineId; }
    
    @Column(name = "price")
    private Float price;
    public Float getPrice() { return price; }
	public void setPrice(Float price) { this.price = price; }
	
	
	public tblUserPriceAlerts(tblUsers id) {this.userId = id;}
    public tblUserPriceAlerts()
    {
        this.id = null;
        this.userId = null;
        this.wineId = null;
        this.name = null;
        this.price = null;
    }
}
