package Tests.Regression.Checkout;

import Base.TestBase;
import Data.TestData;
import Header.HeaderComponents;
import Pages.Cart;
import Pages.Home;
import Pages.SignUp;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Objects;

public class CheckoutEmptyCartTest extends TestBase {

    HeaderComponents header;
    SignUp signUp;
    Cart cart;

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
        System.out.println(country);
        System.out.println(state);

        waitFor().until(ExpectedConditions.urlContains("account/success"));
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("account/success"));
        waitFor().until(ExpectedConditions.visibilityOf(signUp.successMessage));
        Assert.assertEquals(signUp.successMessage.getText().trim(), "YOUR ACCOUNT HAS BEEN CREATED!");
        isLoggedIn = true;
    }

    @Test(dependsOnMethods = {"signUpUser"})
    public void checkOutEmptyCart() {

        header = new HeaderComponents(driver);
        header.openCart();
        waitFor().until(ExpectedConditions.urlContains("checkout/cart"));
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("checkout/cart"));

        cart = new Cart(driver);
        waitFor().until(ExpectedConditions.visibilityOf(cart.emptyCartMessage));
        Assert.assertEquals(cart.emptyCartMessage.getText(), "Your shopping cart is empty!\n" +
                "Continue");
    }
}
