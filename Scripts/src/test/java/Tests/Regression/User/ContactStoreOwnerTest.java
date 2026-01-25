package Tests.Regression.User;

import Base.TestBase;
import Data.TestData;
import Header.HeaderComponents;
import Pages.Account;
import Pages.Contact;
import Pages.SignUp;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Objects;


public class ContactStoreOwnerTest extends TestBase {

    HeaderComponents header;
    SignUp signUp;
    Account account;
    Contact contact;

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

    String enquiry = TestData.generateRate();



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
    public void contactUS() {

        header = new HeaderComponents(driver);
        header.openAccount();
        waitFor().until(ExpectedConditions.urlContains("account/account"));
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("account/account"));

        account = new Account(driver);
        account.openContactUs();
        waitFor().until(ExpectedConditions.urlContains("content/contact"));
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("content/contact"));

        contact = new Contact(driver);
        contact.contactUS(firstName, email, enquiry);

        waitFor().until(ExpectedConditions.visibilityOf(contact.successMessage));
        Assert.assertEquals(contact.successMessage.getText(), "Your enquiry has been successfully sent to the store owner!");
    }
}
