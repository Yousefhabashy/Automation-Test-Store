package Tests.Regression.Product;

import Base.TestBase;
import Pages.Home;
import Pages.Product;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Objects;

public class AddProductToCartTest extends TestBase {

    Home home;
    Product product;

    String productTitle;

    @Test(priority = 1)
    public void openProductPage() {

        home = new Home(driver);
        WebElement product = home.getRandomProduct();
        waitFor().until(ExpectedConditions.elementToBeClickable(product));
        productTitle = home.getProductTitle(product);
        product.click();

        waitFor().until(ExpectedConditions.urlContains("product/"));
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("product/"));
    }

    @Test(dependsOnMethods = {"openProductPage"})
    public void checkRightProductOpen() {

        product = new Product(driver);
        Assert.assertEquals(product.getProductTitle(), productTitle);
    }
    @Test(dependsOnMethods = {"checkRightProductOpen"})
    public void addProductToCart() {

        product = new Product(driver);
        if(product.hasSize()) {
            String productSize = product.chooseSize("S");
        }
        product.addToCart();
        waitFor().until(ExpectedConditions.urlContains("checkout/cart"));
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("checkout/cart"));
    }
}
