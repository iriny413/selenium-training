package ru.selenium.training;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.sql.SQLOutput;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static junit.framework.TestCase.assertTrue;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class Task8 extends CommonMethods{
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
        //System.setProperty("webdriver.chrome.driver", "C:\\workspace\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
    }

    @Test public void checkCountriesSorting(){
        //login
        loginToAdminPageAsAdmin();
        //open page 'Countries'
        assertTrue(openPage("Countries"));
        List<WebElement> rows = wait.until(d -> d.findElements(By.xpath("//tbody//td[5]/a")));
        final int initialSize = rows.size();
        for (int i = 0; i < initialSize; i++) {
            List<WebElement> rows1 = wait.until(d -> d.findElements(By.xpath("//tbody//td[5]/a")));
            String countryName = rows1.get(i).getText();
            System.out.println("Country# " + i + " - " + countryName);
        }
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
    }

    public void loginToAdminPageAsAdmin(){
        driver.get("http://localhost:8090/litecart/admin/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        wait.until(d -> d.findElement(By.xpath("//button[@class='btn btn-default']")));
        driver.findElement(By.xpath("//button[@class='btn btn-default']")).click();
        System.out.println(driver.getTitle());
        wait.until(titleIs("Dashboard | My Store"));
    }

    public boolean isElementPresent(String xpath) {
        boolean found;
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        found = driver.findElements(By.xpath(xpath)).size() > 0;
        driver.manage().timeouts().implicitlyWait(6, TimeUnit.SECONDS);
        return found;
    }

    public boolean openPage(String pageName){
        if (isElementPresent("//span[@class='name'][contains(.,'" + pageName + "')]")){
           driver.findElement(By.xpath("//span[@class='name'][contains(.,'" + pageName + "')]")).click();
           return true;
        }
        else{
            System.out.println("Page not found");
            driver.quit();
            return false;
        }
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
