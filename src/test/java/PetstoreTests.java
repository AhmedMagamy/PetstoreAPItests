import static io.restassured.RestAssured.*;
import io.restassured.response.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utilites.ExcelUtility;

import java.awt.geom.RectangularShape;
import java.net.ResponseCache;
import java.util.Date;

@Listeners(utilites.ReportsGenerator.class)
public class PetstoreTests {

    ExcelUtility excel = new ExcelUtility("TestData.xlsx");


    @Test
    public void tc_1CreateUserAndAssertCreated(){
        
        //read user to create data from excel file
        String userName = excel.getCellDataString("CreateUser",1,0);
        String firstName =excel.getCellDataString("CreateUser",1,1);
        String lastName = excel.getCellDataString("CreateUser",1,2);
        String email = excel.getCellDataString("CreateUser",1,3);
        String password = excel.getCellDataString("CreateUser",1,4);
        String phone = excel.getCellDataString("CreateUser",1,5);
        String userStatus = excel.getCellDataString("CreateUser",1,6);

        //Create user
        Response createdRes = UserAPIs.createUser(userName,firstName,lastName,email,password,phone,userStatus);
        System.out.println(createdRes.body().asString());
        //check that the request is created successfully
        Assert.assertEquals(200,createdRes.getStatusCode());
        //get created user and verify it's created
        Response getUser = UserAPIs.getUser(userName);
        System.out.println(getUser.body().asString());
        //check that the request is created successfully
        Assert.assertEquals(200,getUser.getStatusCode());
        //check that we have a user with user name as created
        Assert.assertEquals(userName,getUser.jsonPath().getString("username"));
    }
    @Test
    public void tc_2getUserInTheSystem(){

        //Username of the user we want to get
        String userName = "ggo";
        //get the user
        Response getUser = UserAPIs.getUser(userName);
        System.out.println(getUser.body().asString());
        //check that the request is created successfully
        Assert.assertEquals(200,getUser.getStatusCode());
        //check that we have a user with username
        Assert.assertEquals(userName,getUser.jsonPath().getString("username"));
    }
    @Test
    public void tc_3loginWithValidData(){

        //Username and password of the user we want to use for login
        String userName = excel.getCellDataString("loginData",1,0);
        String password =excel.getCellDataString("loginData",1,1);

        //get the user
        Response loginRes = UserAPIs.login(userName,password);
        System.out.println(loginRes.body().asString());
        //check that the request is created successfully
        Assert.assertEquals(200,loginRes.getStatusCode());
        //check that we logged in successfully
        Assert.assertTrue(loginRes.jsonPath().getString("message").contains("logged in user"));
    }

    @Test
    public void tc_4LogoutAfterLogin(){

        //Username and password of the user we want to use for login
        String userName = excel.getCellDataString("loginData",1,0);
        String password =excel.getCellDataString("loginData",1,1);
        //get the user
        Response loginRes = UserAPIs.login(userName,password);
        System.out.println(loginRes.body().asString());
        //check that the request is created successfully
        Assert.assertEquals(200,loginRes.getStatusCode());
        //check that we logged in successfully
        Assert.assertTrue(loginRes.jsonPath().getString("message").contains("logged in user"));
        //do logout
        Response logoutRes=UserAPIs.logout();
        Assert.assertEquals(200,logoutRes.getStatusCode());
        System.out.println(logoutRes.body().asString());
        Assert.assertTrue(logoutRes.jsonPath().getString("message").contains("ok"));
    }

