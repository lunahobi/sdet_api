package tests;

import helpers.BaseRequests;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pojo.Entity;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class GetAllEntitiesTest extends BaseTest{
    private final List<Integer> createdIds = new ArrayList<>();
    private final List<Entity> expectedEntities = new ArrayList<>();

    @BeforeClass
    public void setUp(){
        requestSpecification = BaseRequests.initRequestSpecification();
        for (int i = 0; i < 4;) {
            String id = BaseRequests.createEntity(requestSpecification);
            Entity entity = BaseRequests.getEntityById(id);
            createdIds.add(Integer.parseInt(id));
            expectedEntities.add(entity);
            i++;
        }
    }

    @Test(description = "GET: Получение всех сущностей")
    public void testGetAllEntities(){
        List<Entity> actualEntities = given()
                .when()
                .get("/api/getAll")
                .then()
                .statusCode(200)
                .body(notNullValue())
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("jsonSchemas/GetEntitiesResponseSchema.json"))
                .extract().jsonPath().getList("entity", Entity.class);

        SoftAssertions softAssertion = new SoftAssertions();

        softAssertion.assertThat(actualEntities.size())
                .as("Количество сущностей")
                .isGreaterThanOrEqualTo(expectedEntities.size());

        for (Integer id : createdIds) {
            Entity actualEntity = actualEntities.stream()
                    .filter(e -> e.getId() == id)
                    .findFirst()
                    .orElse(null);

            softAssertion.assertThat(actualEntity)
                    .as("Сущность с ID %d должна присутствовать", id)
                    .isNotNull();
        }

        actualEntities.forEach(entity -> {
            softAssertion.assertThat(entity.getId()).isPositive();
            softAssertion.assertThat(entity.getTitle()).isNotNull();
            softAssertion.assertThat(entity.getAddition()).isNotNull();
        });

        softAssertion.assertThat(actualEntities)
                .as("Проверка сортировки по id")
                .isSortedAccordingTo(Comparator.comparing(Entity::getId));

        List<Integer> ids = actualEntities.stream().map(Entity::getId).collect(Collectors.toList());
        softAssertion.assertThat(ids)
                .as("Проверка уникальности id")
                .doesNotHaveDuplicates();

        softAssertion.assertAll();
    }

    @AfterClass
    public void tearDown(){
        for (Integer id: createdIds) {
            BaseRequests.deleteEntityById(String.valueOf(id));
        }
    }
}
