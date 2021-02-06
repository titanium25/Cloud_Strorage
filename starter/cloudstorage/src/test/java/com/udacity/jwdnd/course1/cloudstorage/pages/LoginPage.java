package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    @FindBy(id = "login")
    private WebElement usernameField;

    @FindBy(id = "password")
    private WebElement passwordField;

    @FindBy(id = "submit-button")
    private WebElement submitButton;

    @FindBy(id = "Success-msg")
    private WebElement successMsg;

    @FindBy(id = "Invalid-msg")
    private WebElement invalidUserNameMsg;

    @FindBy(id = "Logout-msg")
    private WebElement logoutMsg;

    public LoginPage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
    }

    public void login(String username, String password) {
        this.usernameField.sendKeys(username);
        this.passwordField.sendKeys(password);
        this.submitButton.click();
    }

    public boolean isInvalid() {
        return this.invalidUserNameMsg.isDisplayed();
    }

    public boolean isLoggedOut() {
        return this.logoutMsg.isEnabled();
    }

}