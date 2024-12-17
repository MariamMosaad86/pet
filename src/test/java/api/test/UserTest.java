package api.test;

import api.endPoints.UserEndPoints;
import api.payload.User;
import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class UserTest {

    Faker faker;
    User userPayload;

    public Logger logger;

    @BeforeClass
    public void setUp() {
        faker = new Faker();
        userPayload = new User();

        userPayload.setId(faker.idNumber().hashCode());
        userPayload.setUsername(faker.name().username());
        userPayload.setFirstName(faker.name().firstName());
        userPayload.setLastName(faker.name().lastName());
        userPayload.setEmail(faker.internet().safeEmailAddress());
        userPayload.setPassword(faker.internet().password(5, 10));
        userPayload.setPhone(faker.phoneNumber().cellPhone());

        // logs
        logger = LogManager.getLogger(this.getClass());
        logger.debug("debugging.....");

    }

    @Test(priority = 1)
    public void testPostUser() {
        logger.info("*************** Creating User **************");

        Response response = UserEndPoints.createUser(userPayload);
        response.then().log().all();

        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 200);
        logger.info("*************** User is created **************");

    }

    @Test(priority = 2)
    public void testGetUserByName() {
        logger.info("*************** Reading user info **************");

        Response response = UserEndPoints.readUser(this.userPayload.getUsername());
        response.then().log().all();

        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 200);

        logger.info("*************** User Info is displayed **************");

    }

    @Test(priority = 3)
    public void testUpdateUserByName() {

        logger.info("*************** Updating user **************");

        // Update data using payload
        userPayload.setFirstName(faker.name().firstName());
        userPayload.setLastName(faker.name().lastName());
        userPayload.setEmail(faker.internet().safeEmailAddress());

        Response response = UserEndPoints.updateUser(this.userPayload.getUsername(), userPayload);
        response.then().log().body();

        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 200);

        logger.info("*************** User is updated **************");

        // Check data after update
        Response responseAfterUpdate = UserEndPoints.readUser(this.userPayload.getUsername());
        responseAfterUpdate.then().log().body();

        int statusCode1 = responseAfterUpdate.getStatusCode();
        Assert.assertEquals(statusCode1, 200);
    }

    @Test(priority = 4)
    public void testDeleteUserByUserName() {
        logger.info("*************** Deleting user **************");

        Response response = UserEndPoints.deleteUser(this.userPayload.getUsername());
        response.then().log().all();

        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 200);

        logger.info("*************** User is deleted **************");

    }
}

