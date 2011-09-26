import org.diveintojee.poc.domain.InterestPoint
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

    def streetAddress
    def postalCode
    def city
    def countryCode
    def requestContentType = "application/x-www-form-urlencoded"
    def responseContentType = "application/x-www-form-urlencoded"
    def expectedResultsCount = 21
    def expectedRestaurantsCount = 5
    def expectedPubsCount = 9
    def expectedSubwayStationsCount = 2
    
    given "I am a valid system user", {}

    and "I provide the location '10 bd haussmann'", {
        streetAddress = "10%20bd%20haussmann"
        postalCode = "75009"
        city = "paris"
        countryCode = "fr"
    }
    
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
        responseEntity.shouldNotBe null
        responseEntity.getStatusCode().shouldNotBe null
        responseEntity.getStatusCode().value().shouldEqual 200
    }

    and "The response should include ${expectedResultsCount} interest points", {
        responseEntity.hasBody().shouldBe true
        responseEntity.getBody().shouldNotBeNull
        results = responseEntity.getBody()
        results.size().shouldEqual expectedResultsCount
    }

    and "The response should include ${expectedRestaurantsCount} restaurants", {
        responseEntity.hasBody().shouldBe true
        responseEntity.getBody().shouldNotBeNull
        results = responseEntity.getBody()
        int filteredCount = results. findAll {it.type == "RESTAURANT" }.size() 
        filteredCount.shouldEqual expectedRestaurantsCount
    }
    
    and "The response should include ${expectedPubsCount} pubs", {
        responseEntity.hasBody().shouldBe true
        responseEntity.getBody().shouldNotBeNull
        results = responseEntity.getBody()
        int filteredCount = results. findAll {it.type == "PUB" }.size() 
        filteredCount.shouldEqual expectedPubsCount
    }
    
    and "The response should include ${expectedSubwayStationsCount} subway stations", {
        responseEntity.hasBody().shouldBe true
        responseEntity.getBody().shouldNotBeNull
        results = responseEntity.getBody()
        int filteredCount = results. findAll {it.type == "SUBWAY_STATION" }.size() 
        filteredCount.shouldEqual expectedSubwayStationsCount
    }
    
    
}