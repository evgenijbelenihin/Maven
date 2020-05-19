Feature: Getting chart data

  Scenario Outline: Getting chart data from MICROSERVICES_SERVER_URL with variety of securities and bartypes using get request

    Given I have MICROSERVICES_SERVER_URL equals 'https://dev-services.maximarkets.org'
    And I get securities from MICROSERVICES_SERVER_URL + '/srvgtw/marketdata/v1/securities'
    And I have an url for chart data request equals MICROSERVICES_SERVER_URL + '/srvgtw/marketdata/v1/chart_data?' + params
    When I send get request for every security with <bartype> and <count> parameters
    Then Response status code equals 200
    And Names of fields in response in container bars should be equal with data in table
      | Field1   | Field2   | Field3  | Field4  | Field5 | Field6 | Field7  | Field8  | Field9 |
      | closeAsk | closeBid | highAsk | highBid | lowAsk | lowBid | openAsk | openBid | time   |

    Examples:
      | bartype | count |
      | MINUTE  | 50    |
    #  | MINUTE5  |45|
    #  | MINUTE15 |40|
    #  | MINUTE30 |35|
     # | HOUR     |30|
    #  | HOUR4    |25|
     # | HOUR8    |10|
     # | DAY      |5|
     # | WEEK     |1|
