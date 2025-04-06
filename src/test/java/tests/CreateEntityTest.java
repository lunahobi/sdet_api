package tests;

import helpers.BaseRequests;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pojo.Entity;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class CreateEntityTest {
    private RequestSpecification requestSpecification;
    private String id;

    @BeforeClass
    public void setUp(){
        requestSpecification = BaseRequests.initRequestSpecification();
    }

    @Test(description = "POST: Создание сущности")
    public void testCreateEntity(){
        Entity entity = Entity.builder().build();

        id = given()
                .spec(requestSpecification)
                .contentType(ContentType.JSON)
                .body(entity)
                .when()
                .post("/api/create")
                .then()
                .statusCode(200)
                .body(notNullValue())
                .extract().body().asPrettyString();
    }

    @AfterClass
    public void tearDown(){
        BaseRequests.deleteEntityById(id);
    }
}
