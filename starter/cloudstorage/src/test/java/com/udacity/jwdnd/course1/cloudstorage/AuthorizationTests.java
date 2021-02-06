package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.pages.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.pages.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.SignupPage;
import io.github.bonigarcia.wdm.WebDriverManager;

import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthorizationTests {

    @LocalServerPort
    private int port;

    private static WebDriver driver;
    private SignupPage signupPage;
    private LoginPage loginPage;
    private HomePage homePage;
    private String baseURL;

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @AfterAll
    public static void afterAll() {
        driver.quit();
        driver = null;
    }

    @BeforeEach
    public void beforeEach() {
        baseURL = "http://localhost:" + port;
    }

    @Test
    public void testUserSignupLoginLogout() {
        String firstName = "Zori";
        String lastName = "Dahan";
        String username = "User_1";
        String password = "123";
        String password2 = "123";
        String email = "mail@gmail.com";

        // Go to signup page
        driver.get(baseURL + "/signup");
        signupPage = new SignupPage(driver);
        signupPage.signup(firstName, lastName, username, password, password2, email);
//        assertEquals("Sign Up", driver.getTitle());

        // Go to login page
        driver.get(baseURL + "/login");
        loginPage = new LoginPage(driver);
        driver.manage().timeouts().implicitlyWait(6, TimeUnit.SECONDS); // because of animation need to delay
        loginPage.login(username, password);
        assertEquals("Home", driver.getTitle());

        // Go to home page and logout
        homePage = new HomePage(driver);
        homePage.logout();
        assertTrue(loginPage.isLoggedOut());
        driver.get(baseURL + "/home");
        assertEquals("Login", driver.getTitle());

    }

}
