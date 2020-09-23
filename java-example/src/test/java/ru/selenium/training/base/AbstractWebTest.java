package ru.selenium.training.base;

import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class AbstractWebTest {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start(String driverName){
        selectDriver(driverName);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    public void selectDriver(String driverName) {
        switch (driverName) {
            case "Chrome":
                //System.setProperty("webdriver.chrome.driver", "C:\\workspace\\chromedriver.exe");
                driver = new ChromeDriver();
                break;
            case "FF":
                driver = new FirefoxDriver();
                break;
            case "IE":
                driver = new InternetExplorerDriver();
                break;
        }

    }

}