    @Test
    public void tc_5UpdateUserPhoneAndAssertUpdated(){
        String userName = excel.getCellDataString("UpadateUser",1,0);
        String firstName =excel.getCellDataString("UpadateUser",1,1);
        String lastName = excel.getCellDataString("UpadateUser",1,2);
        String email = excel.getCellDataString("UpadateUser",1,3);
        String password = excel.getCellDataString("UpadateUser",1,4);
        String phone = excel.getCellDataString("UpadateUser",1,5);
        String userStatus = excel.getCellDataString("UpadateUser",1,6);
        String id = excel.getCellDataString("UpadateUser",1,7);

        //Update user
        Response updatedRes = UserAPIs.updateCreatedUser(id,userName,firstName,lastName,email,password,phone,userStatus);
        System.out.println(updatedRes.body().asString());
        //check that the request is updated successfully
        Assert.assertEquals(200,updatedRes.getStatusCode());
        //get updated user and verify it's updated
        Response getUser = UserAPIs.getUser(userName);
        System.out.println(getUser.body().asString());
        //check that the request is created successfully
        Assert.assertEquals(200,getUser.getStatusCode());
        //check that the phone is updated
        Assert.assertEquals(phone,getUser.jsonPath().getString("phone"));
    }
    @Test
    public void tc_6DeleteCreatedUser(){
        //data of the user to create
        String userName = excel.getCellDataString("CreateUser",1,0);
        String firstName =excel.getCellDataString("CreateUser",1,1);
        String lastName = excel.getCellDataString("CreateUser",1,2);
        String email = excel.getCellDataString("CreateUser",1,3);
        String password = excel.getCellDataString("CreateUser",1,4);
        String phone = excel.getCellDataString("CreateUser",1,5);
        String userStatus = excel.getCellDataString("CreateUser",1,6);
        //Create user
        Response createdUser = UserAPIs.createUser(userName,firstName,lastName,email,password,phone,userStatus);
        System.out.println(createdUser.body().asString());
        //check that the request is created successfully
        Assert.assertEquals(200,createdUser.getStatusCode());
        Response deleteRes = UserAPIs.deleteCreatedUser(userName);
        Assert.assertEquals(200,deleteRes.getStatusCode());
        //get created user and verify it's created
        Response getUser = UserAPIs.getUser(userName);
        System.out.println(getUser.body().asString());
        //check that the user is no more exist
        Assert.assertEquals(404,getUser.getStatusCode());
    }






    @Test
    public void tc_7createTwoUsersAndVerifyCreated() {


        //Read the data of the first user from excel file
        String userName = excel.getCellDataString("CreateUsers",1,0);
        System.out.println(userName);
        String firstName =excel.getCellDataString("CreateUsers",1,1);
        String lastName = excel.getCellDataString("CreateUsers",1,2);
        String email = excel.getCellDataString("CreateUsers",1,3);
        String password = excel.getCellDataString("CreateUsers",1,4);
        String phone = excel.getCellDataString("CreateUsers",1,5);
        String userStatus = excel.getCellDataString("CreateUsers",1,6);

        //Read the data of the second user from excel file
        String userName2 = excel.getCellDataString("CreateUsers",2,0);
        String firstName2 =excel.getCellDataString("CreateUsers",2,1);
        String lastName2 = excel.getCellDataString("CreateUsers",2,2);
        String email2 = excel.getCellDataString("CreateUsers",2,3);
        String password2 = excel.getCellDataString("CreateUsers",2,4);
        String phone2 = excel.getCellDataString("CreateUsers",2,5);
        String userStatus2 = excel.getCellDataString("CreateUsers",2,6);

        //build first user
        JSONObject firstUser = new JSONObject();
        firstUser.put("userStatus",  userStatus);
        firstUser.put("phone",  phone);
        firstUser.put("password",  password);
        firstUser.put("email", email);
        firstUser.put("lastName", lastName);
        firstUser.put("firstName", firstName);
        firstUser.put("username", userName);


        //build second user
        JSONObject secondUser = new JSONObject();
        secondUser.put("username", userName2);
        secondUser.put("firstName", firstName2);
        secondUser.put("lastName", lastName2);
        secondUser.put("email", email2);
        secondUser.put("password",  password2);
        secondUser.put("phone",  phone2);
        secondUser.put("userStatus",  userStatus2);

        //put both users to array
        JSONArray array = new JSONArray();
        array.add(firstUser);
        array.add(secondUser);
        System.out.println(array.toString());

        //create 2 users
        Response postReq = UserAPIs.createListofUsers(array);
        System.out.println(postReq.asString());
        //get user 1
        Response user1Res = UserAPIs.getUser(userName);
        //get user 2
        Response user2Res = UserAPIs.getUser(userName2);
        System.out.println(user1Res.getBody().asString());
        System.out.println(user2Res.getBody().asString());
        //verify user1 and user2 are created
        Assert.assertEquals(userName, user1Res.jsonPath().getString("username"));
        Assert.assertEquals(userName2, user2Res.jsonPath().getString("username"));

    }









}
