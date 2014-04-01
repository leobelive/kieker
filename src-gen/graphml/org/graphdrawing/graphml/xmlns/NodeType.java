//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.04.01 at 08:06:02 AM CEST 
//


package org.graphdrawing.graphml.xmlns;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * 
 *       Complex type for the <node> element.
 *     
 * 
 * <p>Java class for node.type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="node.type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://graphml.graphdrawing.org/xmlns}desc" minOccurs="0"/>
 *         &lt;choice>
 *           &lt;sequence>
 *             &lt;choice maxOccurs="unbounded" minOccurs="0">
 *               &lt;element ref="{http://graphml.graphdrawing.org/xmlns}data"/>
 *               &lt;element ref="{http://graphml.graphdrawing.org/xmlns}port"/>
 *             &lt;/choice>
 *             &lt;element ref="{http://graphml.graphdrawing.org/xmlns}graph" minOccurs="0"/>
 *           &lt;/sequence>
 *           &lt;element ref="{http://graphml.graphdrawing.org/xmlns}locator"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{http://graphml.graphdrawing.org/xmlns}node.extra.attrib"/>
 *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}NMTOKEN" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "node.type", propOrder = {
    "desc",
    "dataOrPort",
    "graph",
    "locator"
})
@Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2014-04-01T08:06:02+02:00", comments = "JAXB RI v2.2.4-2")
public class NodeType {

    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2014-04-01T08:06:02+02:00", comments = "JAXB RI v2.2.4-2")
    protected String desc;
    @XmlElements({
        @XmlElement(name = "data", type = DataType.class),
        @XmlElement(name = "port", type = PortType.class)
    })
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2014-04-01T08:06:02+02:00", comments = "JAXB RI v2.2.4-2")
    protected List<Object> dataOrPort;
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2014-04-01T08:06:02+02:00", comments = "JAXB RI v2.2.4-2")
    protected GraphType graph;
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2014-04-01T08:06:02+02:00", comments = "JAXB RI v2.2.4-2")
    protected LocatorType locator;
    @XmlAttribute(name = "id", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NMTOKEN")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2014-04-01T08:06:02+02:00", comments = "JAXB RI v2.2.4-2")
    protected String id;
    @XmlAttribute(name = "parse.indegree")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2014-04-01T08:06:02+02:00", comments = "JAXB RI v2.2.4-2")
    protected BigInteger parseIndegree;
    @XmlAttribute(name = "parse.outdegree")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2014-04-01T08:06:02+02:00", comments = "JAXB RI v2.2.4-2")
    protected BigInteger parseOutdegree;

    /**
     * Gets the value of the desc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2014-04-01T08:06:02+02:00", comments = "JAXB RI v2.2.4-2")
    public String getDesc() {
        return desc;
    }

    /**
     * Sets the value of the desc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2014-04-01T08:06:02+02:00", comments = "JAXB RI v2.2.4-2")
    public void setDesc(String value) {
        this.desc = value;
    }

    /**
     * Gets the value of the dataOrPort property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dataOrPort property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDataOrPort().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DataType }
     * {@link PortType }
     * 
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2014-04-01T08:06:02+02:00", comments = "JAXB RI v2.2.4-2")
    public List<Object> getDataOrPort() {
        if (dataOrPort == null) {
            dataOrPort = new ArrayList<Object>();
        }
        return this.dataOrPort;
    }

    /**
     * Gets the value of the graph property.
     * 
     * @return
     *     possible object is
     *     {@link GraphType }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2014-04-01T08:06:02+02:00", comments = "JAXB RI v2.2.4-2")
    public GraphType getGraph() {
        return graph;
    }

    /**
     * Sets the value of the graph property.
     * 
     * @param value
     *     allowed object is
     *     {@link GraphType }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2014-04-01T08:06:02+02:00", comments = "JAXB RI v2.2.4-2")
    public void setGraph(GraphType value) {
        this.graph = value;
    }

    /**
     * Gets the value of the locator property.
     * 
     * @return
     *     possible object is
     *     {@link LocatorType }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2014-04-01T08:06:02+02:00", comments = "JAXB RI v2.2.4-2")
    public LocatorType getLocator() {
        return locator;
    }

    /**
     * Sets the value of the locator property.
     * 
     * @param value
     *     allowed object is
     *     {@link LocatorType }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2014-04-01T08:06:02+02:00", comments = "JAXB RI v2.2.4-2")
    public void setLocator(LocatorType value) {
        this.locator = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2014-04-01T08:06:02+02:00", comments = "JAXB RI v2.2.4-2")
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2014-04-01T08:06:02+02:00", comments = "JAXB RI v2.2.4-2")
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the parseIndegree property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2014-04-01T08:06:02+02:00", comments = "JAXB RI v2.2.4-2")
    public BigInteger getParseIndegree() {
        return parseIndegree;
    }

    /**
     * Sets the value of the parseIndegree property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2014-04-01T08:06:02+02:00", comments = "JAXB RI v2.2.4-2")
    public void setParseIndegree(BigInteger value) {
        this.parseIndegree = value;
    }

    /**
     * Gets the value of the parseOutdegree property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2014-04-01T08:06:02+02:00", comments = "JAXB RI v2.2.4-2")
    public BigInteger getParseOutdegree() {
        return parseOutdegree;
    }

    /**
     * Sets the value of the parseOutdegree property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2014-04-01T08:06:02+02:00", comments = "JAXB RI v2.2.4-2")
    public void setParseOutdegree(BigInteger value) {
        this.parseOutdegree = value;
    }

}
