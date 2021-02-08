package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.pages.NotePage;
import com.udacity.jwdnd.course1.cloudstorage.pages.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.ResultPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.SignupPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NotesTests {

    // RANDOM_PORT, port of server
    @LocalServerPort
    private int port;

    // fields:
    private static WebDriver driver;
    private NotePage notePage;
    private ResultPage resultPage;
    private String baseURL;

    // simulate signup user:
    private String firstName = "Zori";
    private String lastName = "Dahan";
    private String username = "User_1";
    private String password = "123";
    private String email = "mail@gmail.com";

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
        driver.manage().timeouts().implicitlyWait(6, TimeUnit.SECONDS); // because of animation need to delay
        loginPage.login(username, password);

        // currently logged in at this stage
        // initialize homepage page:
        notePage = new NotePage(driver);

        /** add a new note so that we can test for add/edit/delete at same time: */
        // simulate user to click on Notes tab:
        notePage.clickNoteTab();
    }

    /**
     * Write a test that creates a note, and verifies it is displayed.
     */
    @Test
    public void noteCreateTest() {

        // simulate user to click "Add/Edit a Note" button to add new note:
        notePage.clickAddNoteBtn();
        // fill in data to add a new note:
        notePage.fillNoteData("Test Title", "Test Description");

        // after successfully added new note, navigate to Result page:
        // initialize new Result page object:
        resultPage = new ResultPage(driver);
        // navigate back to /home by click on "Here" link:
        resultPage.clickHereBtn();

        // test if new note's title and description match:
        assertEquals("Test Title", notePage.getNoteTitleText());
        assertEquals("Test Description", notePage.getNoteDescriptionText());
    }

    /**
     * Write a test that edits an existing note and verifies that the changes are displayed.
     */

    @Test
    public void noteEditTest() {
        // simulate user to click "Add/Edit a Note" button to add new note:
        notePage.clickAddNoteBtn();
        // fill in data to add a new note:
        notePage.fillNoteData("Test Title", "Test Description");

        // after successfully added new note, navigate to Result page:
        // initialize new Result page object:
        resultPage = new ResultPage(driver);
        // navigate back to /home by click on "Here" link:
        resultPage.clickHereBtn();


        notePage.clickEditBtn();
        notePage.fillNoteData("New Test Title", "New Test Description");
        resultPage.clickHereBtn();
        assertEquals("New Test Title", notePage.getNoteTitleText());
        assertEquals("New Test Description", notePage.getNoteDescriptionText());

    }

    /**
     * Write a test that deletes a note and verifies that the note is no longer displayed.
     */

    @Test
    public void noteDeleteTest() {
        // simulate user to click "Add/Edit a Note" button to add new note:
        notePage.clickAddNoteBtn();
        // fill in data to add a new note:
        notePage.fillNoteData("Test Title", "Test Description");

        // after successfully added new note, navigate to Result page:
        // initialize new Result page object:
        resultPage = new ResultPage(driver);
        // navigate back to /home by click on "Here" link:
        resultPage.clickHereBtn();

        notePage.clickDeleteBtn();
        resultPage.clickHereBtn();

        // test there should be no note data on homepage:
        // use assertThrows() with NoSuchElementException.class to test data does not exist:

        assertThrows(NoSuchElementException.class, () -> {
            notePage.getNoteTitleText();
        });
    }
}
