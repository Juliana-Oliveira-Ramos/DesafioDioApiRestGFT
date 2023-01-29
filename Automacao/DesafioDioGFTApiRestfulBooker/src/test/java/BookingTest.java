import Entites.Booking;
import Entites.BookingDates;
import Entites.User;
import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.filter.log.ErrorLoggingFilter;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;

import java.util.*;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static io.restassured.config.LogConfig.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BookingTest {
    public static Faker faker;
    public static RequestSpecification request;
    private static Booking booking;
    private static BookingDates bookingDates;
    private static User user;

    @Order(1)
@BeforeAll
    public static void Setup(){
        RestAssured.baseURI = "https://restful-booker.herokuapp.com/";
        faker = new Faker();
        user = new User(faker.name().username(),
                faker.name().firstName(),
                faker.name().lastName(),
                faker.internet().safeEmailAddress(),
                faker.internet().password(8,10),
                faker.number().toString());

        bookingDates = new BookingDates("","");
        booking = new Booking(user.getFirstName(), user.getLastName(), (float)faker.number().randomDouble(2,50,
                100000000),true,bookingDates, "");
        RestAssured.filters(new RequestLoggingFilter(),new ResponseLoggingFilter(),new ErrorLoggingFilter());

    }
    @Order(2)
    @BeforeEach
    void setRequest(){
        request = given().config(RestAssured.config().logConfig(logConfig().enableLoggingOfRequestAndResponseIfValidationFails()))
                .contentType(ContentType.JSON)
                .auth().basic("admin", "password123");


    }

    @Order(3)
    @Test
    void getBooksById_ReturnOK(){
        Response response = request
                .when()
                .get("/booking")
                .then()
                .extract().response();

        Assertions.assertNotNull(response);
        Assertions.assertEquals(200, response.statusCode());
    }

    @Order(4)
    @Test
    void getAllBookingByUserFirstName_BookExist_returnOK() {
        request
                .when()
                .queryParam("firstName", "Carol")
                .get("/booking")
                .then()
                .assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .and()
                .body("results", hasSize(greaterThan(0)));
    }

    @Order(5)
    @Test
    void CreateBooking_WithValidatData_returnOk() {
        Booking test = booking;
        given()
                .config(RestAssured.config().logConfig(logConfig().enableLoggingOfRequestAndResponseIfValidationFails()))
                .contentType(ContentType.JSON)
                .when()
                .body(booking)
                .post("/booking")
                .then()
                .body(matchesJsonSchemaInClasspath("createBookingRequestSchema.json"))
                .and()
                .assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON).and().time(lessThan(2000L));



    }

    @Order(6)
   @Test
    void getBooksById_ReturnId(){
        request
                .when()
                .get("/booking/ 174")
                .then()
                .assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .extract()
                .response();
    }
    @Order(7)
@Test
        void updateBooking_ReturnOk(){
                    Map<String,Object> updateBooking = new HashMap<>();
                      updateBooking.put("firstName","Teste");
                      updateBooking.put("lastName","Deu Certo");
                      updateBooking.put("totalPrice",120);
                      updateBooking.put("depositPaid","true");
                      updateBooking.put("additionalNeeds","");
                  given()
                          .log().all()
                  .contentType(ContentType.JSON)
            .body(updateBooking).
            when()
            .put("/booking/174")
            .then()
            .statusCode(200) ;
                  when()
            .get("/booking/174").
            then()
            .log().all()
            .statusCode(200)
            .body("firstName" , is( updateBooking.get("firstName")) )
            .body("lastName" ,is ( updateBooking.get("lastLame")  ) )
            .body("totalPrice" ,is ( updateBooking.get("totalPrice")  ) )
            .body("depositPaid" ,is ( updateBooking.get("depositPaid")  ) )
            .body("checkin" ,is ( updateBooking.get("checkin")  ) )
            .body("checkout" ,is ( updateBooking.get("checkout")  ) );

        }


@Order(8)
@Test
        void partialUpdate_ReturnOk(){

            Map <String,Object>updatePatch = new HashMap<>();
                    updatePatch.put("firstName","Atualizar");
                    updatePatch.put("lastName","Deu Certo");
                    given()
                    .log().all()
                    .contentType(ContentType.JSON)
                    .body(updatePatch)
                    .when()
                    .put("/booking/174")
                    .then()
                    .statusCode(200) ;
            when()
                    .get("/booking/174").
                    then()
                    .log().all()
                    .statusCode(200)
                    .body("firstName" , is( updatePatch.get("firstName")))
                    .body("lastName" ,is ( updatePatch.get("lastName")));


        }

@Order(9)
@Test
    void deleteBooking_ReturnOk(){
        request
                .given()
                .contentType(ContentType.JSON)
                .when()
                .delete("/booking/174")
                .then()
                .statusCode(200);
    }


@Order(10)
@Test
    void pingChecked_ReturnOk(){

    given()
            .when()
            .get("/ping")
            .then()
            .assertThat()
            .statusCode(201);




    }


}
