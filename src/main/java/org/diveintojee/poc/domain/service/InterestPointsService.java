/*
 *
 */
package org.diveintojee.poc.domain.service;

import java.util.List;

import org.diveintojee.poc.domain.InterestPoint;

/**
 * @author louis.gueye@gmail.com
 */
public interface InterestPointsService {

    /**
     * @param interestPoint
     * @return
     */
    List<InterestPoint> findByExample(InterestPoint interestPoint);

}
