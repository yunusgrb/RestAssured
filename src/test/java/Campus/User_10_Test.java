package Campus;

import Campus.Model.GradeLevel;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.BeforeClass;

import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.Map;

public class User_10_Test {

    Cookies cookies;

    @BeforeClass
    public void loginCampus(){

        baseURI="https://demo.mersys.io/";

        Map<String, String> credential= new HashMap<>();
        credential.put("username", "richfield.edu");
        credential.put("password", "Richfield2020!");
        credential.put("rememberMe", "true");

        cookies=
                given()
                        .contentType(ContentType.JSON)
                        .body(credential)

                        .when()
                        .post("auth/login")

                        .then()
                        .statusCode(200)
                        .extract().response().getDetailedCookies()
                ;



    }
    public String getRandomName() {
        return RandomStringUtils.randomAlphabetic(8).toLowerCase();
    }
    public String getRandomShortName() {
        return RandomStringUtils.randomAlphabetic(3).toLowerCase();
    }

    public String getRandomCode() {
        return RandomStringUtils.randomAlphabetic(3).toLowerCase();
    }

    String gradeLevelID;
    String gradeLevelName;
    String gradeLevelShortName;
    String gradeLevelOrder;

    @Test
    public void gradeLevelAdd(){

        gradeLevelName=getRandomName();
        gradeLevelShortName=getRandomShortName();

        GradeLevel gl = new GradeLevel();
        gl.setName(gradeLevelName);
        gl.setOrder("5");
        gl.setShortName(gradeLevelShortName);

        gradeLevelID=
                given()
                        .cookies(cookies)
                        .contentType(ContentType.JSON)
                        .body(gl)

                        .when()
                        .post("school-service/api/grade-levels")

                        .then()
                        .log().body()
                        .statusCode(201)
                        .extract().jsonPath().getString("id")
                ;
    }

    @Test(dependsOnMethods = "gradeLevelAdd")
    public void gradeLevelAddNegative(){

        GradeLevel gl =new GradeLevel();
        gl.setName(gradeLevelName);
        gl.setOrder("5");
        gl.setShortName(gradeLevelShortName);

                given()
                        .cookies(cookies)
                        .contentType(ContentType.JSON)
                        .body(gl)

                        .when()
                        .post("school-service/api/grade-levels")

                        .then()
                        .log().body()
                        .statusCode(400)
        ;

    }

    @Test(dependsOnMethods = "gradeLevelAdd")
    public void gradeLevelEdit(){

        gradeLevelName=getRandomName();
        GradeLevel gl =new GradeLevel();
        gl.setId(gradeLevelID);
        gl.setName(gradeLevelName);
        gl.setShortName(gradeLevelShortName);

        given()
                .cookies(cookies)
                .contentType(ContentType.JSON)
                .body(gl)

                .when()
                .put("school-service/api/grade-levels")

                .then()
                .log().body()
                .statusCode(200)
                .body("name",equalTo(gradeLevelName))
        ;

    }

    @Test(dependsOnMethods = "gradeLevelEdit")
    public void gradeLevelDelete()
    {

        given()
                .cookies(cookies)
                .contentType(ContentType.JSON)

                .when()
                .delete("school-service/api/grade-levels/"+gradeLevelID)

                .then()
                .log().body()
                .statusCode(200)
        ;

    }
    @Test(dependsOnMethods = "gradeLevelDelete")
    public void gradeLevelDeleteNegative()
    {

        given()
                .cookies(cookies)
                .contentType(ContentType.JSON)

                .when()
                .delete("school-service/api/grade-levels/"+gradeLevelID)

                .then()
                .log().body()
                .statusCode(400)
        ;

    }


    @Test(dependsOnMethods = "gradeLevelDelete")
    public void gradeLevelEditNegative()
    {
        gradeLevelName=getRandomName();
        GradeLevel gl =new GradeLevel();
        gl.setId(gradeLevelID);
        gl.setName(gradeLevelName);
        gl.setShortName(gradeLevelShortName);

        given()
                .cookies(cookies)
                .contentType(ContentType.JSON)
                .body(gl)

                .when()
                .put("school-service/api/grade-levels")

                .then()
                .log().body()
                .statusCode(400)
                .body("name",equalTo(gradeLevelName))
        ;

    }

}
