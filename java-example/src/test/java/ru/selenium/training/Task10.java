package ru.selenium.training;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class Task10 extends CommonMethods {

    @Before
    public void start() {
        driver = new ChromeDriver();
        //driver.manage().window().maximize();
        //it = new WebDriverWait(driver, Duration.ofSeconds());
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
    }

    private final By phoneInputLocator = By.xpath("//input[@placeholder = 'Номер телефона']");

    @Test
    public void enterText() {
        login();
        WebElement a = driver.findElement(phoneInputLocator);
        a.click();
        a.sendKeys((Keys.HOME + "9515555555"));
        System.out.println("Field to input number has been found");
//        System.out.println(driver.getTitle());
        //wait.until(titleIs("Dashboard | My Store"));
    }

    public void login() {
        driver.get("https://snipp.ru/jquery/masked-input");
        //wait.until(d -> d.findElement(phoneInputLocator));
    }
}

