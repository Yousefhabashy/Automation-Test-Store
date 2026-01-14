package Tests.Regression.Product;

import Base.TestBase;
import Header.HeaderComponents;
import Pages.Home;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProductSearchTest extends TestBase {

    HeaderComponents header;
    Home home;

    @Test(priority = 1)
    public void searchProduct() {
        header = new HeaderComponents(driver);

        header.searchProduct("shirt");
        waitFor().until(ExpectedConditions.urlContains("product/search&keyword=shirt"));
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("product/search&keyword=shirt"));

        home = new Home(driver);
        List<WebElement> shirts = home.getAllProducts();
        List<String> titles = new ArrayList<>();
        if(!shirts.isEmpty()) {
            for (WebElement shirt : shirts) {
                String text = shirt.getText().toLowerCase().trim();
                if (!text.isEmpty())
                    titles.add(text);
            }
        }
        if (!titles.isEmpty()) {
            for (String title : titles) {
                Assert.assertTrue(title.contains("shirt"));
            }
        }
    }
}
