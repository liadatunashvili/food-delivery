package com.solvd.fooddelivery.jaxb;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
@XmlRootElement(name = "order")   // tells JaxB: this class = <order> root element
public class OrderXml {
    @XmlAttribute private int    id;
    @XmlAttribute private String status;
    @XmlAttribute private String createdAt;
    @XmlElement   private double total;
    @XmlElement   private CustomerXml customer;
    @XmlElement   private ItemsXml    items;
    @XmlElement   private PaymentXml  payment;

    public int         getId()        { return id; }
    public String      getStatus()    { return status; }
    public String      getCreatedAt() { return createdAt; }
    public double      getTotal()     { return total; }
    public CustomerXml getCustomer()  { return customer; }
    public ItemsXml    getItems()     { return items; }
    public PaymentXml  getPayment()   { return payment; }
}
