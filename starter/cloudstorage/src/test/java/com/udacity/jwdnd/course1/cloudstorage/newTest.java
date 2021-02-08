package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.pages.CredentialPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.ResultPage;
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
public class newTest {

    // RANDOM_PORT, port of server
    @LocalServerPort
    private int port;

    // fields:
    private static WebDriver driver;
    private String baseURL;
    private CredentialPage credentialPage;
    private ResultPage resultPage;

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
        driver.get(baseURL + "/login");
        LoginPage loginPage = new LoginPage(driver);
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS); // delay due to animation on login page
        loginPage.login("user", "123");
    }

    @Test
    public void newTestMethod() {
        credentialPage = new CredentialPage(driver);
        credentialPage.clickCredTab();
        credentialPage.clickAddCredBtn();
        credentialPage.fillCredentialData("url","username","password");
        assertEquals("Result", driver.getTitle());
        resultPage = new ResultPage(driver);
        resultPage.clickHereBtn();
        assertEquals("Home", driver.getTitle());

    }
}
