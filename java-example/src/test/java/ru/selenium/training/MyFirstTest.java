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
 * Created by k.grigorchuk on 27.02.2017.
 */
public class MyFirstTest {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start(){
        //System.setProperty("webdriver.chrome.driver", "C:\\workspace\\chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @Test
    public void MyFirstTest(){
        driver.get("http://www.google.com/");
        driver.findElement(By.name("q")).sendKeys("webdriver");
        driver.findElement(By.name("btnK")).click();
        System.out.println(driver.getTitle());
        //String text = "webdriver - Поиск в Google123";
        String text = "webdriver - Google Search";
        wait.until(titleIs(text));
    }

    @After
    public void stop(){
        driver.quit();
        driver = null;
    }

}