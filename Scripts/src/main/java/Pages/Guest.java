package Pages;

import Base.PagesBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.Random;

public class Guest extends PagesBase {
    public Guest(WebDriver driver) {
        super(driver);
    }

    // select
    private static String getRandomChoice(WebElement selectElement) {
        Select select = new Select(selectElement);
        List<WebElement> options = select.getOptions();
        options.removeIf( option ->
                option.getText().trim().isEmpty()
                        || option.getText().toLowerCase().contains("please select")
        );
        Random random = new Random();
        WebElement randomOption = options.get(random.nextInt(options.size()));

        select.selectByVisibleText(randomOption.getText());
        return randomOption.getText();
    }
    @FindBy(id = "accountFrm_accountguest")
    WebElement guestCheckout;
    @FindBy(css = "[title='Continue']")
    WebElement checkoutButton;
    public void openGuestCheckout() {
        waitFor().until(ExpectedConditions.elementToBeClickable( guestCheckout));
        clickElementJS(guestCheckout);
        waitFor().until(ExpectedConditions.elementToBeClickable( checkoutButton));
        clickElementJS(checkoutButton);
    }

    // guest checkout data
    @FindBy(id = "guestFrm_firstname")
    WebElement firstNameBox;
    @FindBy(id = "guestFrm_lastname")
    WebElement lastNameBox;
    @FindBy(id = "guestFrm_email")
    WebElement emailBox;
    @FindBy(id = "guestFrm_telephone")
    WebElement telephoneBox;
    @FindBy(id = "guestFrm_fax")
    WebElement faxBox;
    @FindBy(id = "guestFrm_company")
    WebElement companyBox;
    @FindBy(id = "guestFrm_address_1")
    WebElement address1Box;
    @FindBy(id = "guestFrm_address_2")
    WebElement address2Box;
    @FindBy(id = "guestFrm_city")
    WebElement cityBox;
    @FindBy(id = "guestFrm_zone_id")
    public WebElement stateSelect;
    @FindBy(id = "guestFrm_postcode")
    WebElement postalCodeBox;
    @FindBy(id = "guestFrm_country_id")
    public WebElement countrySelect;
    @FindBy(id = "guestFrm_shipping_indicator")
    WebElement separateShippingAddressBox;
    @FindBy(css = "button.btn.btn-orange.pull-right.lock-on-click")
    WebElement continueButton;

    public String[] guestCheckout(String firstName, String lastName, String email,
                                    String telephone, String fax, String address1, String company,
                                    String address2, String city, String postalCode, boolean separateShippingAddress) {

        setElementText(firstNameBox, firstName);
        setElementText(lastNameBox, lastName);
        setElementText(emailBox, email);
        setElementText(telephoneBox, telephone);
        setElementText(faxBox, fax);
        setElementText(companyBox, company);
        setElementText(address1Box, address1);
        setElementText(address2Box, address2);
        setElementText(cityBox, city);
        String countryName = getRandomChoice(countrySelect);
        setElementText(postalCodeBox, postalCode);
        String stateName = getRandomChoice(stateSelect);

        if(separateShippingAddress) {
            clickElementJS(separateShippingAddressBox);
        }
        clickElementJS(continueButton);
        return new String[]{countryName, stateName};
    }
}
