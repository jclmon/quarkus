package org.acme.quickstart;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import org.junit.jupiter.api.Test;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
@QuarkusTestResource(GreetingsQuarkusTestResourceLifecycleManager.class)
public class GreetingResourceTest {

    @Test
    public void testHelloEndpoint() {
        given()
          .when()
          .get("/hello")
          .then()
             .statusCode(200)
             .body(is("MEMORY HELLO"));
    }

}