package jaxb;
import jakarta.xml.bind.annotation.XmlElement;
public class AddressXml {
    @XmlElement private String city;
    @XmlElement private String street;
    @XmlElement private String details;

    public String getCity()    { return city; }
    public String getStreet()  { return street; }
    public String getDetails() { return details; }
}
