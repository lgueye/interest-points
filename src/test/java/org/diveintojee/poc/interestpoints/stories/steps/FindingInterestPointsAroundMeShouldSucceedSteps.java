/*
 *
 */
package org.diveintojee.poc.interestpoints.stories.steps;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.Arrays;

import junit.framework.Assert;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.diveintojee.poc.interestpoints.domain.InterestPoint;
import org.diveintojee.poc.interestpoints.domain.InterestPointType;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
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
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author louis.gueye@gmail.com
 */
@Component
public class FindingInterestPointsAroundMeShouldSucceedSteps {

	private final HttpHeaders				headers		= new HttpHeaders();
	private final String					endPoint	= "http://localhost:9090/interest-points/find";
	private URI								uri;
	private ResponseEntity<InterestPoint[]>	responseEntity;
	private InterestPoint[]					results;
	private String							streetAddress;
	private String							postalCode;
	private String							city;
	private String							countryCode;

	@Autowired
	private RestTemplate					restTemplate;

	@Then("The response should include $expectedPubsCount pubs")
	public void ensureExpectedPubsCount(@Named("expectedPubsCount") final int expectedPubsCount) {

		Assert.assertNotNull(this.results);

		final int countMatches = CollectionUtils.countMatches(Arrays.asList(this.results), new Predicate() {

			@Override
			public boolean evaluate(final Object object) {

				if (object == null) return false;

				if (!(object instanceof InterestPoint)) return false;

				return InterestPointType.PUB.equals(((InterestPoint) object).getType());

			}

		});

		Assert.assertEquals(expectedPubsCount, countMatches);

	}

	@Then("The response should include $expectedRestaurantsCount restaurants")
	public void ensureExpectedRestaurantsCount(@Named("expectedRestaurantsCount") final int expectedRestaurantsCount) {

		Assert.assertNotNull(this.results);

		final int countMatches = CollectionUtils.countMatches(Arrays.asList(this.results), new Predicate() {

			@Override
			public boolean evaluate(final Object object) {

				if (object == null) return false;

				if (!(object instanceof InterestPoint)) return false;

				return InterestPointType.RESTAURANT == ((InterestPoint) object).getType();

			}

		});

		Assert.assertEquals(expectedRestaurantsCount, countMatches);

	}

	@Then("The response should include $expectedResultsCount interest points")
	public void ensureExpectedResultsCount(@Named("expectedResultsCount") final int expectedResultsCount) {
		Assert.assertNotNull(this.responseEntity);
		Assert.assertTrue(this.responseEntity.hasBody());
		this.results = this.responseEntity.getBody();
		Assert.assertNotNull(this.results);
		Assert.assertEquals(expectedResultsCount, this.results.length);
	}

	@Then("The response should include $expectedSubwayStationsCount subway stations")
	public void ensureExpectedSubwayStationsCount(
			@Named("expectedSubwayStationsCount") final int expectedSubwayStationsCount) {

		final int countMatches = CollectionUtils.countMatches(Arrays.asList(this.results), new Predicate() {

			@Override
			public boolean evaluate(final Object object) {

				if (object == null) return false;

				if (!(object instanceof InterestPoint)) return false;

				return InterestPointType.SUBWAY_STATION.equals(((InterestPoint) object).getType());

			}

		});

		Assert.assertEquals(expectedSubwayStationsCount, countMatches);

	}

	@Then("I should get a successfull response")
	public void ensureSuccessfullResponse() {
		Assert.assertNotNull(this.responseEntity);
		Assert.assertNotNull(this.responseEntity.getStatusCode());
		Assert.assertEquals(HttpStatus.OK.value(), this.responseEntity.getStatusCode().value());
	}

	/**
	 * @param location
	 * @return
	 */
	String extractCity(final String location) {

		String[] split = null;

		split = splitInTwoParts(location, ",");

		if (ArrayUtils.isEmpty(split)) return StringUtils.EMPTY;

		final String right = split[1];

		if (StringUtils.isEmpty(right)) return StringUtils.EMPTY;

		split = splitInTwoParts(right, " ");

		if (ArrayUtils.isEmpty(split)) return StringUtils.EMPTY;

		return split[1];

	}

	/**
	 * @param location
	 * @return
	 */
	String extractPostalCode(final String location) {

		String[] split = null;

		split = splitInTwoParts(location, ",");

		if (ArrayUtils.isEmpty(split)) return StringUtils.EMPTY;

		final String right = split[1];

		if (StringUtils.isEmpty(right)) return StringUtils.EMPTY;

		split = splitInTwoParts(right, " ");

		if (ArrayUtils.isEmpty(split)) return StringUtils.EMPTY;

		return split[0];

	}

	/**
	 * @param location
	 * @return
	 */
	String extractStreetAddress(final String location) {

		String[] split = null;

		split = splitInTwoParts(location, ",");

		if (ArrayUtils.isEmpty(split)) return StringUtils.EMPTY;

		return split[0].trim();

	}

	@Given("I provide the location $location")
	public void provideLocation(final String location) {
		this.streetAddress = extractStreetAddress(location);
		this.city = extractCity(location);
		this.postalCode = extractPostalCode(location);
		this.countryCode = "fr";
	}

	@Given("I send $requestContentType")
	public void provideRequestContentType(@Named("requestContentType") final String requestContentType) {

		this.headers.setContentType(MediaType.valueOf(requestContentType));
	}

	@Given("I receive <response-contenttype>")
	public void provideResponseContentType(@Named("response-contenttype") final String responseContentType) {
		this.headers.setAccept(Arrays.asList(MediaType.valueOf(responseContentType)));
		this.headers.setAcceptCharset(Arrays.asList(MappingJacksonHttpMessageConverter.DEFAULT_CHARSET));
	}

	@Given("I am a valid system user")
	public void provideValidUser() {
	}

	@When("I ask for interest points around that location")
	public void requestInterestPointsAroundLocation() throws UnsupportedEncodingException {
		final StringBuilder queryString = new StringBuilder(this.endPoint);
		queryString.append("?");
		queryString.append("address.city=" + URLEncoder.encode(this.city, "UTF-8"));
		queryString.append("&");
		queryString.append("address.countryCode=" + URLEncoder.encode(this.countryCode, "UTF-8"));
		queryString.append("&");
		queryString.append("address.postalCode=" + URLEncoder.encode(this.postalCode, "UTF-8"));
		queryString.append("&");
		queryString.append("address.streetAddress=" + URLEncoder.encode(this.streetAddress, "UTF-8"));
		this.uri = URI.create(queryString.toString());
		final HttpEntity<InterestPoint> requestEntity = new HttpEntity<InterestPoint>(this.headers);
		this.responseEntity = this.restTemplate
				.exchange(this.uri, HttpMethod.GET, requestEntity, InterestPoint[].class);
	}

	private String[] splitInTwoParts(final String string, final String pattern) {

		if (StringUtils.isEmpty(string)) return null;

		if (StringUtils.isEmpty(pattern)) return null;

		final String[] split = string.trim().split(pattern);

		if (ArrayUtils.isEmpty(split)) return null;

		// If pattern not found split returns same string in array
		if (split.length != 2) return null;

		return split;

	}
}
