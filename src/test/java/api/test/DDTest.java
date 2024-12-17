package api.test;

import api.payload.User;
import api.endPoints.UserEndPoints;
import api.utilities.DataProviders;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DDTest {
    @Test(priority = 1, dataProvider = "Data", dataProviderClass = DataProviders.class)
    public void testPostUsers(String userID, String userName, String firstName, String lastName, String userEmail, String password, String phone) {

        User userPayLoad = new User();

        userPayLoad.setId(Integer.parseInt(userID)); // to convert id to number
        userPayLoad.setUsername(userName);
        userPayLoad.setFirstName(firstName);
        userPayLoad.setLastName(lastName);
        userPayLoad.setEmail(userEmail);
        userPayLoad.setPassword(password);
        userPayLoad.setPhone(phone);

        Response response = api.endPoints.UserEndPoints.createUser(userPayLoad);
        response.then().log().body();

        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 200);

    }
    @Test(priority = 2, dataProvider = "UserNames", dataProviderClass = DataProviders.class)
    public void testGetUsersByName(String userName){
        Response response = UserEndPoints.readUser(userName);
        response.then().log().body();

        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 200);
    }

    @Test(priority = 3, dataProvider = "UserNames", dataProviderClass = DataProviders.class)
    public void testDeleteUsersByName(String useName){

        Response response = UserEndPoints.deleteUser(useName);
        response.then().log().body();

        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 200);
    }
}
