package ru.selenium.training;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class Task9 extends CommonMethods {

    @Before
    public void start() {
        //System.setProperty("webdriver.chrome.driver", "C:\\workspace\\chromedriver.exe");
        driver = new ChromeDriver();
        //driver = new FirefoxDriver();
        //driver.manage().window().maximize();
        //it = new WebDriverWait(driver, Duration.ofSeconds());
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
    }

    private final By linkLocator = By.xpath("//a[contains(text(), 'first second')]");
    private final By bannerLocator = By.xpath("//div[@class='banner-message-container']");

    @Test
    public void checkOnLinkInChrome() {
        //login
        loginToAdminPageAsAdmin();
        driver.findElement(linkLocator).click();
        System.out.println("Link has been clicked");
        wait.until(d -> d.findElement(bannerLocator));
        System.out.println(driver.getTitle());
        //wait.until(titleIs("Dashboard | My Store"));
    }

    public void loginToAdminPageAsAdmin() {
        driver.get("https://output.jsbin.com/zezuyo");
        wait.until(d -> d.findElement(linkLocator));
    }
}
