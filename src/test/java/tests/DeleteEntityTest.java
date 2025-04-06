package tests;

import helpers.BaseRequests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.requestSpecification;
import static org.hamcrest.Matchers.emptyString;

public class DeleteEntityTest {
    private String id;

    @BeforeClass
    public void setUp() {
        requestSpecification = BaseRequests.initRequestSpecification();
        id = BaseRequests.createEntity(requestSpecification);
    }

    @Test(description = "DELETE: Удаление сущности")
    public void testDeleteEntity() {
        given()
                .when()
                .delete("/api/delete/" + id)
                .then()
                .statusCode(204)
                .body(emptyString());
    }
}
