/*
 *
 */
package org.diveintojee.poc.interestpoints.webmvc;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.diveintojee.poc.interestpoints.domain.InterestPoint;
import org.diveintojee.poc.interestpoints.domain.service.InterestPointsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author louis.gueye@gmail.com
 */
@Controller
public class InterestPointsController {

    @Autowired
    private InterestPointsService interestPointsService;

    /**
     * @param interestPoint
     * @return Until <a href="https://jira.springsource.org/browse/SPR-7023">this</a> issue is not fixed, return array
     *         instead of collections.<br/>
     *         Allows for proper collection mapping
     */
    @RequestMapping(value = "/find", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody
    InterestPoint[] find(final InterestPoint interestPoint) {

        final List<InterestPoint> results = interestPointsService.findByExample(interestPoint);

        if (CollectionUtils.isEmpty(results))
            return new InterestPoint[] {};

        final InterestPoint[] array = new InterestPoint[CollectionUtils.size(results)];

        for (int i = 0; i < results.size(); i++) {
            array[i] = results.get(i);
        }

        return array;

    }

    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public void resolveExceptions(final Throwable th) {
        th.printStackTrace();
    }
}
