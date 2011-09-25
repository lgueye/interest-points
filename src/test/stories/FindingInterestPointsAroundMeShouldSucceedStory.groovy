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

    def location
    def HttpHeaders headers = new HttpHeaders()
    def uri
    def ctx = new ClassPathXmlApplicationContext("classpath:stories-context.xml")
    def RestTemplate restTemplate = ctx.getBean("restTemplate")
    def ResponseEntity<List<InterestPoint>> responseEntity
    def results

    given "I am a valid system user", {}

    and "I provide the location '10 bd haussmann, 75009 paris'", { location ="10 bd haussmann, 75009 paris" }
    
    and "I send 'application/x-www-form-urlencoded'", {
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED)
    }
    
    and "I receive 'application/json'", {
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON))
        headers.setAcceptCharset(Arrays.asList(MappingJacksonHttpMessageConverter.DEFAULT_CHARSET))
    }
    
    when "I ask for interest points around that location", {
        
        StringBuilder queryString = new StringBuilder("http://localhost:9090/interest-points/find?")
        queryString.append("address.city=paris")
        queryString.append("&")
        queryString.append("address.countryCode=fr")
        queryString.append("&")
        queryString.append("address.postalCode=75009")
        queryString.append("&")
        queryString.append("address.streetAddress=10%20bd%20haussmann")
        uri = URI.create(queryString.toString())
        HttpEntity<InterestPoint> requestEntity = new HttpEntity<InterestPoint>(headers)
        responseEntity = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, List.class)

    }
    
    then "I should get a successfull response", {
        Assert.assertNotNull(responseEntity)
        Assert.assertNotNull(responseEntity.getStatusCode())
        Assert.assertEquals(200, responseEntity.getStatusCode().value())
    }

    and "The response should include 21 interest points", {
        Assert.assertTrue(responseEntity.hasBody())
        Assert.assertNotNull(responseEntity.getBody())
        results = responseEntity.getBody()
        Assert.assertEquals(21, results.size())
    }

    and "The response should include 5 restaurants", {
        Assert.assertTrue(responseEntity.hasBody())
        Assert.assertNotNull(responseEntity.getBody())
        results = responseEntity.getBody()
        Assert.assertEquals(5, results. findAll {it.type == "RESTAURANT" }.size())
    }
    
    and "The response should include 9 pubs", {
        Assert.assertTrue(responseEntity.hasBody())
        Assert.assertNotNull(responseEntity.getBody())
        results = responseEntity.getBody()
        Assert.assertEquals(9, results. findAll {it.type == "PUB" }.size())
    }
    
    and "The response should include 2 subway stations", {
        Assert.assertTrue(responseEntity.hasBody())
        Assert.assertNotNull(responseEntity.getBody())
        results = responseEntity.getBody()
        Assert.assertEquals(2, results. findAll {it.type == "SUBWAY_STATION" }.size())
    }
    
    
}