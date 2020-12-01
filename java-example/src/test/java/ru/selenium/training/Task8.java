package ru.selenium.training;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static junit.framework.TestCase.assertTrue;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class Task8 extends CommonMethods {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
        //System.setProperty("webdriver.chrome.driver", "C:\\workspace\\chromedriver.exe");
        driver = new ChromeDriver();
        //driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
    }

    @Test
    public void checkCountriesSorting() {
        //login
        loginToAdminPageAsAdmin();
        //open page 'Countries'
        assertTrue(openPage("Countries"));
        List<WebElement> rows = wait.until(d -> d.findElements(By.xpath("//tbody//td[5]/a")));
        final int initialSize = rows.size();
        List<String> names = new ArrayList<String>();
        for (int i = 0; i < initialSize; i++) {
            List<WebElement> rows1 = wait.until(d -> d.findElements(By.xpath("//tbody//td[5]/a")));
            String countryName = rows1.get(i).getText();
            System.out.println("Country# " + i + " - " + countryName);
            names.add(countryName);
            rows1.get(i).click();
            //check Geo zones
            checkGeoZones(i);
        }
        Collections.sort(names);
        System.out.println("Alphabetically sorted list of counties is this: " + names);
    }

//    @Test
//    public void checkCountriesSorting() {
//        //login
//        loginToAdminPageAsAdmin();
//        //open page 'Countries'
//        assertTrue(openPage("Countries"));
//        List<WebElement> rows = wait.until(d -> d.findElements(By.xpath("//tbody//td[5]/a")));
//        final int initialSizeC = rows.size();
//        System.out.println("Number of countries is " + initialSizeC);
//        List<String> countryNames = new ArrayList<String>();
//        for (int i = 0; i < initialSizeC; i++) {
//            List<WebElement> rows1 = wait.until(d -> d.findElements(By.xpath("//tbody//td[5]/a")));
//            String countryName = rows1.get(i).getText();
//            System.out.println("Country # " + i + " - " + countryName);
//            countryNames.add(countryName);
//            rows1.get(i).click();
//
//            //check Geo zones
////            checkGeoZones(i);
//        }
//        //sort countries names
//        Collections.sort(countryNames);
//        System.out.println("Alphabetically sorted list of counties is this: " + countryNames);
//    }

    public void checkGeoZones(int countryIndex) {
        if (areElementsPresent("//table[@class='table table-striped table-hover data-table']/tbody/tr")) {
            List<WebElement> zones = wait.until(d -> d.findElements(By.xpath("//table[@class='table table-striped table-hover data-table']/tbody/tr")));
            final int initialSizeZ = zones.size();
            System.out.println("Number of geo zones is " + zones.size());
            List<String> zoneNames = new ArrayList<String>();
            for (int j = 0; j < initialSizeZ; j++) {
                List<WebElement> zones1 = wait.until(d -> d.findElements(By.xpath("//table[@class='table table-striped table-hover data-table']/tbody/tr")));
                String zoneName = zones1.get(j).getText();
                System.out.println("Geo zone # " + j + " - " + zoneName);
                zoneNames.add(zoneName);
            }
            Collections.sort(zoneNames);
            System.out.println("Alphabetically sorted list of geo zones for " + countryIndex + " is this: " + zoneNames);
        } else {
            assertTrue(openPage("Countries"));
        }
    }

    public void loginToAdminPageAsAdmin() {
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

    public boolean areElementsPresent(String xpath) {
        boolean found;
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        found = driver.findElements(By.xpath(xpath)).size() > 0;
        driver.manage().timeouts().implicitlyWait(6, TimeUnit.SECONDS);
        return found;
    }

    public boolean openPage(String pageName) {
        if (isElementPresent("//span[@class='name'][contains(.,'" + pageName + "')]")) {
            driver.findElement(By.xpath("//span[@class='name'][contains(.,'" + pageName + "')]")).click();
            return true;
        } else {
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
