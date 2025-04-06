package tests;

import helpers.BaseRequests;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pojo.Entity;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.emptyString;

public class UpdateEntityTest {
    private RequestSpecification requestSpecification;
    private static String id;

    @BeforeClass
    public void setUp() {
        requestSpecification = BaseRequests.initRequestSpecification();
        id = BaseRequests.createEntity(requestSpecification);
    }

    @Test(description = "PATCH: Обновление сущности и её дополнений")
    public void testUpdateEntity() {
        Entity entity =
                Entity.builder()
                        .title("Заголовок")
                        .verified(false)
                        .addition(Entity.Addition.builder()
                                .additionalInfo("Доп. сведения")
                                .additionalNumber(58).build())
                        .importantNumbers(List.of(54, 59, 124))
                        .build();

        given()
                .spec(requestSpecification)
                .body(entity)
                .when()
                .patch("/api/patch/"+ id)
                .then()
                .statusCode(204)
                .body(emptyString());

        Entity entityUpdated = BaseRequests.getEntityById(id);

        SoftAssert softAssertion = new SoftAssert();
        softAssertion.assertEquals(entityUpdated.getId(), Integer.parseInt(id));
        softAssertion.assertEquals(entityUpdated.getTitle(), "Заголовок");
        softAssertion.assertEquals(entityUpdated.isVerified(), false);
        softAssertion.assertEquals(entityUpdated.getAddition().getId(), Integer.parseInt(id));
        softAssertion.assertEquals(entityUpdated.getAddition().getAdditionalInfo(), "Доп. сведения");
        softAssertion.assertEquals(entityUpdated.getAddition().getAdditionalNumber(), 58);
        softAssertion.assertEquals(entityUpdated.getImportantNumbers(), List.of(54, 59, 124));
        softAssertion.assertAll();
    }

    @AfterClass
    public void tearDown() {
        BaseRequests.deleteEntityById(id);
    }
}
