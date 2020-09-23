package ru.selenium.training;

import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

/**
 * Задание 5. Отрыть одну и ту же страницу в разных браузерах
 * Сделайте несколько тестов открытия страницы (пример:http://localhost/litecart/) в трех браузерах (Chrome, Firefox, IE)
 * Рекомендации: тесты должны включать в себя проверку открытия страницы
 * <p>
 * chromedriver: https://sites.google.com/a/chromium.org/chromedriver/downloads
 * IEDriverServer: http://www.seleniumhq.org/download/
 * geckodriver: https://github.com/mozilla/geckodriver/releases
 * msedgedriver/EdgeDriver: https://developer.microsoft.com/ru-ru/microsoft-edge/tools/webdriver/
 */
public class Task5LoginDifferentBrowsers {
    private WebDriver driver;
    private WebDriverWait wait;

    public void login(){
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
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
        driver.findElement(By.xpath("//input[@type='search']")).sendKeys("Orders");
        System.out.println(driver.getTitle());
        String text3 = "My Store | Online Store";
        wait.until(titleIs(text3));
        driver.findElement(By.xpath("//a[@class='dropdown-toggle']//i[@class='fa fa-user']")).click();
        driver.findElement(By.xpath("//ul[@class='dropdown-menu']//a[contains(text(),'Logout')]")).click();
        driver.findElement(By.xpath("//div[contains(text(),' You are now logged out.')]"));
        System.out.println("I've signed out.");
    }

    @Test
    public void LoginAdminPage_Chrome(){
        //System.setProperty("webdriver.chrome.driver", "C:\\workspace\\chromedriver.exe");
        driver = new ChromeDriver();
        login();
    }

    @Test
    public void LoginAdminPage_FF(){
//        System.setProperty("webdriver.gecko.driver", "C:\\workspace\\geckodriver.exe");
        driver = new FirefoxDriver();
        login();
    }
    @Test
    public void LoginAdminPage_IE(){
        //System.setProperty("webdriver.internetExplorer.driver", "C:\\workspace\\IEDriverServer.exe");
        driver = new InternetExplorerDriver();
        login();
    }

    @After
    public void stop(){
        driver.quit();
        driver = null;
    }
}


