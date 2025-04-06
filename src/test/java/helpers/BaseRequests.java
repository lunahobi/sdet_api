package helpers;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import pojo.Entity;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class BaseRequests {

    /**
     * Подготовка спецификации запроса.
     *
     * @return спецификация
     */
    public static RequestSpecification initRequestSpecification() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder
                .setContentType(ContentType.JSON)
                .setBaseUri(PropertyProvider.getInstance().getProperty("apiUrl"))
                .setAccept(ContentType.JSON);
        return builder.build();
    }

    /**
     * Удаление сущности с заданным id
     *
     * @param id id сущности, которое необходимо удалить
     */
    public static void deleteEntityById(String id) {
        given()
                .when()
                .delete("/api/delete/" + id)
                .then()
                .statusCode(204)
                .body(emptyString());;
    }

    /**
     * Получить сущность с заданным id
     *
     * @param id id сущности, которое необходимо получить
     * @return сущность
     */
    public static Entity getEntityById(String id) {
        return given()
                .when()
                .get("/api/get/" + id)
                .then()
                .statusCode(200)
                .body(notNullValue())
                .extract().as(Entity.class);
    }

    /**
     * Обновить сущность с заданным id
     *
     * @param entity сущность, которую необходимо обновить
     */
    public static void updateEntityById(Entity entity, RequestSpecification requestSpecification) {
        given()
                .spec(requestSpecification)
                .body(entity)
                .when()
                .patch("/api/patch/" + entity.getId())
                .then()
                .statusCode(204)
                .body(emptyString());
    }

    public static String createEntity(RequestSpecification requestSpecification){
        Entity entity = Entity.builder().build();
        return given()
                .spec(requestSpecification)
                .body(entity)
                .when()
                .post("/api/create")
                .then()
                .statusCode(200)
                .body(notNullValue())
                .extract().body().asPrettyString();
    }
}
