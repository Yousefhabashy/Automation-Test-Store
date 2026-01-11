package Pages;

import Base.PagesBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class SignUp extends PagesBase {
    public SignUp(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "[title='Continue'")
    WebElement registerButton;
    public void openRegisterPage() {
        waitFor().until(ExpectedConditions.visibilityOf(registerButton));
        waitFor().until(ExpectedConditions.elementToBeClickable(registerButton));
        clickElementJS(registerButton);
    }

    @FindBy(linkText = "login page")
    WebElement loginPage;
    public void openLoginPage() {
        clickElementJS(loginPage);
    }

    @FindBy(id = "AccountFrm_firstname")
    WebElement firstNameBox;
    @FindBy(id = "AccountFrm_lastname")
    WebElement lastNameBox;
    @FindBy(id = "AccountFrm_email")
    WebElement emailBox;
    @FindBy(id = "AccountFrm_telephone")
    WebElement telephoneBox;
    @FindBy(id = "AccountFrm_fax")
    WebElement faxBox;
    @FindBy(id = "AccountFrm_company")
    WebElement companyBox;
    @FindBy(id = "AccountFrm_address_1")
    WebElement address1Box;
    @FindBy(id = "AccountFrm_address_2")
    WebElement address2Box;
    @FindBy(id = "AccountFrm_city")
    WebElement cityBox;
    @FindBy(id = "AccountFrm_zone_id")
    WebElement stateSelect;
    @FindBy(id = "AccountFrm_postcode")
    WebElement postalCodeBox;
    @FindBy(id = "AccountFrm_country_id")
    WebElement countrySelect;
    @FindBy(id = "AccountFrm_loginname")
    WebElement loginNameBox;
    @FindBy(id = "AccountFrm_password")
    WebElement passwordBox;
    @FindBy(id = "AccountFrm_confirm")
    WebElement confirmPasswordBox;
    @FindBy(id = "AccountFrm_newsletter1")
    WebElement subscribeNewsletter;
    @FindBy(id = "AccountFrm_newsletter0")
    WebElement notSubscribeNewsletter;
    @FindBy(id = "AccountFrm_agree")
    WebElement acceptPolicy;
    @FindBy(css = "button.btn.btn-orange.pull-right.lock-on-click")
    WebElement continueButton;

    public void registerNewUser(String firstName, String lastName, String email,
                                String telephone, String fax, String address1, String company,
                                String address2, String city, String state, String postalCode,
                                String countryName, String loginName, String password , boolean subscribe) {
        setElementText(firstNameBox, firstName);
        setElementText(lastNameBox, lastName);
        setElementText(emailBox, email);
        setElementText(telephoneBox, telephone);
        setElementText(faxBox, fax);
        setElementText(companyBox, company);
        setElementText(address1Box, address1);
        setElementText(address2Box, address2);
        setElementText(cityBox, city);
        Select selectState = new Select(stateSelect);
        selectState.deSelectByContainsVisibleText(state);
        setElementText(postalCodeBox, postalCode);
        Select selectCountry = new Select(countrySelect);
        selectCountry.deSelectByContainsVisibleText(countryName);
        setElementText(loginNameBox, loginName);
        setElementText(passwordBox, password);
        setElementText(confirmPasswordBox, password);
        if(subscribe) {
            clickElementJS(subscribeNewsletter);
        } else {
            clickElementJS(notSubscribeNewsletter);
        }
        clickElementJS(acceptPolicy);
        clickElementJS(continueButton);
    }

    public void registerNewUser(String firstName, String lastName, String email,
                                String address1, String city, String state, String postalCode,
                                String countryName, String loginName, String password) {
        setElementText(firstNameBox, firstName);
        setElementText(lastNameBox, lastName);
        setElementText(emailBox, email);
        setElementText(address1Box, address1);
        setElementText(cityBox, city);
        Select selectState = new Select(stateSelect);
        selectState.deSelectByContainsVisibleText(state);
        setElementText(postalCodeBox, postalCode);
        Select selectCountry = new Select(countrySelect);
        selectCountry.deSelectByContainsVisibleText(countryName);
        setElementText(loginNameBox, loginName);
        setElementText(passwordBox, password);
        setElementText(confirmPasswordBox, password);
        clickElementJS(acceptPolicy);
        clickElementJS(continueButton);
    }
}
