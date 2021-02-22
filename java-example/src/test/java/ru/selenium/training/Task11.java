package ru.selenium.training;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.util.concurrent.TimeUnit;

public class Task11 extends CommonMethods {

    @Before
    public void start() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
    }

    //sortable 1
    private final By s1_elem2 = By.xpath("//ul[@id='sortable1']//li[contains(text(),'Item 2')]");
    private final By s1_elem3 = By.xpath("//ul[@id='sortable1']//li[contains(text(),'Item 3')]");
    private final By s1_elem4 = By.xpath("//ul[@id='sortable1']//li[contains(text(),'Item 4')]");
    //sortable 2
    private final By s2_elem2 = By.xpath("//ul[@id='sortable2']//li[contains(text(),'Item 2')]");

    @Test
    public void rearrangeElements() {
        driver.get("https://jqueryui.com/resources/demos/sortable/connect-lists.html");
        WebElement s1_e2 = driver.findElement(s1_elem2);
        WebElement s1_e3 = driver.findElement(s1_elem3);
        WebElement s1_e4 = driver.findElement(s1_elem4);
        WebElement s2_e2 = driver.findElement(s2_elem2);
        Actions action = new Actions(driver);
        action.dragAndDrop(s1_e2, s1_e4).perform();
        action.dragAndDrop(s1_e3, s2_e2).perform();
    }
}

