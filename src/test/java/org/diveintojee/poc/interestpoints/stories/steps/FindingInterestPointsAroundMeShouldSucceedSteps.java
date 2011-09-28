/*
 *
 */
package org.diveintojee.poc.interestpoints.stories.steps;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.diveintojee.poc.interestpoints.domain.InterestPoint;
import org.diveintojee.poc.interestpoints.domain.InterestPointType;
import org.diveintojee.poc.interestpoints.stories.Scenario;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * @author louis.gueye@gmail.com
 */
@Scenario
public class FindingInterestPointsAroundMeShouldSucceedSteps {

    private final HttpHeaders headers = new HttpHeaders();
    private URI uri;
    private ResponseEntity<List> responseEntity;
    private List<InterestPoint> results;
    private String streetAddress;
    private String postalCode;
    private String city;
    private String countryCode;

    @Autowired
    private RestTemplate restTemplate;

    @Then("The response should include $expectedPubsCount pubs")
    public void ensureExpectedPubsCount(final int expectedPubsCount) {

        Assert.assertNotNull(results);

        final int countMatches = CollectionUtils.countMatches(results, new Predicate() {

            @Override
            public boolean evaluate(final Object object) {

                if (object == null)
                    return false;

                if (!(object instanceof InterestPoint))
                    return false;

                return InterestPointType.PUB.equals(((InterestPoint) object).getType());

            }

        });

        Assert.assertEquals(expectedPubsCount, countMatches);

    }

    @Then("The response should include $expectedRestaurantsCount restaurants")
    public void ensureExpectedRestaurantsCount(final int expectedRestaurantsCount) {

        Assert.assertNotNull(results);

        final int countMatches = CollectionUtils.countMatches(results, new Predicate() {

            @Override
            public boolean evaluate(final Object object) {

                if (object == null)
                    return false;
                System.out.println("object is not null");

                System.out.println("object is of type " + object.getClass());
                System.out.println(((Map) object).keySet());
                if (!(object instanceof InterestPoint))
                    return false;
                System.out.println("object is of type InterestPoint");

                return InterestPointType.RESTAURANT == ((InterestPoint) object).getType();

            }

        });

        Assert.assertEquals(expectedRestaurantsCount, countMatches);

    }

    @SuppressWarnings("unchecked")
    @Then("The response should include $expectedResultsCount interest points")
    public void ensureExpectedResultsCount(final int expectedResultsCount) {
        Assert.assertNotNull(responseEntity);
        Assert.assertTrue(responseEntity.hasBody());
        results = responseEntity.getBody();
        Assert.assertNotNull(results);
        Assert.assertEquals(expectedResultsCount, results.size());
    }

    @Then("The response should include $expectedSubwayStationsCount subway stations")
    public void ensureExpectedSubwayStationsCount(final int expectedSubwayStationsCount) {

        final int countMatches = CollectionUtils.countMatches(results, new Predicate() {

            @Override
            public boolean evaluate(final Object object) {

                if (object == null)
                    return false;

                if (!(object instanceof InterestPoint))
                    return false;

                return InterestPointType.SUBWAY_STATION.equals(((InterestPoint) object).getType());

            }

        });

        Assert.assertEquals(expectedSubwayStationsCount, countMatches);

    }

    @Then("I should get a successfull response")
    public void ensureSuccessfullResponse() {
        Assert.assertNotNull(responseEntity);
        Assert.assertNotNull(responseEntity.getStatusCode());
        Assert.assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCode().value());
    }

    /**
     * @param location
     * @return
     */
    private String extractCity(final String location) {

        if (StringUtils.isEmpty(location))
            return StringUtils.EMPTY;

        final String right = location.split(",")[1];

        if (StringUtils.isEmpty(right))
            return StringUtils.EMPTY;

        return right.split("")[1];

    }

    /**
     * @param location
     * @return
     */
    private String extractPostalCode(final String location) {

        if (StringUtils.isEmpty(location))
            return StringUtils.EMPTY;

        final String right = location.split(",")[1];

        if (StringUtils.isEmpty(right))
            return StringUtils.EMPTY;

        return right.split("")[0];

    }

    /**
     * @param location
     * @return
     */
    private String extractStreetAddress(final String location) {

        if (StringUtils.isEmpty(location))
            return StringUtils.EMPTY;

        return location.split(",")[0].trim();

    }

    @Given("I provide the location $location")
    public void provideLocation(final String location) {
        streetAddress = extractStreetAddress(location);
        city = extractCity(location);
        postalCode = extractPostalCode(location);
        countryCode = "fr";
    }

    @Given("I send $requestContentType")
    public void provideRequestContentType(final String requestContentType) {
        headers.setContentType(MediaType.valueOf(requestContentType));
    }

    @Given("I receive $requestContentType")
    public void provideResponseContentType(final String responseContentType) {
        headers.setAccept(Arrays.asList(MediaType.valueOf(responseContentType)));
        headers.setAcceptCharset(Arrays.asList(MappingJacksonHttpMessageConverter.DEFAULT_CHARSET));
    }

    @Given("I am a valid system user")
    public void provideValidUser() {}

    @When("I ask for interest points around that location")
    public void requestInterestPointsAroundLocation() throws UnsupportedEncodingException {
        final StringBuilder queryString = new StringBuilder("http://localhost:9090/interest-points/find?");
        queryString.append("address.city=" + URLEncoder.encode(city, "UTF-8"));
        queryString.append("&");
        queryString.append("address.countryCode=" + URLEncoder.encode(countryCode, "UTF-8"));
        queryString.append("&");
        queryString.append("address.postalCode=" + URLEncoder.encode(postalCode, "UTF-8"));
        queryString.append("&");
        queryString.append("address.streetAddress=" + URLEncoder.encode(streetAddress, "UTF-8"));
        uri = URI.create(queryString.toString());
        final HttpEntity<InterestPoint> requestEntity = new HttpEntity<InterestPoint>(headers);
        responseEntity = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, List.class);
    }
}
