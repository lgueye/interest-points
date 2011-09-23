/*
 *
 */
package org.diveintojee.poc.domain;

import java.io.Serializable;
import java.util.Arrays;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author louis.gueye@gmail.com
 */
@XmlRootElement
public class InterestPoint implements Serializable {

    /**  */
    private static final long serialVersionUID = 6074518431206905892L;

    public static final int CONSTRAINT_DESCRIPTION_MAX_SIZE = 200;

    public static final int CONSTRAINT_NAME_MAX_SIZE = 100;

    private Long id;

    private String name;

    private String description;

    private Address address;

    private InterestPointType type;

    public InterestPoint() {
        setAddress(new Address());
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final InterestPoint other = (InterestPoint) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    public Address getAddress() {
        return address;
    }

    public String getDescription() {
        return description;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public InterestPointType getType() {
        return type;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (id == null ? 0 : id.hashCode());
        return result;
    }

    public void setAddress(final Address address) {
        this.address = address;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setType(final InterestPointType type) {
        this.type = type;
    }

    /**
     * @see java.lang.Object#toString()
     */

    @Override
    public String toString() {
        ReflectionToStringBuilder.setDefaultStyle(ToStringStyle.SHORT_PREFIX_STYLE);
        return ReflectionToStringBuilder.toStringExclude(this, Arrays.asList("description"));
    }

}
