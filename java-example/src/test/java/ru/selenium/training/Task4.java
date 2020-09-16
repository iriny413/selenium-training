package ru.selenium.training;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

/**
 Задание 4. Сделайте сценарий логина
 Сделайте сценарий для логина в панель администрирования учебного
 приложения http://localhost/litecart/admin/.
 Рекомендации: приложение должно включать действия входа и выхода в учебном приложении
 */

public class Task4 {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start(){
        //System.setProperty("webdriver.chrome.driver", "C:\\workspace\\chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test
    public void LoginAdminPage(){
        driver.get("http://localhost/litecart/admin/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.xpath("//button[@class='btn btn-default']")).click();
        System.out.println(driver.getTitle());
        String text1 = "My Store";
        wait.until(titleIs(text1));
        System.out.println("I'm logged in as admin!");
        driver.findElement(By.xpath("//input[@type='search']")).sendKeys("Orders");
        System.out.println(driver.getTitle());
        String text2 = "Dashboard | My Store";
        wait.until(titleIs(text2));
        driver.findElement(By.xpath("//a[@title='Logout']")).click();
        System.out.println("I've signed out.");
    }

    @After
    public void stop(){
        driver.quit();
        driver = null;
    }
}
