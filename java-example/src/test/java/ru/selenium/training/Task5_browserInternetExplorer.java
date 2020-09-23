package ru.selenium.training;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class Task5_browserInternetExplorer {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
    System.setProperty("webdriver.internetExplorer.driver", "C:\\workspace\\IEDriverServer.exe");
    driver = new InternetExplorerDriver();
    wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    driver.manage().window().maximize();
    }

    @Test
    public void LoginAdminPage_IE(){
        driver.get("http://localhost/litecart/");
        System.out.println(driver.getTitle());
        String text1 = "My Store | Online Store";
        wait.until(titleIs(text1));
        driver.findElement(By.xpath("//a[@class='dropdown-toggle']//i[@class='fa fa-user']")).click();
        driver.findElement(By.name("email")).sendKeys("kubik-rub@rambler.ru");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.xpath("//ul[@class='dropdown-menu']//button[@class='btn btn-default']")).click();
        System.out.println(driver.getTitle());
        String text2 = "My Store | Online Store";
        wait.until(titleIs(text1));
        driver.findElement(By.xpath("//div[contains(text(),' You are now logged in as Irina Khv.')]"));
        System.out.println("I'm logged in as admin!");
        driver.findElement(By.xpath("//a[@class='dropdown-toggle']//i[@class='fa fa-user']")).click();
        driver.findElement(By.xpath("//ul[@class='dropdown-menu']//a[contains(text(),'Logout')]")).click();
        driver.findElement(By.xpath("//div[contains(text(),' You are now logged out.')]"));
        System.out.println("I've signed out.");
    }

    @After
    public void stop(){
        driver.quit();
        driver = null;
    }

}
