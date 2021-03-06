//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.01.29 at 11:35:23 PM HKT 
//


package com.algoTrader.mapping.type;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.algotrader.mapping.type package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@SuppressWarnings("restriction")
@XmlRegistry
public class ObjectFactory {

    private final static QName _Fix_QNAME = new QName("", "fix");
    private final static QName _Java_QNAME = new QName("", "java");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.algotrader.mapping.type
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link TypeMap }
     * 
     */
    public TypeMap createTypeMap() {
        return new TypeMap();
    }

    /**
     * Create an instance of {@link Format }
     * 
     */
    public Format createFormat() {
        return new Format();
    }

    /**
     * Create an instance of {@link Converter }
     * 
     */
    public Converter createConverter() {
        return new Converter();
    }

    /**
     * Create an instance of {@link Type }
     * 
     */
    public Type createType() {
        return new Type();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "fix")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    public JAXBElement<String> createFix(String value) {
        return new JAXBElement<String>(_Fix_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "java")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    public JAXBElement<String> createJava(String value) {
        return new JAXBElement<String>(_Java_QNAME, String.class, null, value);
    }

}
