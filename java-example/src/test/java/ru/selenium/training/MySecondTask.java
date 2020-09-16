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
 * Created by i. khvichiya on 16.09.2020
 */
public class MySecondTask {
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
    public void MySecondTest(){
        driver.get("http://www.google.com/");
        driver.findElement(By.name("q")).sendKeys("Stop Coronavirus");
        driver.findElement(By.name("btnK")).click();
        driver.findElement(By.xpath("//h3[contains(text(),'Stop Coronavirus: Главная')]")).click();
        System.out.println(driver.getTitle());
        String text1 = "Главная - Stop Coronavirus";
        wait.until(titleIs(text1));
        driver.findElement(By.xpath("//div[@class='page-item-title'][contains(text(),'Дом, офис')]")).click();
        System.out.println(driver.getTitle());
        String text2 = "Дом, офис - Stop Coronavirus";
        wait.until(titleIs(text2));
        driver.findElement(By.xpath("//div[@class='list-posts']//div[3]//div[@class='post-item-inner']")).click();
        System.out.println(driver.getTitle());
        String text3 = "Elastomeric Half Facepiece Respirator - Stop Coronavirus";
        wait.until(titleIs(text3));
        driver.get("https://www.youtube.com/");
        System.out.println(driver.getTitle());
        String text4 = "YouTube";
        wait.until(titleIs(text4));
        System.out.println("Let's stop this test & go watch a movie!");
    }

    @After
    public void stop(){
        driver.quit();
        driver = null;
    }
}
