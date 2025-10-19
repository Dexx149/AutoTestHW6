import helpers.DataHelper;
import pages.LoginPage;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Test;

import static helpers.DataHelper.getAuthInfo;
import static com.codeborne.selenide.Selenide.$;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyTransferTest {

    @Test
    void shouldTransferMoneyBetweenOwnCards(){
        var info = getAuthInfo();
        var verificationCode = DataHelper.getVerificationCodeFor(info);
        var firstCard = DataHelper.getFirstCardInfo();
        var secondCard = DataHelper.getSecondCardInfo();

        var loginPage = Selenide.open("http://localhost:9999", LoginPage.class);
        var verificationPage = loginPage.validLogin(info);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var firstCardBalance = dashboardPage.getCardBalance(firstCard);
        var secondCardBalance = dashboardPage.getCardBalance(secondCard);
        var amount = DataHelper.calculateAmount(secondCardBalance);
        var transferPage = dashboardPage.selectCard(firstCard);

        dashboardPage = transferPage.makeValidTransfer(String.valueOf(amount),secondCard.getNumber());
        var newFirstCardBalance = dashboardPage.getCardBalance(firstCard);
        var newSecondCardBalance = dashboardPage.getCardBalance(secondCard);

        assertEquals(firstCardBalance + amount, newFirstCardBalance);
        assertEquals(secondCardBalance - amount, newSecondCardBalance);

    }
}
