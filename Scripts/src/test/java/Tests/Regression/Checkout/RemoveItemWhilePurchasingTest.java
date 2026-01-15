package Tests.Regression.Checkout;

import Base.TestBase;
import Data.TestData;
import Header.HeaderComponents;
import Pages.*;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Objects;

public class RemoveItemWhilePurchasingTest extends TestBase {

    HeaderComponents header;
    SignUp signUp;
    Home home;
    Product product;
    Cart cart;
    Checkout checkout;

    String product1Title;
    String product2Title;

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
    String loginName = TestData.generateLoginName(firstName, lastName);
    String password = TestData.generatePassword();

    @Test(priority = 1)
    public void signUpUser() {

        header = new Header.HeaderComponents(driver);
        header.openLoginOrRegister();
        waitFor().until(ExpectedConditions.urlContains("account/login"));
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("account/login"));

        signUp = new SignUp(driver);
        signUp.openRegisterPage();

        String[] countryState = signUp.registerNewUser(firstName, lastName, email, telephone,
                fax, address1, company, address2, city, postalCode,
                loginName, password, true
        );
        country = countryState[0];
        state = countryState[1];

        waitFor().until(ExpectedConditions.urlContains("account/success"));
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("account/success"));
        waitFor().until(ExpectedConditions.visibilityOf(signUp.successMessage));
        Assert.assertEquals(signUp.successMessage.getText().trim(), "YOUR ACCOUNT HAS BEEN CREATED!");
        isLoggedIn = true;
    }

    @Test(dependsOnMethods = {"signUpUser"})
    public void openProduct1Page() {

        header = new HeaderComponents(driver);
        header.selectMakeup();
        waitFor().until(ExpectedConditions.urlContains("category&path=36"));
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("category&path=36"));

        home = new Home(driver);
        WebElement product = home.getRandomProduct();
        product1Title = home.getProductTitle(product);
        product.click();

        waitFor().until(ExpectedConditions.urlContains("product/"));
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("product/"));
    }

    @Test(dependsOnMethods = {"openProduct1Page"})
    public void addProduct1ToCart() {

        product = new Product(driver);
        Assert.assertEquals(product.getProductTitle(), product1Title);

        product.addToCart();
        waitFor().until(ExpectedConditions.urlContains("checkout/cart"));
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("checkout/cart"));
    }

    @Test(dependsOnMethods = {"addProduct1ToCart"})
    public void openProduct2Page() {

        header = new HeaderComponents(driver);
        header.selectFragrance();
        waitFor().until(ExpectedConditions.urlContains("category&path=49"));
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("category&path=49"));

        home = new Home(driver);
        WebElement product = home.getRandomProduct();
        product2Title = home.getProductTitle(product);
        product.click();

        waitFor().until(ExpectedConditions.urlContains("product/"));
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("product/"));
    }

    @Test(dependsOnMethods = {"openProduct2Page"})
    public void addProduct2ToCart() {

        product = new Product(driver);
        Assert.assertEquals(product.getProductTitle(), product2Title);

        product.addToCart();
        waitFor().until(ExpectedConditions.urlContains("checkout/cart"));
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("checkout/cart"));
    }

    @Test(dependsOnMethods = {"addProduct2ToCart"})
    public void removeProduct() {

        cart = new Cart(driver);
        cart.openCheckout();

        waitFor().until(ExpectedConditions.urlContains("checkout/confirm"));
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("checkout/confirm"));

        checkout = new Checkout(driver);
        checkout.editCart();

        waitFor().until(ExpectedConditions.urlContains("checkout/cart"));
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("checkout/cart"));
        Cart.CartProduct product2 = cart.getProduct(2);
        product2.removeProduct();
    }

    @Test(dependsOnMethods = {"removeProduct"})
    public void checkoutProduct() {

        cart = new Cart(driver);
        cart.openCheckout();
        waitFor().until(ExpectedConditions.urlContains("checkout/confirm"));
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("checkout/confirm"));

        checkout = new Checkout(driver);
        checkout.confirmOrder();

        waitFor().until(ExpectedConditions.urlContains("checkout/success"));
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("checkout/success"));
        waitFor().until(ExpectedConditions.visibilityOf(checkout.successMessage));
        Assert.assertEquals(checkout.successMessage.getText().trim(), "YOUR ORDER HAS BEEN PROCESSED!");
    }
}
