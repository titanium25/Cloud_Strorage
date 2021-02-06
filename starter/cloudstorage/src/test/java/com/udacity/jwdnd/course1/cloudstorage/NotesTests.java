package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.pages.HomePage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NotesTests {

    // RANDOM_PORT, port of server
    @LocalServerPort
    private int port;

    // fields:
    private static WebDriver driver;
    private HomePage homePage;
    private String baseURL;

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

    // before each test, navigate to url to simulate test:
    @BeforeEach
    public void beforeEach() {
        baseURL = "http://localhost:" + port;
    }

    /**
     * Write a test that creates a note, and verifies it is displayed.
     */

}
