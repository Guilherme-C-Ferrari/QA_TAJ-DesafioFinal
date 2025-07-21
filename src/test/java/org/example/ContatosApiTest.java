package org.example;

import java.util.List;
import org.junit.jupiter.api.*;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@DisplayName("Sequência de testes para operações CRUD da API de contatos")
public class ContatosApiTest {

    @BeforeAll
    static void initAll() {
        baseURI = "http://localhost:3000";
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @DisplayName("GET /contatos deve retornar 200 OK")
    @Test
    public void getContacts_shouldReturn200() {
        given()
        .when()
            .get("/contatos")
        .then()
            .statusCode(200);
    }

    @DisplayName("GET /contatos deve retornar um JsonArray")
    @Test
    public void getContacts_shouldReturnJsonArray_whenCalled() {
        given()
        .when()
            .get("/contatos")
        .then()
            .body("", isA(List.class));
    }

    @Test
    public void getContacts_shouldReturnEmptyList_whenNoContactsExist() {
        given()
        .when()
            .get("/contatos")
        .then()
            .statusCode(200)
            .body("", hasSize(0));
    }

    @Test
    public void postContact_shouldAddNewContactSuccessfully() {

    }

    @Test
    public void postContact_shouldReturn201_whenContactIsCreated() {

    }

    @Test
    public void postContact_shouldFail_when30ContactsExist() {

    }

    @Test
    public void deleteContacts_shouldClearAllContacts() {

    }

    @Test
    public void deleteContacts_shouldReturnEmptyListAfterDelete_whenGettingAllContacts() {

    }

    @Test
    public void getContact_shouldReturnCorrectContact_whenUsingCpf() {

    }

    @Test
    public void getContact_shouldReturn404_whenContactNotFound() {

    }
}
