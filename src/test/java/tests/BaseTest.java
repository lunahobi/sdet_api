package tests;

import helpers.BaseRequests;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class BaseTest {
    protected RequestSpecification requestSpecification;
    protected String id;

    @BeforeClass
    public void setUp(){
        requestSpecification = BaseRequests.initRequestSpecification();
        id = BaseRequests.createEntity(requestSpecification);
    }

    @AfterClass
    public void tearDown(){
        BaseRequests.deleteEntityById(id);
    }
}
