Find interest points by example

Meta:
@refs 12496

Narrative:

Given I provide a location
When I look for interest points
The system should return interestpoints arround that location

Scenario: Find interest points by example should succeed
    
Given I am a valid system user
And I provide the location 10 bd haussmann, 75009 paris
And I send application/x-www-form-urlencoded
And I receive <response-contenttype>
When I ask for interest points around that location
Then I should get a successfull response
And The response should include 21 interest points
And The response should include 5 restaurants
And The response should include 9 pubs
And The response should include 2 subway stations

Examples:
|response-contenttype|
|application/xml|
|application/json|