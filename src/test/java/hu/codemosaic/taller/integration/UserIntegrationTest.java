package hu.codemosaic.taller.integration;

import hu.codemosaic.taller.dto.UserDto;
import hu.codemosaic.taller.entity.AppUserEntity;
import hu.codemosaic.taller.repository.AppUserRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

/**
 * Integration tests for the User REST API using RestAssured.
 * NOTE: This requires a running Spring Boot application and
 * will attempt to interact with the actual controller logic and (mocked) dependencies.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {
                "jwt.secret=a-secure-placeholder-secret-key-for-testing-only-1234567890"
        })
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private AppUserRepository userRepository;

    private static final String API_BASE_URL = "/api/v1";

    @BeforeEach
    public void setUp() {
        // Set the base URI and port for all RestAssured calls
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @BeforeEach
    void logTestStart(TestInfo testInfo) {
        System.out.println("Running test: " + testInfo.getDisplayName());
    }

    /**
     * Test case for creating a new user via the POST /user endpoint.
     * Assumes a successful creation returns 201 Created and the user object.
     */
    @Test
    void testCreateUser_success() {
        // Arrange
        UserDto newUser = UserDto.builder().username("integration_test_user").build();

        // Act & Assert
        given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(newUser)
                .when()
                .post(API_BASE_URL + "/user")
                .then()
                .statusCode(201) // Assuming 201 Created on success
                .body("username", equalTo(newUser.getUsername()));
    }

    /**
     * Test case for retrieving all users via the GET /user endpoint.
     * Assumes a successful retrieval returns 200 OK and a list.
     * Note: This test would usually require mocking the UserService/Repository
     * or setting up known data via a Test DB configuration. Here we just check the structure.
     */
    @Test
    void testGetAllUsers_success() {
        // Act & Assert
        given()
                .contentType(ContentType.JSON)
                .when()
                .get(API_BASE_URL + "/user")
                .then()
                .statusCode(200)
                .body("$", isA(List.class)); // Check if the response body is a JSON array
    }

    /**
     * Test case for a successful login via the POST /auth/login endpoint.
     * Assumes the endpoint returns a JWT token (a non-null string).
     * Since the UserService has a TODO for password check, this test relies on
     * the user existing in the mocked/test database.
     */
    @Test
    void testLogin_success() {
        // Arrange
        String username = "existing_user_for_login";
        // Assuming a prior setup (e.g., in a test database fixture) to ensure 'existing_user_for_login' exists.
        // For simplicity, we use a basic map/object structure for the login request body.
        String loginRequestBody = String.format("{\"username\": \"%s\", \"password\": \"testpass\"}", username);
        AppUserEntity user = new AppUserEntity();
        user.setUsername("existing_user_for_login");
        user.setPassword("testpass"); // hash if needed
        userRepository.save(user);

        // Act & Assert
        given()
                .contentType(ContentType.JSON)
                .body(loginRequestBody)
                .when()
                .post(API_BASE_URL + "/user/login") // Assuming a login endpoint exists
                .then()
                .statusCode(200)
                .body(notNullValue()); // Check that the response body (the token) is not null
        userRepository.delete(user);
    }

    /**
     * Test case for failed login (user not found).
     */
    @Test
    void testLogin_userNotFound() {
        // Arrange
        String loginRequestBody = "{\"username\": \"unknown_user\", \"password\": \"testpass\"}";

        // Act & Assert
        given()
                .contentType(ContentType.JSON)
                .body(loginRequestBody)
                .when()
                .post(API_BASE_URL + "/user/login")
                .then()
                .statusCode(404); // Assuming 401 Unauthorized or 404 Not Found is returned for failed login
    }
}
