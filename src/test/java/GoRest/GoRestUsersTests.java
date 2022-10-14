package GoRest;

import io.restassured.http.ContentType;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class GoRestUsersTests {

    @BeforeClass
    void Setup(){
        baseURI="https://gorest.co.in/public/v2/";
    }

    @Test(enabled = false)
    public void createUser()
    {
     int userID=
     given()
             .header("Authorization","Bearer 2761d8bc5fc1464c8354771dd6f474aea5dcec009ff1f60f3ddef9f15d098f9a")
             .contentType(ContentType.JSON)
             .body("{\"name\":\""+getRandomName()+ "\",\"gender\":\"male\", \"email\":\""+getRandomEmail()+"\",\"status\":\"active\"}")
             .when()
             .post("users")

             .then()
             .log().body()
             .statusCode(201)
             .contentType(ContentType.JSON)
             .extract().path("id")
             ;

        System.out.println("userID = " + userID);

    }

    public String getRandomName(){

        return RandomStringUtils.randomAlphabetic(8);
    }
    public String getRandomEmail(){

        return RandomStringUtils.randomAlphabetic(8).toLowerCase()+"@gmail.com";
    }

    @Test(enabled = false)
    public void createUserMap()
    {

        Map<String,String> newUser=new HashMap<>();
        newUser.put("name",getRandomName());
        newUser.put("gender","male");
        newUser.put("email",getRandomEmail());
        newUser.put("status","active");

        int userID=
                given()
                        .header("Authorization","Bearer 2761d8bc5fc1464c8354771dd6f474aea5dcec009ff1f60f3ddef9f15d098f9a")
                        .contentType(ContentType.JSON)
                        .body(newUser)
                        .when()
                        .post("users")

                        .then()
                        .log().body()
                        .statusCode(201)
                        .contentType(ContentType.JSON)
                        .extract().path("id")
                ;

        System.out.println("userID = " + userID);

    }
    class User{
        private String name;
        private String gender;
        private String email;
        private String status;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
    int userID=0;

    @Test
    public void createUserObject()
    {

        User newUser=new User();
        newUser.setName(getRandomName());
        newUser.setGender("male");
        newUser.setEmail(getRandomEmail());
        newUser.setStatus("active");


        userID=
                given()
                        .header("Authorization","Bearer 2761d8bc5fc1464c8354771dd6f474aea5dcec009ff1f60f3ddef9f15d098f9a")
                        .contentType(ContentType.JSON)
                        .body(newUser)
                        .when()
                        .post("users")

                        .then()
                        .log().body()
                        .statusCode(201)
                        .contentType(ContentType.JSON)
                        .extract().path("id")
                ;

        System.out.println("userID = " + userID);

    }

    @Test(dependsOnMethods = "createUserObject", priority = 1)
    public void updateUserObject()
    {

        Map<String,String> updateUser=new HashMap<>();
        updateUser.put("name","ismet tem");

                given()
                        .header("Authorization","Bearer 2761d8bc5fc1464c8354771dd6f474aea5dcec009ff1f60f3ddef9f15d098f9a")
                        .contentType(ContentType.JSON)
                        .body(updateUser)
                        .log().body()
                        .pathParam("userID",userID)
                        .when()
                        .put("users/{userID}")

                        .then()
                        .log().body()
                        .statusCode(200)
                        .contentType(ContentType.JSON)
                        .body("name",equalTo("ismet tem"))
                ;

        System.out.println("userID = " + userID);

    }

    @Test(dependsOnMethods = "createUserObject", priority = 2)
    public void getUserById()
    {

//        Map<String,String> updateUser=new HashMap<>();
//        updateUser.put("name","ismet tem");

        given()
                .header("Authorization","Bearer 2761d8bc5fc1464c8354771dd6f474aea5dcec009ff1f60f3ddef9f15d098f9a")
                .contentType(ContentType.JSON)
                .log().body()
                .pathParam("userID",userID)

                .when()
                .put("users/{userID}")

                .then()
                .log().body()
                .statusCode(200)
                .body("name",equalTo(userID))
        ;

        System.out.println("userID = " + userID);

    }


}
