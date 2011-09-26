/*
 *
 */
package org.diveintojee.poc.webmvc;

import static org.mockito.Mockito.verify;

import org.diveintojee.poc.domain.InterestPoint;
import org.diveintojee.poc.domain.service.InterestPointsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * @author louis.gueye@gmail.com
 */
@RunWith(MockitoJUnitRunner.class)
public class InterestPointsControllerTest {

    @InjectMocks
    private final InterestPointsController underTest = new InterestPointsController();

    @Mock
    private InterestPointsService interestPointsService;

    /**
     * Test method for
     * {@link org.diveintojee.poc.webmvc.InterestPointsController#find(org.diveintojee.poc.domain.InterestPoint)}.
     */
    @Test
    public void findShouldInvokeService() {
        // Given
        final InterestPoint interestPoint = new InterestPoint();

        // When
        underTest.find(interestPoint);

        // Then
        verify(interestPointsService).findByExample(interestPoint);
    }

}
