package com.rozetka.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.CapabilityType;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.lang.String.format;

public class ShoppingCart {
    public static void main(String[] args) {
//        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        WebDriverManager.chromedriver().arch64().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        //driver.get("https://www.google.com");
        driver.get("https://www.rozetka.com.ua");

        findAndBuy(driver, "acer aspire", true);
        pause();
        findAndBuy(driver, "freggia", false);
    }

    private static void findAndBuy(WebDriver driver, String searchName, boolean doClose) {
        WebElement inputField = driver.findElement(/*By.name("q")*/
                By.xpath("//*[contains(@class, 'search-form__input ng-untouched ng-pristine ng-valid')]"));
        inputField.sendKeys(searchName, Keys.ENTER);

        List<WebElement> foundGoods = driver.findElements(
                By.xpath("//*[contains(@class, 'goods-tile__picture ng-star-inserted')]"));
        List<WebElement> goods = new ArrayList<>(foundGoods);
        int index = 3 - 1;
        if (goods.size() <= index) {
            throw new RuntimeException(format("Something went wrong... Count of goods = %d", goods.size()));
        }
        goods.get(index).click();

        WebElement buyButton = driver.findElement(
                By.xpath("//*[contains(@class,"
                        + " 'buy-button button button--with-icon button--green button--medium"
                        + " buy-button--tile ng-star-inserted')]"));
        buyButton.click();

        pause();

        if (doClose) {
            WebElement closeButton = driver.findElement(
                    By.xpath("//*[contains(@class, 'modal__close')]"));
            closeButton.click();
        }
    }

    private static void pause() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
























