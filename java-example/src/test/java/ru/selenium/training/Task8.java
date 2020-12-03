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
import java.util.Iterator;
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


    private final By countriesList = new By.ByXPath("//table[@class='table table-striped table-hover data-table']//td[5]/a");
    private final By geoZonesList = new By.ByXPath("//table[@class='table table-striped table-hover data-table']/tbody/tr");

    @Test
    public void checkCountriesSorting() {
        //login
        loginToAdminPageAsAdmin();
        //open page 'Countries'
        assertTrue(openPage("Countries"));
        List<WebElement> rows = wait.until(d -> d.findElements(countriesList));
        ArrayList<String> names = new ArrayList<>();
        final int initialSize = rows.size();
        for (int i = 0; i < initialSize; i++) {
            List<WebElement> rows1 = wait.until(d -> d.findElements(countriesList));
            String countryName = rows1.get(i).getAttribute("textContent");
            System.out.println("Country# " + i + " - " + countryName);
            names.add(countryName);

            //check # of geo zones for a country
            //String geoZoneNumber = driver.findElement(By.xpath("//table[@class='table table-striped table-hover data-table']//td[contains(.,'"+ countryName.replace("'","\\'") +"')]/following-sibling::td[@class='text-center']")).getAttribute("textContent");
            String geoZoneNumber = driver.findElement(By.xpath("//table[@class='table table-striped table-hover data-table']//td[contains(.,'" + countryName + "')]/following-sibling::td[@class='text-center']")).getAttribute("textContent");
            if (!geoZoneNumber.equals("0")) {
                System.out.println("Number of geozones for " + countryName + "is " + geoZoneNumber);
                rows1.get(i).click();
                checkGeoZones(i, geoZonesList);
                //sort geoZones alphabetically
                //sortElements(names);
            }
        }
        //sort countries alphabetically
        //sortElements(names);
    }

    public int sortElements(ArrayList<String> names) {
        Iterator<String> iter = names.iterator();
        String str1 = names.get(0);
        String str2 = names.get(1);
        int result = 0;
        while (iter.hasNext()) {
            result = str1.compareTo(str2);

            System.out.println(result);
        }
        return result;
    }

    public void checkGeoZones(int countryIndex, By xpath) {
        findRows(xpath);
        assertTrue(openPage("Countries"));
    }

    public void findRows(By xpath) {
        ArrayList<String> names = new ArrayList<>();
        List<WebElement> rows = wait.until(d -> d.findElements(xpath));
        final int initialSize = rows.size();
        System.out.println("Number of elements is " + rows.size());
        for (int j = 0; j < initialSize; j++) {
            List<WebElement> rows1 = wait.until(d -> d.findElements(xpath));
            String name = rows1.get(j).getAttribute("textContext");
            System.out.println("Name # " + j + " - " + name);
            names.add(name);
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
