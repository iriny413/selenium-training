package ru.selenium.training;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static junit.framework.TestCase.assertTrue;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

/**
 * Задание 5. Сделайте сценарий, проверяющий пункты меню в панели администратора
 * Сценарий должен выполнять следующие действия:
 * 1) входит в панель администратора http://localhost/litecart/admin
 * 2) прокликивает последовательно все пункты меню слева, включая вложенные пункты
 * 3) для каждой страницы проверяет наличие заголовка
 */

public class Task7_waits {
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

    @Test
    public void clickEveryLeftMenuOption() throws InterruptedException {
        //login
        loginToAdminPageAsAdmin();
        //count main options
        assertTrue(areElementsPresent("//ul[@id='box-apps-menu']//li[contains(@class,'app')]/a"));
        List<WebElement> rows = wait.until(d -> d.findElements(By.xpath("//ul[@id='box-apps-menu']//li[contains(@class,'app')]/a")));
        final int initialSize = rows.size();
        for (int i = 0; i < initialSize; i++) {
            List<WebElement> rows1 = wait.until(d -> d.findElements(By.xpath("//ul[@id='box-apps-menu']//li[contains(@class,'app')]/a")));
            System.out.println("Title is found: " + i + ": " + rows1.get(i).getText());
            rows1.get(i).click();
            //click suboption(s)
            clickEverySubOption();
        }
    }

    private void clickEverySubOption() {
        if (isElementPresent("//ul[@class='docs']")) {
            List<WebElement> subrows = wait.until(d -> d.findElements(By.xpath("//ul[@class='docs']//span[@class='name']")));
            final int initialSize2 = subrows.size();
//                    for (WebElement subrow : subrows) {
            for (int j = 0; j < initialSize2; j++) {
                List<WebElement> subrows1 = wait.until(d -> d.findElements(By.xpath("//ul[@class='docs']//span[@class='name']")));
                System.out.println("Subtitle is found: " + j + ": " + subrows1.get(j).getAttribute("innerText"));
                subrows1.get(j).click();
            }
        }
    }

    @FindBy(xpath = "//button[@class='btn btn-default']")
    private WebElement button_login;

    private void loginToAdminPageAsAdmin() throws InterruptedException {
        driver.get("http://localhost:8090/litecart/admin/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        assertTrue(isElementPresent("//button[@class='btn btn-default']"));
        wait.until(d -> d.findElement(By.xpath("//button[@class='btn btn-default']")));
        driver.findElement(By.xpath("//button[@class='btn btn-default']")).click();
        System.out.println(driver.getTitle());
        wait.until(titleIs("Dashboard | My Store"));
    }

    public boolean isElementPresent(By locator) {
        try {
            wait.until(d -> d.findElement(locator));
            //driver.findElement(locator);
            return true;
        } catch (TimeoutException ex) {
            return false;
        }
    }

    public boolean areElementsPresent(By locator) {
        boolean found;
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        found = driver.findElements(locator).size() > 0;
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

    public boolean isElementPresent(String xpath) {
        boolean found;
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        found = driver.findElements(By.xpath(xpath)).size() > 0;
        driver.manage().timeouts().implicitlyWait(6, TimeUnit.SECONDS);
        return found;
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}

