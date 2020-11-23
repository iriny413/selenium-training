package ru.selenium.training;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

/**
 * Задание 5. Сделайте сценарий, проверяющий пункты меню в панели администратора
 * Сценарий должен выполнять следующие действия:
 * 1) входит в панель администратора http://localhost/litecart/admin
 * 2) прокликивает последовательно все пункты меню слева, включая вложенные пункты
 * 3) для каждой страницы проверяет наличие заголовка (то есть элемента с
 * тегом h1)
 */
public class Task7_waits {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
        //System.setProperty("webdriver.chrome.driver", "C:\\workspace\\chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test
    public void clickEveryLeftMenuOption() {
        loginToAdminPageAsAdmin();
        //Check that user is logged in on the right web page
        List<WebElement> rows = driver.findElements(By.xpath("//ul[@id='box-apps-menu']//li[contains(@class,'app')]/a"));
        final int initialSize = rows.size();
        for (int i = 0; i < initialSize; i++) {
            List<WebElement> rows2 = driver.findElements(By.xpath("//ul[@id='box-apps-menu']//li[contains(@class,'app')]/a"));
//                clickMainOption(rows2.get(i));
//                String foundTitle = driver.getTitle();
//                wait.until(titleIs(foundTitle));
            System.out.println("Title is found: " + rows2.get(i).getText());
            rows2.get(i).click();
            //     i = i +1;
//                if (isElementPresent("//li[@class='app selected']/ul[@class='docs']")) {
//                    List<WebElement> subrows = driver.findElements(By.xpath("//li[@class='app selected']/ul[@class='docs']//li"));
//                    for (WebElement subrow : subrows) {
//                        clickSubOption(subrow);
//                        String foundSubTitle = driver.getTitle();
//                        wait.until(titleIs(foundTitle));
//                        System.out.println("Subtitle is found: " + foundSubTitle);
//                    }
//                }
        }
    }

    public void loginToAdminPageAsAdmin() {
        driver.get("http://localhost:8090/litecart/admin/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.xpath("//button[@class='btn btn-default']")).click();
    }

    public boolean isTitleCorrect(String text) {
        String foundText = driver.getTitle();
        System.out.println(foundText);
        if (foundText.equals(text)) {
            if (wait.until(titleIs(text))) {
                System.out.println("Title is correct: " + text);
                return true;
            }
        } else {
            System.out.println("Title is incorrect: " + text);
        }
        return false;
    }

    public void clickMainOption(WebElement webElement) {
        String a = webElement.getText();
        System.out.println("name of list " + a);
        try {
            webElement.click();
        } catch (StaleElementReferenceException e) {
            System.out.println("StaleElementException appeared" + e);
        }
    }

    public void clickSubOption(WebElement webElement) {
        driver.manage().timeouts().setScriptTimeout(3, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(3, TimeUnit.SECONDS);
        try {
            webElement.click();
//            waitForAjaxRequests();
        } catch (StaleElementReferenceException e) {
            System.out.println("StaleElementException appeared" + e);
        }
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

