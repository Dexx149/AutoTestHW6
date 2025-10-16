package Pages;

import Helpers.DataHelper;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

 public class DashboardPage {
    private final SelenideElement header = $("[data-test-id=dashboard]");
    private ElementsCollection cards = $$(".list__item div");
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";

    public DashboardPage() { header.should(Condition.visible);}

    private int extractBalance(String text) {
        var start = text.indexOf(balanceStart);
        var finish = text.indexOf(balanceFinish);
        var value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }

    private SelenideElement getCard(DataHelper.CardInfo cardInfo){
        return cards.find(Condition.attribute("data-test-id",cardInfo.getTestId()));
    }

    public int getCardBalance(DataHelper.CardInfo cardInfo){
        var text = cards.find(Condition.attribute("data-test-id",cardInfo.getTestId())).getText();
        return extractBalance(text);
    }

    public TransferPage selectCard(DataHelper.CardInfo cardInfo){
        getCard(cardInfo).$("button").click();
        return new TransferPage();
    }
}
