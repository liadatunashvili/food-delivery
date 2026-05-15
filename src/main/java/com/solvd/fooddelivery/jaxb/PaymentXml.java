package com.solvd.fooddelivery.jaxb;
import jakarta.xml.bind.annotation.XmlElement;
public class PaymentXml {
    @XmlElement private double  amount;
    @XmlElement private String  method;
    @XmlElement private boolean success;

    public double  getAmount()  { return amount; }
    public String  getMethod()  { return method; }
    public boolean isSuccess()  { return success; }
}
