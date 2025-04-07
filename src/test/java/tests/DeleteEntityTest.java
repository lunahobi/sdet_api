package tests;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.equalTo;

public class DeleteEntityTest extends BaseTest{

    @Test(description = "DELETE: Удаление сущности")
    public void testDeleteEntity() {
        given()
                .when()
                .delete("/api/delete/" + id)
                .then()
                .statusCode(204)
                .body(emptyString());

        given()
                .when()
                .get("/api/get/" + id)
                .then()
                .statusCode(500)
                .body("error", equalTo("no rows in result set"));
    }

    @AfterClass
    public void tearDown(){
    }
}
