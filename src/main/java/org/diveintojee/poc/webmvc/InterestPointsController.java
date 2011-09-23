/*
 *
 */
package org.diveintojee.poc.webmvc;

import java.util.List;

import org.diveintojee.poc.domain.InterestPoint;
import org.diveintojee.poc.domain.service.InterestPointsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author louis.gueye@gmail.com
 */
@Controller
public class InterestPointsController {

    @Autowired
    private InterestPointsService interestPointsService;

    @RequestMapping(value = "/find", method = RequestMethod.GET)
    @ResponseBody
    public List<InterestPoint> find(final InterestPoint interestPoint) {

        return interestPointsService.findByExample(interestPoint);

    }
}
