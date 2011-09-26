/*
 *
 */
package org.diveintojee.poc.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.diveintojee.poc.domain.InterestPoint;
import org.diveintojee.poc.domain.InterestPointType;
import org.diveintojee.poc.domain.service.InterestPointsService;
import org.junit.Before;
import org.junit.Test;

/**
 * @author louis.gueye@gmail.com
 */
public class InterestPointsServiceImplTest {

    private InterestPointsService underTest = null;

    @Before
    public void before() {
        underTest = new InterestPointsServiceImpl();
    }

    /**
     * Test method for
     * {@link org.diveintojee.poc.service.InterestPointsServiceImpl#findByExample(org.diveintojee.poc.domain.InterestPoint)}
     * .
     */
    @Test
    public final void findByExampleShouldReturn21InterestPoints() {
        // Given
        final InterestPoint example = new InterestPoint();
        example.getAddress().setCity("paris");
        example.getAddress().setCountryCode("fr");
        example.getAddress().setPostalCode("75009");
        example.getAddress().setPostalCode("10 bd haussmann");

        // When
        final List<InterestPoint> results = underTest.findByExample(example);

        // Then
        assertEquals(21, CollectionUtils.size(results));
    }

    /**
     * Test method for
     * {@link org.diveintojee.poc.service.InterestPointsServiceImpl#findByExample(org.diveintojee.poc.domain.InterestPoint)}
     * .
     */
    @Test
    public final void findByExampleShouldReturn2subwayStations() {
        // Given
        final InterestPoint example = new InterestPoint();
        example.getAddress().setCity("paris");
        example.getAddress().setCountryCode("fr");
        example.getAddress().setPostalCode("75009");
        example.getAddress().setPostalCode("10 bd haussmann");

        // When
        final List<InterestPoint> results = underTest.findByExample(example);

        CollectionUtils.filter(results, new Predicate() {

            @Override
            public boolean evaluate(final Object object) {

                return object instanceof InterestPoint
                    && ((InterestPoint) object).getType() == InterestPointType.SUBWAY_STATION;
            }

        });

        // Then
        assertEquals(2, CollectionUtils.size(results));
    }

    /**
     * Test method for
     * {@link org.diveintojee.poc.service.InterestPointsServiceImpl#findByExample(org.diveintojee.poc.domain.InterestPoint)}
     * .
     */
    @Test
    public final void findByExampleShouldReturn5restaurants() {
        // Given
        final InterestPoint example = new InterestPoint();
        example.getAddress().setCity("paris");
        example.getAddress().setCountryCode("fr");
        example.getAddress().setPostalCode("75009");
        example.getAddress().setPostalCode("10 bd haussmann");

        // When
        final List<InterestPoint> results = underTest.findByExample(example);

        CollectionUtils.filter(results, new Predicate() {

            @Override
            public boolean evaluate(final Object object) {

                return object instanceof InterestPoint
                    && ((InterestPoint) object).getType() == InterestPointType.RESTAURANT;
            }

        });

        // Then
        assertEquals(5, CollectionUtils.size(results));
    }

    /**
     * Test method for
     * {@link org.diveintojee.poc.service.InterestPointsServiceImpl#findByExample(org.diveintojee.poc.domain.InterestPoint)}
     * .
     */
    @Test
    public final void findByExampleShouldReturn9pubs() {
        // Given
        final InterestPoint example = new InterestPoint();
        example.getAddress().setCity("paris");
        example.getAddress().setCountryCode("fr");
        example.getAddress().setPostalCode("75009");
        example.getAddress().setPostalCode("10 bd haussmann");

        // When
        final List<InterestPoint> results = underTest.findByExample(example);

        CollectionUtils.filter(results, new Predicate() {

            @Override
            public boolean evaluate(final Object object) {

                return object instanceof InterestPoint && ((InterestPoint) object).getType() == InterestPointType.PUB;
            }

        });

        // Then
        assertEquals(9, CollectionUtils.size(results));
    }

}
