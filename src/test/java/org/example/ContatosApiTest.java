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

    @BeforeEach
    private void clearBeforeEachTest() {
        clearContacts();
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

    @DisplayName("GET /contatos deve retornar uma lista vazia se nenhum contato existir")
    @Test
    public void getContacts_shouldReturnEmptyList_whenNoContactsExist() {
        given()
            .when()
                .get("/contatos")
            .then()
                .statusCode(200)
                .body("", hasSize(0));
    }

    @DisplayName("POST /contatos deve adicionar um contato novo com sucesso")
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

    @DisplayName("POST /contatos retornar 201 Created")
    @Test
    public void postContact_shouldReturn201_whenContactIsCreated() {
        Contato c = new Contato("Carlos Silva", "(11) 91234-5678", "carlos.silva@example.com", "09876543210");

        given()
                .contentType("application/json")
                .body(c)
            .when()
                .post("/contatos")
            .then()
                .statusCode(201);
    }

    @DisplayName("POST /contatos deve falhar caso já hajam 30 contatos e retornar 400 Bad Request")
    @Test
    public void postContact_shouldFail_when30ContactsExist() {
        for (int i = 1; i <= 30; i++) {
            Contato c = new Contato(
                    "C" + i,
                    "(11) 9000-000" + i,
                    "C" + i + "@example.com",
                    String.format("%011d", 10000000000L + i)
            );

            given()
                    .contentType("application/json")
                    .body(c)
                .when()
                    .post("/contatos")
                .then()
                    .statusCode(201);
        }

        Contato c = new Contato(
                "C31",
                "(11) 9000-0031",
                "C31@example.com",
                String.format("%011d", 10000000000L + 31)
        );

        given()
                .contentType("application/json")
                .body(c)
            .when()
                .post("/contatos")
            .then()
                .statusCode(400);
    }

    @DisplayName("DELETE /contatos deve limpar todos os contatos fazendo com que GET /contatos retorne uma lista vazia")
    @Test
    public void deleteContacts_shouldClearAllContacts() {
        given()
            .when()
                .delete("/contatos")
            .then()
                .statusCode(204);

        given()
            .when()
                .get("/contatos")
            .then()
                .statusCode(200)
                .body("", hasSize(0));
    }

    @DisplayName("GET /contato/:cpf deve retornar contato específico por CPF")
    @Test
    public void getContact_shouldReturnCorrectContact_whenUsingCpf() {
        Contato c = new Contato("João Silva", "(11) 91234-5678", "joao.silva@example.com", "12345678901");

        given()
                .contentType("application/json")
                .body(c)
            .when()
                .post("/contatos")
            .then()
                .statusCode(201);

        given()
                .pathParam("cpf", "12345678901")
            .when()
                .get("/contatos/{cpf}")
            .then()
                .body("cpf", equalTo("12345678901"));
    }

    @DisplayName("GET /contato/:cpf deve retornar 404 Not Found quando o contato não for encontrado")
    @Test
    public void getContact_shouldReturn404_whenContactNotFound() {
        given()
                .pathParam("cpf", "00000000000")
            .when()
                .get("/contatos/{cpf}")
            .then()
                .statusCode(404);
    }

    private static void clearContacts() {
        given()
            .when()
                .delete("/contatos")
            .then()
                .statusCode(204);
    }
}