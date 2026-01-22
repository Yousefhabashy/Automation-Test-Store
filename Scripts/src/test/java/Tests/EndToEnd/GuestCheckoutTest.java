package Tests.EndToEnd;

import Base.TestBase;
import Data.TestData;
import Header.HeaderComponents;
import Pages.*;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Objects;

public class GuestCheckoutTest extends TestBase {

    HeaderComponents header;
    Home home;
    Product product;
    Cart cart;
    Guest guest;
    Checkout checkout;


    String productTitle;
    String productPrice;
    boolean hasSize;
    String productSize;
    int productQuantity;

    String orderID;

    String firstName = TestData.generateFirstName();
    String lastName = TestData.generateLastName();
    String email = TestData.generateEmail();
    String telephone = TestData.generateTelephoneNumber();
    String fax = TestData.generateFax();
    String address1 = TestData.generateAddress();
    String address2 = TestData.generateAddress();
    String company = TestData.generateCompany();
    String city = TestData.generateCity();
    String country ;
    String state ;
    String postalCode = TestData.generatePostalCode();

    @Test(priority = 1)
    public void openProductPage() {

        header = new HeaderComponents(driver);
        header.selectHome();
        waitFor().until(ExpectedConditions.urlContains("https://automationteststore.com/"));
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("https://automationteststore.com/"));

        home = new Home(driver);
        WebElement product = home.getRandomProduct();
        waitFor().until(ExpectedConditions.elementToBeClickable(product));
        productTitle = home.getProductTitle(product);
        product.click();

        waitFor().until(ExpectedConditions.urlContains("product/"));
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("product/"));
    }

    @Test(dependsOnMethods = {"openProductPage"})
    public void addProductToCart() {

        product = new Product(driver);
        Assert.assertEquals(product.getProductTitle(), productTitle);
        hasSize = product.hasSize();
        if(hasSize) {
            productSize = product.chooseSize("S");
        }
        productPrice = product.getPrice();
        productQuantity = product.getQuantity();
        product.addToCart();
        waitFor().until(ExpectedConditions.urlContains("checkout/cart"));
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("checkout/cart"));
    }

    @Test(dependsOnMethods = {"addProductToCart"})
    public void openGuestCheckout() {

        cart = new Cart(driver);
        cart.openCheckout();
        waitFor().until(ExpectedConditions.urlContains("account/login"));
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("account/login"));

        guest = new Guest(driver);
        guest.openGuestCheckout();

        waitFor().until(ExpectedConditions.urlContains("checkout/guest_step_1"));
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("checkout/guest_step_1"));
        String[] countryState =  guest.guestCheckout(firstName, lastName, email, telephone, fax,
                address1, company, address2, city, postalCode, false);

        country = countryState[0];
        state = countryState[1];
        waitFor().until(ExpectedConditions.urlContains("checkout/guest_step_3"));
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("checkout/guest_step_3"));
    }

    @Test(dependsOnMethods = {"openGuestCheckout"})
    public void checkoutProduct() {

        checkout = new Checkout(driver);
        checkout.confirmOrder();

        waitFor().until(ExpectedConditions.urlContains("checkout/success"));
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("checkout/success"));
        waitFor().until(ExpectedConditions.visibilityOf(checkout.successMessage));
        Assert.assertEquals(checkout.successMessage.getText().trim(), "YOUR ORDER HAS BEEN PROCESSED!");

        orderID = checkout.getOrderID();
    }
}
