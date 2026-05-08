package jaxb;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
public class CustomerXml {
    @XmlAttribute private int customerId;
    @XmlElement    private String name;
    @XmlElement    private String email;
    @XmlElement    private String phone;
    @XmlElement    private AddressXml address;

    public int         getCustomerId() { return customerId; }
    public String      getName()       { return name; }
    public String      getEmail()      { return email; }
    public String      getPhone()      { return phone; }
    public AddressXml  getAddress()    { return address; }
}
