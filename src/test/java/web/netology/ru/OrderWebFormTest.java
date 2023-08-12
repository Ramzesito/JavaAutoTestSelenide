package web.netology.ru;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class OrderWebFormTest {

    private String generateDate(int addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    public void shouldTestOrderForm() {

        String curDate = generateDate(4, "dd.MM.YYYY");
        open("http://localhost:9999");
        $(By.cssSelector("[data-test-id='city'] input")).setValue("Калуга");
        $(By.cssSelector("[data-test-id='date'] input")).sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE); // очистка поля ввода
        $(By.cssSelector("[data-test-id='date'] input")).sendKeys(curDate);
        $(By.cssSelector("[data-test-id='name'] input")).setValue("Сергеев Сергей");
        $(By.cssSelector("[data-test-id='phone'] input")).setValue("+79876543210");
        $(By.cssSelector("[data-test-id='agreement']")).click();
        $(By.cssSelector("button.button")).click();
        $(".notification__content").
                shouldBe(Condition.visible, Duration.ofSeconds(15)).
                shouldHave(Condition.exactText("Встреча успешно забронирована на " + curDate));

    }
}
