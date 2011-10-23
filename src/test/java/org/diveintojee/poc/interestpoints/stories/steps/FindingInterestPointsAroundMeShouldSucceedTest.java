/*
 *
 */
package org.diveintojee.poc.interestpoints.stories.steps;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author louis.gueye@gmail.com
 */
public class FindingInterestPointsAroundMeShouldSucceedTest {

	private FindingInterestPointsAroundMeShouldSucceed	underTest	= null;

	@Before
	public void before() {
		this.underTest = new FindingInterestPointsAroundMeShouldSucceed();
	}

	@Test
	public void extractCityShouldMatch() {
		final String location = " a , b c";
		final String extract = this.underTest.extractCity(location);
		Assert.assertEquals("c", extract);
	}

	@Test
	public void extractCityShouldNotMatch0() {
		final String location = null;
		final String extract = this.underTest.extractCity(location);
		Assert.assertEquals(StringUtils.EMPTY, extract);
	}

	@Test
	public void extractCityShouldNotMatch1() {
		final String location = StringUtils.EMPTY;
		final String extract = this.underTest.extractCity(location);
		Assert.assertEquals(StringUtils.EMPTY, extract);
	}

	@Test
	public void extractCityShouldNotMatch2() {
		final String location = " a , b:c";
		final String extract = this.underTest.extractCity(location);
		Assert.assertEquals(StringUtils.EMPTY, extract);
	}

	@Test
	public void extractCityShouldNotMatch3() {
		final String location = " a , b c d e";
		final String extract = this.underTest.extractCity(location);
		Assert.assertEquals(StringUtils.EMPTY, extract);
	}

	@Test
	public void extractPostalCodeShouldMatch() {
		final String location = " a , b c";
		final String extract = this.underTest.extractPostalCode(location);
		Assert.assertEquals("b", extract);
	}

	@Test
	public void extractPostalCodeShouldNotMatch0() {
		final String location = null;
		final String extract = this.underTest.extractPostalCode(location);
		Assert.assertEquals(StringUtils.EMPTY, extract);
	}

	@Test
	public void extractPostalCodeShouldNotMatch1() {
		final String location = StringUtils.EMPTY;
		final String extract = this.underTest.extractPostalCode(location);
		Assert.assertEquals(StringUtils.EMPTY, extract);
	}

	@Test
	public void extractPostalCodeShouldNotMatch2() {
		final String location = " a , b:c";
		final String extract = this.underTest.extractPostalCode(location);
		Assert.assertEquals(StringUtils.EMPTY, extract);
	}

	@Test
	public void extractPostalCodeShouldNotMatch3() {
		final String location = " a , b c d e";
		final String extract = this.underTest.extractPostalCode(location);
		Assert.assertEquals(StringUtils.EMPTY, extract);
	}

	@Test
	public void extractStreetAddressShouldMatch() {
		final String location = " a , b";
		final String extract = this.underTest.extractStreetAddress(location);
		Assert.assertEquals("a", extract);
	}

	@Test
	public void extractStreetAddressShouldNotMatch0() {
		final String location = null;
		final String extract = this.underTest.extractStreetAddress(location);
		Assert.assertEquals(StringUtils.EMPTY, extract);
	}

	@Test
	public void extractStreetAddressShouldNotMatch1() {
		final String location = StringUtils.EMPTY;
		final String extract = this.underTest.extractStreetAddress(location);
		Assert.assertEquals(StringUtils.EMPTY, extract);
	}

	@Test
	public void extractStreetAddressShouldNotMatch2() {
		final String location = "vxcvxcvxcxc vxcvxvxcv";
		final String extract = this.underTest.extractStreetAddress(location);
		Assert.assertEquals(StringUtils.EMPTY, extract);
	}

	@Test
	public void extractStreetAddressShouldNotMatch3() {
		final String location = "vxcvxcvxcxcvxcvxvxcv";
		final String extract = this.underTest.extractStreetAddress(location);
		Assert.assertEquals(StringUtils.EMPTY, extract);
	}

	@Test
	public void extractStreetAddressShouldNotMatch4() {
		final String location = " a , b, c";
		final String extract = this.underTest.extractStreetAddress(location);
		Assert.assertEquals(StringUtils.EMPTY, extract);
	}
}
