package jaxb;
import jakarta.xml.bind.annotation.XmlElement;
public class FoodXml {
    @XmlElement private String name;
    @XmlElement private double price;
    @XmlElement private int    expiration;
    @XmlElement private String category;
    @XmlElement private boolean available;

    public String  getName()       { return name; }
    public double  getPrice()      { return price; }
    public int     getExpiration() { return expiration; }
    public String  getCategory()   { return category; }
    public boolean isAvailable()   { return available; }
}
