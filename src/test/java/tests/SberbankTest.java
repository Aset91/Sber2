package tests;

import base.BaseTest;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

public class SberbankTest extends BaseTest {

    @Test
    public void testCards() {
        //Нажать на меню – Карты
        driver.findElement(By.xpath("//a[@aria-label='Карты']")).click();

        //Выбрать подменю – «Дебетовые карты»
        driver.findElement(By.xpath("//a[@data-cga_click_top_menu='Карты_Дебетовые карты_type_important']")).click();

        //Проверить наличие на странице заголовка – «Дебетовые карты»
        Assert.assertEquals("Заголовок неверный", "Подобрать и заказать дебетовую карту онлайн — СберБанк", driver.getTitle());

        //Под заголовком из представленных карт найти “Молодёжная карта” и кликнуть на кнопку данной карты “Заказать онлайн”
        WebElement orderYouthCard = driver.findElement(By.xpath("//a[@data-product='Молодёжная карта' and contains(@class,'kitt-button')]"));
        scrollToElementJs(orderYouthCard);
        orderYouthCard.click();

        //Проверить наличие на странице заголовка – «Молодёжная карта»
        Assert.assertEquals("Заголовок неверный", "Молодёжная карта СберБанка — СберБанк", driver.getTitle());

        //кликнуть на кнопку «Оформить онлайн» под заголовком
        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("scroll(250, 0)");
        WebElement issueOnline = (driver.findElement(By.xpath("//a[@data-test-id='PageTeaserDict_button']//span")));
        waitUntilElementToBeClickable(issueOnline);
        try {//Ждём прокрутки обратно вверх
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        issueOnline.click();

        try {//Ждём прокрутки вниз после клика на кнопку
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //В представленной форме заполнить поля:
        //•       Фамилию, Имя, Отчетво, Имя и фамилия на карте, Дату рождения, E-mail, Мобильный телефон
        //•       Основной документ - не заполняем
        String inputField = "//input[@data-name='%s']";
        WebElement lastNameFill = driver.findElement(By.xpath("//input[@data-name='lastName']"));
        scrollToElementJs(lastNameFill);
        fillInputField(driver.findElement(By.xpath(String.format(inputField, "lastName"))), "Иванов");
        fillInputField(driver.findElement(By.xpath(String.format(inputField, "firstName"))), "Василий");
        fillInputField(driver.findElement(By.xpath(String.format(inputField, "middleName"))), "Андреевич");
        driver.findElement(By.xpath("//input[@data-name='middleName']")).sendKeys(Keys.TAB);
        driver.findElement(By.xpath("//label[@role='checkbox']")).sendKeys(Keys.TAB);
        driver.findElement(By.xpath("//input[@data-name='cardName']")).sendKeys(Keys.TAB);
        fillInputFieldDate(driver.findElement(By.xpath(String.format(inputField, "birthDate"))), "01012000");
        fillInputField(driver.findElement(By.xpath(String.format(inputField, "email"))), "asetka91@mail.ru");
        fillInputFieldPhone(driver.findElement(By.xpath(String.format(inputField, "phone"))), "9265658989");

        //Нажать «Далее»
        scrollToElementJs(driver.findElement(By.xpath("//button[@data-step='2']")));
        driver.findElement(By.xpath("//button[@data-step='2']")).click();

        //Проверить, что появилось сообщение именно у незаполненных полях – «Обязательное поле»
        scrollToElementJs(driver.findElement(By.xpath("//input[@data-name='series']")));
        checkMandatoryFields(driver.findElement(By.xpath("//input[@data-name='series']")), null);
        checkMandatoryFields(driver.findElement(By.xpath("//input[@data-name='number']")), null);
        checkMandatoryFields(driver.findElement(By.xpath("//input[@data-name='issueDate']")), null);
    }
}



