/**
 * 
 */
package org.diveintojee.poc.interestpoints.domain;

import java.io.Serializable;

/**
 * @author louis.gueye@gmail.com
 */
public class Address implements Serializable {

    /**
	 * 
	 */
    private static final long serialVersionUID = -5732718607385874727L;

    public static final int CONSTRAINT_CITY_MAX_SIZE = 30;

    public static final int CONSTRAINT_POSTAL_CODE_MAX_SIZE = 10;

    public static final int CONSTRAINT_STREET_ADDRESS_MAX_SIZE = 100;

    private String streetAddress;

    private String city;

    private String postalCode;

    private String countryCode;

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Address other = (Address) obj;
        if (city == null) {
            if (other.city != null)
                return false;
        } else if (!city.equals(other.city))
            return false;
        if (countryCode == null) {
            if (other.countryCode != null)
                return false;
        } else if (!countryCode.equals(other.countryCode))
            return false;
        if (postalCode == null) {
            if (other.postalCode != null)
                return false;
        } else if (!postalCode.equals(other.postalCode))
            return false;
        if (streetAddress == null) {
            if (other.streetAddress != null)
                return false;
        } else if (!streetAddress.equals(other.streetAddress))
            return false;
        return true;
    }

    public String getCity() {
        return city;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (city == null ? 0 : city.hashCode());
        result = prime * result + (countryCode == null ? 0 : countryCode.hashCode());
        result = prime * result + (postalCode == null ? 0 : postalCode.hashCode());
        result = prime * result + (streetAddress == null ? 0 : streetAddress.hashCode());
        return result;
    }

    public void setCity(final String city) {
        this.city = city;
    }

    public void setCountryCode(final String countryCode) {
        this.countryCode = countryCode;
    }

    public void setPostalCode(final String postalCode) {
        this.postalCode = postalCode;
    }

    public void setStreetAddress(final String streetAddress) {
        this.streetAddress = streetAddress;
    }

}
