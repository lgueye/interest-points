/*
 *
 */
package org.diveintojee.poc.interestpoints.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.diveintojee.poc.interestpoints.domain.InterestPoint;
import org.diveintojee.poc.interestpoints.domain.InterestPointType;
import org.diveintojee.poc.interestpoints.domain.service.InterestPointsService;
import org.springframework.stereotype.Service;

/**
 * @author louis.gueye@gmail.com
 */
@Service
public class InterestPointsServiceImpl implements InterestPointsService {

    /**
     * @see org.diveintojee.poc.interestpoints.domain.service.InterestPointsService#findByExample(org.diveintojee.poc.interestpoints.domain.InterestPoint)
     */
    @Override
    public List<InterestPoint> findByExample(final InterestPoint criteria) {
        final List<InterestPoint> result = new ArrayList<InterestPoint>();
        for (int id = 1; id <= 21; id++) {
            final InterestPoint interestPoint = Repository.validInterestPoint();
            interestPoint.setId(Long.valueOf(id));
            result.add(interestPoint);
        }
        Collections.shuffle(result);
        int countRestaurants = 5;
        int countPubs = 9;
        int countSubwayStations = 2;
        for (final InterestPoint interestPoint : result) {
            if (countRestaurants != 0) {
                interestPoint.setType(InterestPointType.RESTAURANT);
                countRestaurants--;
            } else if (countPubs != 0) {
                interestPoint.setType(InterestPointType.PUB);
                countPubs--;
            } else if (countSubwayStations != 0) {
                interestPoint.setType(InterestPointType.SUBWAY_STATION);
                countSubwayStations--;
            } else {
                interestPoint.setType(InterestPointType.MUSEUM);
            }
        }

        return result;
    }
}
