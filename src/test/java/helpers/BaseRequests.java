package helpers;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.specification.RequestSpecification;
import pojo.Entity;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class BaseRequests {

    /**
     * Подготовка спецификации запроса.
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
     * @param id id сущности, которое необходимо удалить
     */
    public static void deleteEntityById(String id) {
        given()
                .when()
                .delete("/api/delete/" + id)
                .then()
                .statusCode(204);
    }

    /**
     * Получить сущность с заданным id
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
     * @param id id
     * @param entity
     */
    public static void updateEntityById(String id, Entity entity) {
        given()
                .when()
                .contentType("application/json")
                .body(entity)
                .patch("/api/patch/" + id)
                .then()
                .statusCode(204)
                .body(emptyString());
    }
}
