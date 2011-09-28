/*
 *
 */
package org.diveintojee.poc.interestpoints.webmvc;

import java.util.List;

import org.diveintojee.poc.interestpoints.domain.InterestPoint;
import org.diveintojee.poc.interestpoints.domain.service.InterestPointsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
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

    @RequestMapping(value = "/find", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody
    List<InterestPoint> find(final InterestPoint interestPoint) {

        final List<InterestPoint> results = interestPointsService.findByExample(interestPoint);

        return results;

    }
}
