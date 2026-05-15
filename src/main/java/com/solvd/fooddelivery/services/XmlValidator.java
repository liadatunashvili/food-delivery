package com.solvd.fooddelivery.services;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.InputStream;
public class XmlValidator {
    private static final Logger logger = LogManager.getLogger(XmlValidator.class);
    public boolean validate(String xmlResource, String xsdResource) {
        try (
            InputStream xmlStream = getClass().getClassLoader().getResourceAsStream(xmlResource);
            InputStream xsdStream = getClass().getClassLoader().getResourceAsStream(xsdResource)
        ) {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new StreamSource(xsdStream));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(xmlStream));
            logger.info("XML validation PASSED for: " + xmlResource);
            return true;
        } catch (Exception e) {
            logger.error("XML validation FAILED: " + e.getMessage());
            return false;
        }
    }
}
