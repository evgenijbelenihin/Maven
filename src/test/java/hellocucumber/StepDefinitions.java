package hellocucumber;

import static io.restassured.RestAssured.given;

import hellocucumber.Models.ChartDataModel;
import hellocucumber.Models.SecuritiesModel;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import java.util.Map;
import lombok.var;
import org.junit.Assert;

public class StepDefinitions {
    String              BaseUrl;
    String              SecuritiesUrl;
    String              RequestChartDataUrl;
    Response response;
    SecuritiesModel     securitiesModel;
    String[]            securities;
    ChartDataModel chartDataModel;

    @Given("I have MICROSERVICES_SERVER_URL equals {string}")
    public void i_have_MICROSERVICES_SERVER_URL_equals(String serverUrl) {
        BaseUrl = serverUrl;
    }

    @Given("I get securities from MICROSERVICES_SERVER_URL + {string}")
    public void iGetSecuritiesFromMICROSERVICES_SERVER_URLSrvgtwMarketdataVSecurities(String controllerAndMethod) {
        SecuritiesUrl = BaseUrl + controllerAndMethod;
        response = given().when().get(SecuritiesUrl);
        securitiesModel = response.as(SecuritiesModel.class);
        securities      = securitiesModel.getSecurities();
    }

    @Given("I have an url for chart data request equals MICROSERVICES_SERVER_URL + {string} + params")
    public void iHaveAnUrlForChartDataRequestEqualsMICROSERVICES_SERVER_URLSrvgtwMarketdataVChart_data(String controllerAndMethod) {
        RequestChartDataUrl = BaseUrl + controllerAndMethod;
    }

    @When("^I send get request for every security with ([^\"]*) and ([^\"]*) parameters$")
    public void iSendGetRequestForEverySecurityWithVariousBartypeParameters(String bartype, Integer count) {
        for (int i = 0; i < securities.length; i++) {
            response = given().when().get(RequestChartDataUrl + "security=" + securities[i] + "&bartype=" + bartype + "&count=" + count);
            chartDataModel = response.as(ChartDataModel.class);
            Assert.assertEquals(200, response.getStatusCode());
            Assert.assertEquals(count, (Integer)chartDataModel.getBars().length);
            Assert.assertEquals(securities[i],chartDataModel.getSecurity());
            Assert.assertEquals(bartype, chartDataModel.getBarType());
        }
    }

    @Then("Response status code equals {int}")
    public void response_status_code_equals(long statusCode) {
        Assert.assertEquals(statusCode, response.getStatusCode());
    }

    @Then("Names of fields in response in container bars should be equal with data in table")
    public void Names_of_fields_in_response_in_container_bars_should_be_equal_with_data_in_table(io.cucumber.datatable.DataTable dataTable) {
        chartDataModel = response.as(ChartDataModel.class);
        Map<String, Long>[] bars = chartDataModel.getBars();

        var listOfExpectedFields = dataTable.rows(1).asList();

        for (int i = 0; i < listOfExpectedFields.size(); i++){
            Assert.assertTrue(bars[0].containsKey(listOfExpectedFields.get(i)));
        }
    }

}
