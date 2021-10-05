import static io.restassured.RestAssured.*;
import io.restassured.response.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class UserAPIs {

    public static Response createUser(String userName ,String firstName,String lastName ,String email ,String password ,String phone ,String userStatus){
        //build request body
        JSONObject requestParams = new JSONObject();
        requestParams.put("username", userName);
        requestParams.put("firstName", firstName);
        requestParams.put("lastName", lastName);
        requestParams.put("email", email);
        requestParams.put("password", password);
        requestParams.put("phone",  phone);
        requestParams.put("userStatus",  userStatus);
        JSONArray array = new JSONArray();
        array.add(requestParams);



        //Send the request and get the response
        Response postRes = given()
                .header("Content-type", "application/json")
                .body(requestParams.toString())
                .when()
                .post("https://petstore.swagger.io/v2/user")
                .then()
                .extract().response();

        return postRes;
    }
    public static Response getUser(String userName){
        Response res = get("https://petstore.swagger.io/v2/user/"+userName);
        return res;
    }


    public static Response login(String userName  ,String password) {


        Response loginRes = given()
                .contentType("application/json")
                .param("username", userName)
                .param("password", password)
                .when()
                .get("https://petstore.swagger.io/v2/user/login")
                .then().extract().response();


        return loginRes;
    }

    public static Response logout() {

        Response logoutRes = given()
                .contentType("application/json")
                .when()
                .get("https://petstore.swagger.io/v2/user/logout")
                .then().extract().response();

        return logoutRes;

    }

    //in this method we do our tests
    @Test
    public static Response updateCreatedUser(String id ,String userName ,String firstName,String lastName ,String email ,String password ,String phone ,String userStatus) {

        JSONObject requestParams = new JSONObject();
        requestParams.put("id", id);
        requestParams.put("username", userName);
        requestParams.put("firstName", firstName);
        requestParams.put("lastName", lastName);
        requestParams.put("email", email);
        requestParams.put("password", password);
        requestParams.put("phone",  phone);
        requestParams.put("userStatus",  userStatus);
        JSONArray array = new JSONArray();
        array.add(requestParams);



        Response putRes = given()
                .header("Content-type", "application/json")
                .body(requestParams.toString())
                .when()
                .put("https://petstore.swagger.io/v2/user/"+userName)
                .then()
                .extract().response();
        return putRes;
    }

    public static Response deleteCreatedUser(String userName) {

        Response deleteRes = given()
                .header("Content-type", "application/json")
                .when()
                .delete("https://petstore.swagger.io/v2/user/"+userName)
                .then()
                .extract().response();

        return deleteRes;
    }

    //in this method we do our tests
    @Test
    public static Response createListofUsers(JSONArray arrayOfUsers) {

        Response postRes = given()
                .header("Content-type", "application/json")
                .body(arrayOfUsers.toString())
                .when()
                .post("https://petstore.swagger.io/v2/user/createWithList")
                .then()
                .extract().response();

     return postRes;



    }






}
