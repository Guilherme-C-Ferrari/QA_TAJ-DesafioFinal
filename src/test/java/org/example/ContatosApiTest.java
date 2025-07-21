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
        Contato c = new Contato("João Silva", "(11) 91234-5678", "joao.silva@example.com", "12345678901");

        given()
            .contentType("application/json")
            .body(c)
        .when()
            .post("/contatos")
        .then()
            .body("nome", equalTo("João Silva"))
            .body("email", equalTo("joao.silva@example.com"))
            .body("cpf", equalTo("12345678901"));
    }

    @Test
    public void postContact_shouldReturn201_whenContactIsCreated() {
        Contato c = new Contato("Carlos Silva", "(11) 91234-5678", "carlos.silva@example.com", "0987654321");

        given()
            .contentType("application/json")
            .body(c)
        .when()
            .post("/contatos")
        .then()
            .statusCode(201);
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
