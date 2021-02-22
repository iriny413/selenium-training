package ru.selenium.training;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class Task10 extends CommonMethods {
    /**
     * Написать тест:  открыть страницу https://snipp.ru/jquery/masked-input ;
     * найти поле с вводом номера телефона ; ввести туда значение – 9515555555;
     * найти первое поле ввода с датой и ввести 12.11.1990; вернуться в поле с вводом номера
     * и изменить 3 и 4 знак с конца «55» на «66»  (т.е результат будет 9515556655)
     */

    @Before
    public void start() {
        driver = new ChromeDriver();
        //driver.manage().window().maximize();
        //it = new WebDriverWait(driver, Duration.ofSeconds());
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
    }

    private final By pageHeaderLocator = By.xpath("//h1[contains(text(),'Маски ввода для текстовых полей')]");
    private final By phoneInputLocator = By.xpath("//input[@placeholder = 'Номер телефона']");
    private final By dateInputLocator = By.xpath("//input[@class='mask-date form-control']");

    @Test
    public void enterText() {
        login();

        //найти поле с вводом номера телефона ; ввести туда значение – 9515555555;
        driver.switchTo().frame(0);
        WebElement phoneNumber = driver.findElement(phoneInputLocator);
        phoneNumber.sendKeys((Keys.HOME + "9515555555"));
        System.out.println("Field to insert the phone number has been found");
        driver.switchTo().defaultContent();

        //найти первое поле ввода с датой и ввести 12.11.1990
        driver.switchTo().frame(1);
        WebElement date = driver.findElement(dateInputLocator);
        date.sendKeys((Keys.HOME + "12.11.1990"));
        driver.switchTo().defaultContent();

        //изменить 3 и 4 знак с конца «55» на «66»
        driver.switchTo().frame(0);
        phoneNumber.click();
        phoneNumber.sendKeys(Keys.END);
        phoneNumber.sendKeys(Keys.ARROW_LEFT);
        phoneNumber.sendKeys(Keys.ARROW_LEFT);
        phoneNumber.sendKeys(Keys.ARROW_LEFT);
        phoneNumber.sendKeys(Keys.ARROW_LEFT);
        phoneNumber.sendKeys(Keys.ARROW_LEFT);
        phoneNumber.sendKeys("66");
        driver.switchTo().defaultContent();
    }

    public void login() {
        driver.get("https://snipp.ru/jquery/masked-input");
        //wait.until(d -> d.findElement(pageHeaderLocator));
    }
}

