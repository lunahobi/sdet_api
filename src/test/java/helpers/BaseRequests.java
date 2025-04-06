package helpers;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

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
}
