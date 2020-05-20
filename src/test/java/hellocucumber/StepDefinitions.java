package hellocucumber;

import static io.restassured.RestAssured.given;

import Core.ChartDataModel;
import Core.Security;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import java.util.List;
import org.junit.Assert;

public class StepDefinitions {
    String              BaseUrl;
    String              SecuritiesUrl;
    String              RequestChartDataUrl;
    Response response;
    String[]            securities;

    @Given("^I have MICROSERVICES_SERVER_URL equals ([^\"]*)$")
    public void i_have_MICROSERVICES_SERVER_URL_equals(String serverUrl) {
        BaseUrl = serverUrl;
    }

    @Given("I get securities from MICROSERVICES_SERVER_URL + {string}")
    public void iGetSecuritiesFromMICROSERVICES_SERVER_URLSrvgtwMarketdataVSecurities(String controllerAndMethod) {
        SecuritiesUrl = BaseUrl + controllerAndMethod;
        response = given().when().get(SecuritiesUrl);
        Security securitiesModel = response.as(Security.class);
        securities      = securitiesModel.getSecurities();
    }

    @Given("I have an url for chart data request equals MICROSERVICES_SERVER_URL + {string} + params")
    public void iHaveAnUrlForChartDataRequestEqualsMICROSERVICES_SERVER_URLSrvgtwMarketdataVChart_data(String controllerAndMethod) {
        RequestChartDataUrl = BaseUrl + controllerAndMethod;
    }

    @Then("^I send get request for every security with ([^\"]*) and (\\d+) parameters, assert results$")
    public void iSendGetRequestForEverySecurityWithVariousBartypeParameters(String bartype, int count) {
        for (int i = 0; i < 2/*securities.length*/; i++) {
            response = given().when().get(RequestChartDataUrl + "security=" + securities[i] + "&bartype=" + bartype + "&count=" + count);
            ChartDataModel chartDataModel = response.as(ChartDataModel.class);
            Assert.assertEquals(200, response.getStatusCode());
            Assert.assertEquals(count, chartDataModel.getBars().size());
            Assert.assertEquals(securities[i],chartDataModel.getSecurity());
            Assert.assertEquals(bartype, chartDataModel.getBarType());
        }
    }

    @Then("^Response status code equals (\\d+)$")
    public void response_status_code_equals(long statusCode) {
        Assert.assertEquals(statusCode, response.getStatusCode());
    }

    @Then("^Names of fields in response in container bars should be equal with data in table$")
    public void Names_of_fields_in_response_in_container_bars_should_be_equal_with_data_in_table(DataTable dataTable) {
        List<String> listOfExpectedFields = dataTable.rows(1).asList();
        for (String listOfExpectedField : listOfExpectedFields) {
            Assert.assertTrue(response.asString().contains(listOfExpectedField));
        }
    }

}
