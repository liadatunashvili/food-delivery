package jaxb;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.xml.bind.annotation.XmlElement;
import java.util.List;
public class ItemsXml {
    @XmlElement(name = "food")
    @JsonProperty("items")
    private List<FoodXml> foods;
    public List<FoodXml> getFoods() { return foods; }
}
