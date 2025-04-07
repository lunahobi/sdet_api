package tests;

import helpers.BaseRequests;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pojo.Entity;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.emptyString;

public class UpdateEntityTest extends BaseTest{

    @Test(description = "PATCH: Обновление сущности и её дополнений")
    public void testUpdateEntity() {
        Entity entity = Entity.builder().build();

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
        softAssertion.assertEquals(entityUpdated.getTitle(), entity.getTitle());
        softAssertion.assertEquals(entityUpdated.isVerified(), entity.isVerified());
        softAssertion.assertEquals(entityUpdated.getAddition().getId(), Integer.parseInt(id));
        softAssertion.assertEquals(entityUpdated.getAddition().getAdditionalInfo(), entity.getAddition().getAdditionalInfo());
        softAssertion.assertEquals(entityUpdated.getAddition().getAdditionalNumber(), entity.getAddition().getAdditionalNumber());
        softAssertion.assertEquals(entityUpdated.getImportantNumbers(), entity.getImportantNumbers());
        softAssertion.assertAll();
    }
}
