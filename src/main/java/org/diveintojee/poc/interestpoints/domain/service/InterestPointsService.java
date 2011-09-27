/*
 *
 */
package org.diveintojee.poc.interestpoints.domain.service;

import java.util.List;

import org.diveintojee.poc.interestpoints.domain.InterestPoint;

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
