package ru.selenium.training;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class Subscripts {
    private WebDriver driver;
    private WebDriverWait wait;

    public void isUserLoggedIn(String text){
        System.out.println(driver.getTitle());
        wait.until(titleIs(text));
        System.out.println("User is logged in");
    }
}
