package base;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static java.util.concurrent.TimeUnit.SECONDS;


public class BaseTest {
    protected WebDriver driver;
    protected WebDriverWait wait;

    @Before
    public void before() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        System.setProperty("webdriver.chrome.driver", "webdriver/chromedriver.exe");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(30, SECONDS);
        driver.manage().timeouts().implicitlyWait(10, SECONDS);
        wait = new WebDriverWait(driver, 10, 1000);

        String baseUrl = "http://www.sberbank.ru/ru/person";

        driver.get(baseUrl);
    }

    @After
    public void after() {
        driver.quit();
    }


    public void scrollToElementJs(WebElement element) {
        JavascriptExecutor javaScriptExecutor = (JavascriptExecutor) driver;
        javaScriptExecutor.executeScript("arguments[0].scrollIntoView(true)", element);
    }

    public void waitUntilElementToBeClickable(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public void waitUntilElementToBeClickable(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public void waitUntilElementToBeVisible(By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated((locator)));
    }

    public void waitUntilElementToBeVisible(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }
    public void fillInputField(WebElement element, String value) {
        scrollToElementJs(element);
        element.sendKeys(value);
        Assert.assertEquals("Поле было заполнено некорректно",
                value, element.getAttribute("value"));
        element.sendKeys(Keys.TAB);

    }
    //
    public void fillInputFieldDate(WebElement element, String value) {
        scrollToElementJs(element);
        waitUntilElementToBeClickable(element);
        element.click();
        element.sendKeys(value);
        String dateValue = value.substring(0, 2) + "." + value.substring(2, 4) + "." + value.substring(4,8);
        Assert.assertEquals("Поле было заполнено некорректно",
                dateValue, element.getAttribute("value"));
        element.sendKeys(Keys.TAB);
    }
    public void fillInputFieldPhone(WebElement element, String value) {
        scrollToElementJs(element);
        waitUntilElementToBeClickable(element);
        element.click();
        element.sendKeys(value);
        String phoneValue = "+7 (" + value.substring(0, 3) + ") " + value.substring(3,6) + "-" + value.substring(6,8) + "-" + value.substring(8, 10);
        Assert.assertEquals("Поле было заполнено некорректно",
                phoneValue, element.getAttribute("value"));
        element.sendKeys(Keys.TAB);
    }
    public void checkMandatoryFields(WebElement element, String value) {
        element.sendKeys(value);
        Assert.assertEquals("Обязательное поле",
                value, element.getAttribute("value"));
    }

}