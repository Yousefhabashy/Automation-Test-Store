package Tests.Regression.Product;

import Base.TestBase;
import Data.TestData;
import Header.HeaderComponents;
import Pages.Home;
import Pages.Product;
import Pages.SignUp;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Objects;

public class AddProductReviewTest extends TestBase {
    Header.HeaderComponents header;
    SignUp signUp;
    Home home;
    Product product;

    String productTitle;

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

    String review = TestData.generateReview();
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

        waitFor().until(ExpectedConditions.urlMatches("https://automationteststore.com/"));
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
    public void checkRightProductOpen() {

        product = new Product(driver);
        Assert.assertEquals(product.getProductTitle(), productTitle);
    }
    @Test(dependsOnMethods = {"checkRightProductOpen"})
    public void addProductReview() throws InterruptedException {

        product = new Product(driver);
        product.openReview();
        product.addReview(4, loginName, review);

        waitFor().until(ExpectedConditions.visibilityOf(product.reviewSuccess));
        Assert.assertEquals(product.reviewSuccess.getText().trim().replace("×\n", ""),
                "Thank you for your review. It has been submitted to the webmaster for approval." );
    }
}
