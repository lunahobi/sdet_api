package tests;

import io.restassured.module.jsv.JsonSchemaValidator;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pojo.Entity;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class GetEntityTest {

    @Test(description = "GET: Получение сущности")
    public void testGetEntity(){
        Entity entity = given()
                .when()
                .get("/api/get/1")
                .then()
                .statusCode(200)
                .body(notNullValue())
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("jsonSchemas/GetEntityResponseSchema.json"))
                .extract().as(Entity.class);

        SoftAssert softAssertion = new SoftAssert();
        softAssertion.assertEquals(entity.getId(), 1);
        softAssertion.assertEquals(entity.getTitle(), "Новая сущность");
        softAssertion.assertEquals(entity.isVerified(), true);
        softAssertion.assertEquals(entity.getAddition().getId(), 1);
        softAssertion.assertEquals(entity.getAddition().getAdditionalInfo(), "Дополнительные сведения");
        softAssertion.assertEquals(entity.getAddition().getAdditionalNumber(), 123);
        softAssertion.assertEquals(entity.getImportantNumbers(), List.of(42, 87, 15));
        softAssertion.assertAll();
    }

    @Test(description = "GET: Получение всех сущностей")
    public void testGetAllEntities(){
        List<Entity> entities = given()
                .when()
                .get("/api/getAll")
                .then()
                .statusCode(200)
                .body(notNullValue())
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("jsonSchemas/GetEntitiesResponseSchema.json"))
                .extract().jsonPath().getList("entity", Entity.class);

        SoftAssertions softAssertion = new SoftAssertions();

        entities.forEach(entity -> {
            softAssertion.assertThat(entity.getId())
                    .as("ID сущности %d должен быть положительным", entity.getId())
                    .isPositive();

            softAssertion.assertThat(entity.getTitle())
                    .as("Title сущности %d не должен быть пустым", entity.getId())
                    .isNotNull();

            softAssertion.assertThat(entity.getAddition().getId())
                    .as("ID addition для сущности %d должен быть положительным", entity.getId())
                    .isPositive();
        });

        softAssertion.assertThat(entities)
                .as("Проверка сортировки по id")
                .isSortedAccordingTo(Comparator.comparing(Entity::getId));

        List<Integer> ids = entities.stream().map(Entity::getId).collect(Collectors.toList());
        softAssertion.assertThat(ids)
                .as("Проверка уникальности id")
                .doesNotHaveDuplicates();

        softAssertion.assertAll();
    }
}
