import org.diveintojee.poc.domain.InterestPoint
import org.diveintojee.poc.domain.InterestPointType
import org.junit.Assert
import org.springframework.context.support.ClassPathXmlApplicationContext
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter
import org.springframework.web.client.RestTemplate

scenario "finding interest points around me should succeed" , {

    def HttpHeaders headers = new HttpHeaders()
    def uri
    def ctx = new ClassPathXmlApplicationContext("classpath:stories-context.xml")
    def RestTemplate restTemplate = ctx.getBean("restTemplate")
    def ResponseEntity<List<InterestPoint>> responseEntity
    def results

    def streetAddress = "10%20bd%20haussmann"
    def postalCode = "75009"
    def city = "paris"
    def countryCode = "fr"
    def requestContentType = "application/x-www-form-urlencoded"
    def responseContentType = "application/x-www-form-urlencoded"
    def expectedResultsCount = 21
    def expectedRestaurantsCount = 5
    def expectedPubsCount = 9
    def expectedSubwayStationsCount = 2
    
    given "I am a valid system user", {}

    and "I provide the location '10 bd haussmann'", {}
    
    and "I send ${requestContentType}", {
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED)
    }
    
    and "I receive ${responseContentType}", {
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON))
        headers.setAcceptCharset(Arrays.asList(MappingJacksonHttpMessageConverter.DEFAULT_CHARSET))
    }
    
    when "I ask for interest points around that location", {
        
        StringBuilder queryString = new StringBuilder("http://localhost:9090/interest-points/find?")
        queryString.append("address.city=${city}")
        queryString.append("&")
        queryString.append("address.countryCode=${countryCode}")
        queryString.append("&")
        queryString.append("address.postalCode=${postalCode}")
        queryString.append("&")
        queryString.append("address.streetAddress=${streetAddress}")
        uri = URI.create(queryString.toString())
        HttpEntity<InterestPoint> requestEntity = new HttpEntity<InterestPoint>(headers)
        responseEntity = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, List.class)

    }
    
    then "I should get a successfull response", {
        Assert.assertNotNull(responseEntity)
        Assert.assertNotNull(responseEntity.getStatusCode())
        Assert.assertEquals(200, responseEntity.getStatusCode().value())
    }

    and "The response should include ${expectedResultsCount} interest points", {
        Assert.assertTrue(responseEntity.hasBody())
        Assert.assertNotNull(responseEntity.getBody())
        results = responseEntity.getBody()
        Assert.assertEquals(expectedResultsCount, results.size())
    }

    and "The response should include ${expectedRestaurantsCount} restaurants", {
        Assert.assertTrue(responseEntity.hasBody())
        Assert.assertNotNull(responseEntity.getBody())
        results = responseEntity.getBody()
        Assert.assertEquals(expectedRestaurantsCount, results. findAll {it.type == "RESTAURANT" }.size())
    }
    
    and "The response should include ${expectedPubsCount} pubs", {
        Assert.assertTrue(responseEntity.hasBody())
        Assert.assertNotNull(responseEntity.getBody())
        results = responseEntity.getBody()
        Assert.assertEquals(expectedPubsCount, results. findAll {it.type == "PUB" }.size())
    }
    
    and "The response should include ${expectedSubwayStationsCount} subway stations", {
        Assert.assertTrue(responseEntity.hasBody())
        Assert.assertNotNull(responseEntity.getBody())
        results = responseEntity.getBody()
        Assert.assertEquals(expectedSubwayStationsCount, results. findAll {it.type == "SUBWAY_STATION" }.size())
    }
    
    
}