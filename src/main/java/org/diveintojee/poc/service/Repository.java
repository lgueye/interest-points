/*
 *
 */
package org.diveintojee.poc.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.diveintojee.poc.domain.Address;
import org.diveintojee.poc.domain.InterestPoint;
import org.diveintojee.poc.domain.InterestPointType;

/**
 * @author louis.gueye@gmail.com
 */
public class Repository {

    private static final String charSet = "azertyuiopqsdfghjklmwxcvbnAZERTYUIOPQSDFGHJKLMWXCVBN7894561230";

    /**
     * @return
     */
    public static Address validAddress() {
        final Address address = new Address();
        address.setCity(RandomStringUtils.random(Address.CONSTRAINT_CITY_MAX_SIZE, Repository.charSet));
        address.setCountryCode("fr");
        address.setPostalCode(RandomStringUtils.random(Address.CONSTRAINT_POSTAL_CODE_MAX_SIZE, Repository.charSet));
        address.setStreetAddress(RandomStringUtils.random(Address.CONSTRAINT_STREET_ADDRESS_MAX_SIZE,
            Repository.charSet));
        return address;
    }

    /**
     * @return
     */
    public static InterestPoint validInterestPoint() {
        final InterestPoint result = new InterestPoint();
        result.setAddress(Repository.validAddress());
        result.setDescription(RandomStringUtils.random(InterestPoint.CONSTRAINT_DESCRIPTION_MAX_SIZE,
            Repository.charSet));
        result.setName(RandomStringUtils.random(InterestPoint.CONSTRAINT_NAME_MAX_SIZE, Repository.charSet));
        final List<InterestPointType> randomValues = Arrays.asList(InterestPointType.values());
        Collections.shuffle(randomValues);
        result.setType(randomValues.get(0));
        return result;
    }

}
