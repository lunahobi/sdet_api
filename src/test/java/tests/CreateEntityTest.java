package tests;

import helpers.BaseRequests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pojo.Entity;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class CreateEntityTest extends BaseTest{

    @BeforeClass
    public void setUp(){
        requestSpecification = BaseRequests.initRequestSpecification();
    }

    @Test(description = "POST: Создание сущности")
    public void testCreateEntity(){
        Entity entity = Entity.builder().build();

        id = given()
                .spec(requestSpecification)
                .body(entity)
                .when()
                .post("/api/create")
                .then()
                .statusCode(200)
                .body(notNullValue())
                .extract().body().asPrettyString();

        Entity newEntity = BaseRequests.getEntityById(id);
        SoftAssert softAssertion = new SoftAssert();
        softAssertion.assertEquals(newEntity.getTitle(), entity.getTitle());
        softAssertion.assertEquals(newEntity.isVerified(), entity.isVerified());
        softAssertion.assertEquals(newEntity.getAddition().getAdditionalInfo(), entity.getAddition().getAdditionalInfo());
        softAssertion.assertEquals(newEntity.getAddition().getAdditionalNumber(), entity.getAddition().getAdditionalNumber());
        softAssertion.assertEquals(newEntity.getImportantNumbers(), entity.getImportantNumbers());
        softAssertion.assertAll();
    }
}
