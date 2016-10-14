/**
 * 广州宏天软件有限公司版权所有
 */
package org.mybatis.spring.mapping.entity;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}mappingLocation" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "mappingLocation"
})
@XmlRootElement(name = "mappings")
public class Mappings {

    @XmlElement(required = true)
    protected List<MappingLocation> mappingLocation;

    /**
     * Gets the value of the mappingLocation property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the mappingLocation property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMappingLocation().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MappingLocation }
     * 
     * 
     */
    public List<MappingLocation> getMappingLocation() {
        if (mappingLocation == null) {
            mappingLocation = new ArrayList<MappingLocation>();
        }
        return this.mappingLocation;
    }

}
