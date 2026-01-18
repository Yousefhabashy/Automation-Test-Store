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

public class CheckOrderTest extends TestBase {

    HeaderComponents header;
    SignUp signUp;
    Home home;
    Product product;
    Cart cart;
    Checkout checkout;
    Account account;
    Order order;

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

    @Test(dependsOnMethods = {"checkoutProduct"})
    public void logoutUser() {

        header = new HeaderComponents(driver);
        header.openAccount();
        waitFor().until(ExpectedConditions.urlContains("account/account"));
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("account/account"));

        account = new Account(driver);
        account.logoutUser();
        waitFor().until(ExpectedConditions.visibilityOf(account.mainText));
        Assert.assertEquals(account.mainText.getText(), "ACCOUNT LOGOUT");
        isLoggedIn = false;
    }

    @Test(dependsOnMethods = {"logoutUser"})
    public void checkOrder() {

        header = new HeaderComponents(driver);
        header.openCheckOrder();
        waitFor().until(ExpectedConditions.urlContains("account/invoice"));
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("account/invoice"));

        order = new Order(driver);
        String orderNum = orderID.replace("#", "");
        order.checkOrder(orderNum, email);

        String orderId = order.getOrderID();
        String orderEmail = order.getOrderEmail();

        Assert.assertEquals(orderId, orderID);
        Assert.assertEquals(orderEmail, email);

        Order.OrderProduct product = order.getProduct(1);
        String orderProductName = product.getProductTitle();
        int orderProductQuantity = product.getQuantity();

        Assert.assertEquals(orderProductName, productTitle);
        Assert.assertEquals(orderProductQuantity, productQuantity);
    }
}
