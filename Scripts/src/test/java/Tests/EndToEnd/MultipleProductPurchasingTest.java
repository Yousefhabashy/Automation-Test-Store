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

public class MultipleProductPurchasingTest extends TestBase {

    HeaderComponents header;
    SignUp signUp;
    Home home;
    Product product;
    Cart cart;
    Checkout checkout;
    Account account;
    Order order;

    String product1Title;
    String product1Price;
    int product1Quantity;

    String product2Title;
    String product2Price;
    int product2Quantity;

    String product3Title;
    String product3Price;
    int product3Quantity;


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
    String country;
    String state;
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
    public void openProductPage() {

        header = new HeaderComponents(driver);
        header.selectFragrance();
        waitFor().until(ExpectedConditions.urlContains("category&path=49"));
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("category&path=49"));

        home = new Home(driver);
        WebElement product = home.getRandomProduct();
        waitFor().until(ExpectedConditions.elementToBeClickable(product));
        product1Title = home.getProductTitle(product);
        product.click();

        waitFor().until(ExpectedConditions.urlContains("product/"));
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("product/"));
    }

    @Test(dependsOnMethods = {"openProductPage"})
    public void addProductToCart() {

        product = new Product(driver);
        Assert.assertEquals(product.getProductTitle(), product1Title);
        product1Price = product.getPrice();
        product1Quantity = product.getQuantity();
        product.addToCart();
        waitFor().until(ExpectedConditions.urlContains("checkout/cart"));
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("checkout/cart"));
    }

    @Test(dependsOnMethods = {"addProductToCart"})
    public void openProduct2Page() {

        header = new HeaderComponents(driver);
        header.selectSkincare();
        waitFor().until(ExpectedConditions.urlContains("product/category&path=43"));
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("product/category&path=43"));

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
        product2Price = product.getPrice();
        product2Quantity = product.getQuantity();
        product.addToCart();
        waitFor().until(ExpectedConditions.urlContains("checkout/cart"));
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("checkout/cart"));
    }

    @Test(dependsOnMethods = {"addProduct2ToCart"})
    public void openProduct3Page() {

        header = new HeaderComponents(driver);
        header.selectHairCare();
        waitFor().until(ExpectedConditions.urlContains("product/category&path=52"));
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("product/category&path=52"));

        home = new Home(driver);
        WebElement product = home.getRandomProduct();
        waitFor().until(ExpectedConditions.elementToBeClickable(product));
        product3Title = home.getProductTitle(product);
        product.click();

        waitFor().until(ExpectedConditions.urlContains("product/"));
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("product/"));
    }

    @Test(dependsOnMethods = {"openProduct3Page"})
    public void addProduct3ToCart() {

        product = new Product(driver);
        Assert.assertEquals(product.getProductTitle(), product3Title);
        product3Price = product.getPrice();
        product3Quantity = product.getQuantity();
        product.addToCart();
        waitFor().until(ExpectedConditions.urlContains("checkout/cart"));
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("checkout/cart"));
    }

    @Test(dependsOnMethods = {"addProduct3ToCart"})
    public void checkCartProducts() {

        cart = new Cart(driver);
        Cart.CartProduct product1 = cart.getProduct(1);
        Cart.CartProduct product2 = cart.getProduct(2);
        Cart.CartProduct product3 = cart.getProduct(3);

        String cartProduct1Title = product1.getProductTitle();
        String cartProduct2Title = product2.getProductTitle();
        String cartProduct3Title = product3.getProductTitle();
        String cartProduct1Price = product1.getUnitPrice();
        String cartProduct2Price = product2.getUnitPrice();
        String cartProduct3Price = product3.getUnitPrice();
        int cartProduct1Quantity = product1.getQuantity();
        int cartProduct2Quantity = product2.getQuantity();
        int cartProduct3Quantity = product3.getQuantity();

        Assert.assertEquals(cartProduct1Title, product1Title);
        Assert.assertEquals(cartProduct1Price, product1Price);
        Assert.assertEquals(cartProduct1Quantity, product1Quantity);
        Assert.assertEquals(cartProduct2Title, product2Title);
        Assert.assertEquals(cartProduct2Price, product2Price);
        Assert.assertEquals(cartProduct2Quantity, product2Quantity);
        Assert.assertEquals(cartProduct3Title, product3Title);
        Assert.assertEquals(cartProduct3Price, product3Price);
        Assert.assertEquals(cartProduct3Quantity, product3Quantity);
    }

    @Test(dependsOnMethods = {"checkCartProducts"})
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

        orderID = checkout.getOrderID();
    }
}
