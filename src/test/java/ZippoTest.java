import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class ZippoTest {

    @Test
    public void statusCodeTest(){

        given()


                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body()
                .statusCode(200)
                .contentType(ContentType.JSON)

        ;

    }

    @Test
    public void checkStateInRespondBody(){

        given()


                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body()
                .body("country",equalTo("United States"))
                .statusCode(200)

        ;

    }

    @Test
    public void bodyJsonPathtest2(){

        given()


                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body()
                .body("places[0].state",equalTo("California"))
                .statusCode(200)

        ;

    }

    @Test
    public void bodyJsonPathtest3(){

        given()


                .when()
                .get("http://api.zippopotam.us/tr/01000")

                .then()
                .log().body()
                .body("places.'place name'",hasItem("Çaputçu Köyü"))
                .statusCode(200)

        ;

    }

    @Test
    public void bodyArrayHasSizetest(){

        given()


                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body()
                .body("places",hasSize(1))
                .statusCode(200)

        ;

    }

    @Test
    public void combingTest(){

        given()


                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body()
                .body("places",hasSize(1))
                .body("places.'place name'",hasItem("Beverly Hills"))
                .statusCode(200)

        ;

    }
    @Test
    public void pathParamTest(){

        given()
                .pathParam("Country","us")
                .pathParam("ZipCode","90210")
                .log().uri()


                .when()
                .get("http://api.zippopotam.us/{Country}/{ZipCode}")

                .then()
                .log().body()

                .statusCode(200)

        ;

    }

    @Test
    public void pathParamTest2() {

        for (int i = 90210; i <90214; i++) {

            given()
                    .pathParam("Country", "us")
                    .pathParam("ZipCode", i)
                    .log().uri()


                    .when()
                    .get("http://api.zippopotam.us/{Country}/{ZipCode}")

                    .then()
                    .log().body()
                    .body("places",hasSize(1))
                    .statusCode(200)

            ;

        }
    }

}
