package ru.selenium.training;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
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
        if (isTitleCorrect("My Store")) {
            //find all menu element on the left side & click
            List<WebElement> rows = driver.findElements(By.xpath("//ul[@id='box-apps-menu']//li[@class='app']"));
            for (WebElement row : rows) {
                clickMainOption(row);
                String foundTitle = driver.getTitle();
                wait.until(titleIs(foundTitle));
                System.out.println("Title is found: " + foundTitle);
                if (isElementPresent("//li[@class='app selected']/ul[@class='docs']")) {
                    List<WebElement> subrows = driver.findElements(By.xpath("//li[@class='app selected']/ul[@class='docs']//li"));
                    for (WebElement subrow : subrows) {
                        clickSubOption(subrow);
                        String foundSubTitle = driver.getTitle();
                        wait.until(titleIs(foundTitle));
                        System.out.println("Subtitle is found: " + foundSubTitle);
                    }
                }
            }
            System.out.println("Main elements were clicked");
        } else {
            System.out.println("User is not logged in or a wrong page is opened");
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
        staleElementXpathRefresh(webElement);
        driver.manage().timeouts().pageLoadTimeout(3,TimeUnit.SECONDS);
        driver.manage().timeouts().setScriptTimeout(3,TimeUnit.SECONDS);
        try {
            webElement.click();
//            waitForAjaxRequests();
        } catch (StaleElementReferenceException e) {
            System.out.println("StaleElementException appeared" + e);
        }
    }

    public void clickSubOption(WebElement webElement) {
        staleElementXpathRefresh(webElement);
        driver.manage().timeouts().setScriptTimeout(3,TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(3,TimeUnit.SECONDS);
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

    /**
     * Check page status, wait until its in ready/complete state.
     */
    public void waitForPageToLoad() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        int timeWaitedInMilliseconds = 0;
        int maxWaitTimeInMilliseconds = 2000;

        while (timeWaitedInMilliseconds < maxWaitTimeInMilliseconds) {
            //System.out.println(js.executeScript("return document.readyState"));
            if (js.executeScript("return document.readyState").equals("interactive")) {
                System.out.println("Waited interactive: " + timeWaitedInMilliseconds);
                break;
            }
            waitElementsReload(100);
            timeWaitedInMilliseconds += 100;
        }

        timeWaitedInMilliseconds = 0;
        while (!js.executeScript("return document.readyState").equals("complete")) {
            System.out.println("waiting !!!!");
            waitElementsReload(500);
            timeWaitedInMilliseconds += 500;
            if (timeWaitedInMilliseconds == 10000) {
                break;
            }
        }
    }

//    public void waitForAjaxRequests() {
//        WebDriverWait webDriverWait = new WebDriverWait(driver, 30);
//        webDriverWait.until(new ExpectedCondition<Boolean>() {
//            public Boolean apply(WebDriver d) {
//                JavascriptExecutor js = (JavascriptExecutor) d;
//                Boolean readyState = (Boolean) js.executeScript("return document.readyState == 'complete'");
//                Boolean requestsAreFinished = (Boolean) js.executeScript("return !!window.jQuery && window.jQuery.active == 0");
//                Boolean blockedIsHidden = (Boolean) js.executeScript("return document.getElementsByClassName('uiPageBlockerJS').length < 1 || document.getElementsByClassName('uiPageBlockerJS')[0].getAttribute('style') == undefined || (document.getElementsByClassName('uiPageBlockerJS')[0].getAttribute('style') != undefined && !document.getElementsByClassName('uiPageBlockerJS')[0].getAttribute('style').includes('block'))");
//                return requestsAreFinished && readyState && blockedIsHidden;
//            }
//        });
//        waitToBeInvisible("//div[@class='uiPageBlockerJS']");
//    }

    /**
     * Thread sleep
     *
     * @param ms time in milliseconds
     */
    protected void waitElementsReload(int ms) {
        try {
            Thread.sleep(ms);
        } catch (Exception e) {
            System.out.println("Exception!" + e);
        }
    }

    /**
     * Reinitialize webelement, get its xpath and find again
     *
     * @param webElement webelement
     * @return webelement
     */
    protected WebElement staleElementXpathRefresh(WebElement webElement) {
        WebElement webElementRefreshed = null;
        int count = 0;
        while (count < 4) {
            try {
                //get xpath of webelement
                String xpath = xpathExtractor(webElement);
                //find it again
                webElementRefreshed = driver.findElement(By.xpath(xpath));
            } catch (StaleElementReferenceException e) {
                //logger.error("Stale element!");
                count = count + 1;
            }
            count = count + 4;
        }
        return webElementRefreshed;
    }

    /**
     * Returns element locator (xpath) from its annotation (@FindBy(....))
     *
     * @param webelement webelement
     * @return locator
     */
    protected String xpathExtractor(WebElement webelement) {
        try {
            String myString = webelement.toString();
            Pattern pattern = Pattern.compile("(//.*)");
            Matcher matcher = pattern.matcher(myString);

            if (matcher.find()) {
                String matchedString = matcher.group();
                return matchedString.substring(0, matchedString.length() - 1);
            }
        } catch (StaleElementReferenceException e) {
            System.out.println("StaleElementReferenceException");
        }

        return null;
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}

