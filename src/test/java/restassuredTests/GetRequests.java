package restassuredTests;
import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.BeforeClass;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.util.ArrayList;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
/*
 To get the top two cities based on the weather over the next 3 months.

 */
public class GetRequests {
    public static RequestSpecification requestSpecification;
    public static ResponseSpecBuilder responseSpecBuilder;
    int day = 16;

    //Data Parameterized for 5 cities along with their Latitudes,Longitudes,postcodes,API Key
    @DataProvider
    public Object [][] DataForGetRequest(){
        return  new Object[][] {
                {"51.5085","-0.1257","2643743","PO167GZ","TW1W0NY","FWERWR","KILDFGDG","ERTETR","0e1c9f3a70754efda00109582aca9114"},
                {"41.5085","-4.1257","7643743","KO767GZ","EW1W0NY","SDFCSDF","WEDDWRI","ASDAD","0e1c9f3a70754efda00109582aca9114"},
                {"31.5085","-6.1257","8643743","DO167GZ","JW1W0NY","SDFCSDF","WEDDWRI","ERRADFA","0e1c9f3a70754efda00109582aca9114"},
                {"61.5085","-2.1257","4643743","GO167GZ","KW1W0NY","SDFCSDF","WEDDWRE","ASDDAD","0e1c9f3a70754efda00109582aca9114"},
                {"61.5085","-6.1257","9643743","YO127GZ","TW1W0NY","SDFCSDF","WEDDWRE","SADAD","0e1c9f3a70754efda00109582aca9114"},
        };
    }
    //Initialization of Base URL
    @BeforeClass
    public static void createRequestSpecification() {
        RestAssured.baseURI="https://api.weatherbit.io/v2.0/forecast/daily?/";
    }


    //Get the results based on the data past and assert the reponses
    @Test(dataProvider = "DataForGetRequest")
    public void getWeatherDetails(String Latitude,String Longtitude,String City_id,String PostalCode1,String PostalCode2,String PostalCode3,String PostalCode4,String PostalCode5,String APIKey) throws InterruptedException {
                getResponseBody(Latitude, Longtitude, City_id, PostalCode1, PostalCode2, PostalCode3, PostalCode4, PostalCode5, APIKey);
                getResponseStatus(Latitude, Longtitude, City_id, PostalCode1, PostalCode2, PostalCode3, PostalCode4, PostalCode5, APIKey);
    }

    //This will fetch the response body as is and log it. given and when are optional here
    @Test(dataProvider = "DataForGetRequest")
    public  void getResponseBody(String Latitude,String Longtitude,String City_id,String PostalCode1,String PostalCode2,String PostalCode3,String PostalCode4,String PostalCode5,String APIKey){
         getQueryParam( Latitude, Longtitude, City_id, PostalCode1, PostalCode2, PostalCode3, PostalCode4, PostalCode5, APIKey);
         given().when().get(RestAssured.baseURI).then().log().body();
    }

    @Test(dataProvider = "DataForGetRequest")
    public  void getResponseStatus(String Latitude,String Longtitude,String City_id,String PostalCode1,String PostalCode2,String PostalCode3,String PostalCode4,String PostalCode5,String APIKey){
         getQueryParam( Latitude, Longtitude, City_id, PostalCode1, PostalCode2, PostalCode3, PostalCode4, PostalCode5, APIKey);
         given().when().get(RestAssured.baseURI).getStatusCode();
                 given().when().get(RestAssured.baseURI).then().assertThat().statusCode(200).contentType(ContentType.JSON).extract().response();
    }

    @Test(dataProvider = "DataForGetRequest")
    public  void checkTempWindSpeedResponseBody(String Latitude,String Longtitude,String City_id,String PostalCode1,String PostalCode2,String PostalCode3,String PostalCode4,String PostalCode5,String APIKey){
        int largest1 = 0;
        int largest2 = 0;
        int temp;
        getQueryParam( Latitude, Longtitude, City_id, PostalCode1, PostalCode2, PostalCode3, PostalCode4, PostalCode5, APIKey);
                given()
                .when().get(RestAssured.baseURI)
                .then()
                .body("data[*].temp", greaterThan(12)).body("data[*].temp", lessThan(30))
                .body("data[*].wind_gust_spd", greaterThanOrEqualTo(3)).body("data[*].wind_gust_spd",lessThanOrEqualTo(9))
                .body("data[*].uv", lessThanOrEqualTo(12)).extract().response();
        ArrayList<String> data = when().get(RestAssured.baseURI).then().extract().path("data");
        for(int i =0; i< data.size();i ++) {
            largest1 = Integer.parseInt(data.get(0));
            largest2 = Integer.parseInt(data.get(1));
        }
        if (largest1 < largest2)
        {
            temp = largest1;
            largest1 = largest2;
            largest2 = temp;
        }
        for (int i = 2; i < data.size(); i++)
        {
            if (Integer.parseInt(data.get(i)) > largest1)
            {
                largest2 = largest1;
                largest1 = Integer.parseInt(data.get(i));
            }
            else if (Integer.parseInt(data.get(i)) > largest2 && Integer.parseInt(data.get(i)) != largest1)
            {
                largest2 = Integer.parseInt(data.get(i));
            }
        }
        System.out.println ("The First largest is " + largest1);
        System.out.println ("The Second largest is "+ largest2);
    }

    public void getQueryParam(String Latitude,String Longtitude,String City_id,String PostalCode1,String PostalCode2,String PostalCode3,String PostalCode4,String PostalCode5,String APIKey) {
         given().queryParam("lat", Latitude)
                .queryParam("lon", Longtitude)
                .queryParam("days", day)
                .queryParam("city_id", City_id)
                .queryParam("postal_code", PostalCode1)
                .queryParam("postal_code", PostalCode2)
                .queryParam("postal_code", PostalCode3)
                .queryParam("postal_code", PostalCode4)
                .queryParam("postal_code", PostalCode5)
                .queryParam("key", APIKey)
                .when().get(RestAssured.baseURI).then().log().body().extract().response();
    }
}