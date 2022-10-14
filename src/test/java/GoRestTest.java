import POJO.Location;
import POJO.Place;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class GoRestTest {

    @Test
    public void pathParamTest(){

        given()
                .param("page",1)
                .log().uri()


                .when()
                .get("http://gorest.co.in/public/v1/users")

                .then()
                .log().body()
                .body("meta.pagination.page",equalTo(1))

                .statusCode(200)

        ;

    }
    @Test
    public void pathParamTest2() {
        for (int i = 1; i <11; i++) {

            given()
                    .param("page", i)
                    .log().uri() //request linki


                    .when()
                    .get("http://gorest.co.in/public/v1/users")

                    .then()
                    .log().body()
                    .body("meta.pagination.page", equalTo(i))

                    .statusCode(200)
            ;
        }
    }

    RequestSpecification requestSpecs;
    ResponseSpecification responseSpecs;

    @BeforeClass
    void setup(){

        String baseURI = "http://gorest.co.in/public/v1";
        requestSpecs=new RequestSpecBuilder()
            .log(LogDetail.URI)
                .setAccept(ContentType.JSON)
                .build();


        responseSpecs= new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .log(LogDetail.BODY)
                .build();
        ;

    }

    @Test
    public void specsTest(){

        given()
                .param("page",1)
                .spec(requestSpecs)


                .when()
                .get("/users")

                .then()
                .body("meta.pagination.page",equalTo(1))
                .body("meta.pagination.page",equalTo(1))
                .spec(responseSpecs)
        ;
    }
    @Test
    public void extractingJsonPathTest(){
        String placeName=
        given()

                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body()
                .statusCode(200)
                .extract().path("places[0].'place name'");

        System.out.println("placeName = " + placeName);

    }

    @Test
    public void extractingJsonPathInt(){
        int limit=
                given()

                        .when()
                        .get("https://gorest.co.in/public/v1/users")

                        .then()
                        .log().body()
                        .statusCode(200)
                        .extract().path("mega.pagination.limit")
                ;

        System.out.println("limit = " + limit);
      //  Assert.assertEquals(limit,10,"test sonucu");

    }

    @Test
    public void extractingJsonPathInt2(){
        int id=
                given()

                        .when()
                        .get("https://gorest.co.in/public/v1/users")

                        .then()
                        //.log().body()
                        .statusCode(200)
                        .extract().path("data[2].id")
                ;

        System.out.println("id = " + id);
    }
    @Test
    public void extractingJsonPathList(){
        List<Integer> ids=
                given()

                        .when()
                        .get("https://gorest.co.in/public/v1/users")

                        .then()
                        //.log().body()
                        .statusCode(200)
                        .extract().path("data.id")
                ;

        System.out.println("ids = " + ids);

        Assert.assertTrue(ids.contains(3040));


    }
    @Test
    public void extractingJsonPathList3(){
        List<String> names=
                given()

                        .when()
                        .get("https://gorest.co.in/public/v1/users")

                        .then()
                        //.log().body()
                        .statusCode(200)
                        .extract().path("data.name")
                ;

        System.out.println("names = " + names);

        Assert.assertTrue(names.contains("Gurdev Sharma"));


    }
    @Test
    public void extractingJsonPathList4(){
        Response response=
                given()

                        .when()
                        .get("https://gorest.co.in/public/v1/users")

                        .then()
                        //.log().body()
                        .statusCode(200)
                        .extract().response()
                ;

        List<Integer> ids= response.path("data.id");
        List<String> names= response.path("data.name");
        int limit= response.path("meta.pagination.limit");

        System.out.println("ids = " + ids);
        System.out.println("names = " + names);
        System.out.println("limit = " + limit);

    }

//    @Test
//    public void checkStateInRespondBody(){
//        Location place=
//                given()
//                .when()
//                .get("http://api.zippopotam.us/us/90210")
//
//                .then()
//                .extract().as(Location.class)
//        ;
//        System.out.println("place = " + place);
//        System.out.println(place.getCountry());
//        System.out.println(place.getPlace.get(0).getPlacename);
//    }


}
