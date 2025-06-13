package tests;

import config.ConfigReader;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

import pojo.UserRequest;
import pojo.UserResponse;
import utils.ExtentReportManager;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

public class UserCrudTests {

    ExtentReports report;
    ExtentTest test;
    String userId;

    @BeforeSuite
    public void setupReport() {
        report = ExtentReportManager.getInstance();
    }

    @AfterSuite
    public void flushReport() {
        report.flush();
    }

    @Test(priority = 1)
    public void createUser() {
        test = report.createTest("Create User API Test");

        UserRequest request = new UserRequest("sunil", "QA");

        Response res = RestAssured.given()
                .baseUri(ConfigReader.getBaseUri())
                .contentType(ContentType.JSON)
                .body(request)
                .post("/users");

        test.info("Status: " + res.getStatusCode());
        Assert.assertEquals(res.getStatusCode(), 201, "Status code mismatch");

        UserResponse response = res.as(UserResponse.class);
        userId = response.getId();
        Assert.assertNotNull(userId, "User ID is null");
        test.pass("User created successfully with ID: " + userId);
    }

    @Test(priority = 2, dependsOnMethods = "createUser")
    public void deleteUser() {
        test = report.createTest("Delete User API Test");

        Response res = RestAssured.given()
                .baseUri(ConfigReader.getBaseUri())
                .delete("/users/" + userId);

        Assert.assertEquals(res.getStatusCode(), 204, "Status code mismatch for delete");
        test.pass("User deleted successfully with ID: " + userId);
    }

    @AfterMethod
    public void logStatus(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            test.fail(result.getThrowable());
        } else if (result.getStatus() == ITestResult.SKIP) {
            test.skip("Test skipped: " + result.getThrowable());
        }
    }
}
