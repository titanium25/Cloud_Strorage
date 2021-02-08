package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.pages.CredentialPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.ResultPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.SignupPage;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
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

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CredentialTests {

    // RANDOM_PORT, port of server
    @LocalServerPort
    private int port;

    // fields:
    private static WebDriver driver;
    private CredentialPage credentialPage;
    private CredentialService credentialService;
    private EncryptionService encryptionService;
    private ResultPage resultPage;
    private String baseURL;

    // simulate signup user:
    private String firstName = "Zori";
    private String lastName = "Dahan";
    private String username = "User_1";
    private String password = "123";
    private String email = "mail@gmail.com";
    private String url = "google.com";

    // before all test, initialize Driver as Chrome browser:
    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    // after all tests, quit driver:
    @AfterAll
    public static void afterAll() {
        driver.quit();
        driver = null;
    }

    // before each test, signup and login to get to homepage:
    @BeforeEach
    public void beforeEach() {
        baseURL = "http://localhost:" + port;

        // navigate to /signup:
        driver.get(baseURL + "/signup");
        // initialize object for SignupPage:
        SignupPage signupPage = new SignupPage(driver);
        // simulate new user:
        signupPage.signup(firstName, lastName, username, password, password, email);

        // initialize object for LoginPage:
        LoginPage loginPage = new LoginPage(driver);
        driver.manage().timeouts().implicitlyWait(6, TimeUnit.SECONDS); // delay due to animation on login page
        loginPage.login(username, password);

        // currently logged in at this stage
        // initialize homepage page:
        credentialPage = new CredentialPage(driver);

        // simulate user to click on Credentials tab:
        credentialPage.clickCredTab();
    }

    /**
     * Write a test that creates a set of credentials, verifies that they are displayed, and verifies that the displayed password is encrypted.
     */
    @Test
    public void credentialCreateTest() {
        credentialPage.clickAddCredBtn();
        credentialPage.fillCredentialData(url,username,password);
        resultPage = new ResultPage(driver);
        resultPage.clickHereBtn();
//        credentialPage.clickCredTab();
        assertEquals("Home", driver.getTitle());
        // initialize Credential object:
        // since this is a test, just get the very first value of data displayed on screen:
//        Credential credential = this.credentialService.getByCredentialId(1);
//        assertEquals(url, credentialPage.getUrlText());
//        assertEquals(username, credentialPage.getUsernameText());
//        assertEquals(password, credentialPage.getPasswordText());
        // https://github.com/ploratran/SuperDuperDrive/blob/2e4826bbbc4bcc659cafb73be8de01f1c4bb6c14/src/test/java/com/udacity/jwdnd/course1/cloudstorage/CredentialTests.java
    }
}
