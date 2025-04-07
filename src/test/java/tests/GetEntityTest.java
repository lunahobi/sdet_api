package tests;

import io.restassured.module.jsv.JsonSchemaValidator;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pojo.Entity;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class GetEntityTest extends BaseTest{

    @Test(description = "GET: Получение сущности")
    public void testGetEntity(){
        Entity entity = given()
                .when()
                .get("/api/get/" + id)
                .then()
                .statusCode(200)
                .body(notNullValue())
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("jsonSchemas/GetEntityResponseSchema.json"))
                .extract().as(Entity.class);

        SoftAssert softAssertion = new SoftAssert();

        softAssertion.assertNotNull(entity.getId(), "ID должен быть не null");
        softAssertion.assertNotNull(entity.getTitle(), "Title должен быть не null");
        softAssertion.assertNotNull(entity.getAddition(), "Addition должен быть не null");
        softAssertion.assertEquals(entity.getId(), Integer.parseInt(id), "ID должен совпадать с запрошенным");

        if (entity.getAddition() != null) {
            softAssertion.assertNotNull(entity.getAddition().getId(), "Addition ID не должен быть null");
            softAssertion.assertTrue(
                    entity.getAddition().getAdditionalNumber() >= 0,
                    "AdditionalNumber должен быть положительным"
            );
        }

        softAssertion.assertFalse(
                entity.getImportantNumbers().isEmpty(),
                "ImportantNumbers не должен быть пустым"
        );
        entity.getImportantNumbers().forEach(num ->
                softAssertion.assertTrue(num > 0, "Числа должны быть положительными")
        );

        softAssertion.assertAll();
    }
}
