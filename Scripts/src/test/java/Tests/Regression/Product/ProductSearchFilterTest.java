package Tests.Regression.Product;

import Base.TestBase;
import Header.HeaderComponents;
import Pages.Home;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProductSearchFilterTest extends TestBase {

    HeaderComponents header;
    Home home;

    @Test(priority = 1)
    public void filterSearch() {

        header = new HeaderComponents(driver);
        header.openSearchBar();
        String oldCategory = header.getSelectedCategory();
        header.SelectFragrance();
        String newCategory = header.getSelectedCategory();
        Assert.assertNotEquals(newCategory, oldCategory);
        header.searchProduct("gucci");
        waitFor().until(ExpectedConditions.urlContains("product/search&keyword=gucci"));
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("product/search&keyword=gucci"));

        home = new Home(driver);
        List<WebElement> products = home.getAllProducts();
        List<String> titles = new ArrayList<>();
        if(!products.isEmpty()) {
            for (WebElement product : products) {
                String text = product.getText().toLowerCase().trim();
                if (!text.isEmpty())
                    titles.add(text);
            }
        }
        if (!titles.isEmpty()) {
            for (String title : titles) {
                Assert.assertTrue(title.contains("gucci"));
            }
        }    }
}
