package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.concurrent.TimeUnit;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

    public static WebDriver driver;
    public String baseURL;
    @LocalServerPort
    private int port;

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
        baseURL = baseURL = "http://localhost:" + port;
    }

    @Test
    public void testUserSignup() throws InterruptedException {
        String firstName = "Zori";
        String lastName = "Dahan";
        String username = "User_1";
        String password = "123";
        String password2 = "123";
        String email = "123";

        driver.get(baseURL + "/signup");

        SignupPage signupPage = new SignupPage(driver);
        signupPage.signup(firstName, lastName, username, password, password2, email);

        driver.get(baseURL + "/login");

        LoginPage loginPage = new LoginPage(driver);
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        loginPage.login(username, password);
    }

}
