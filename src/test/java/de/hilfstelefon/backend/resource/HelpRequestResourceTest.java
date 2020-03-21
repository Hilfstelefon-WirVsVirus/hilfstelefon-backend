package de.hilfstelefon.backend.resource;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;

@QuarkusTest
public class HelpRequestResourceTest {

    @Test
    public void testGetHelpRequestEndpoint() {
        given()
          .when().get("/help-request")
          .then()
             .statusCode(200);
    }
}