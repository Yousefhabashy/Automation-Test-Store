package Pages;

import Base.PagesBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Contact extends PagesBase {
    public Contact(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "ContactUsFrm_first_name")
    WebElement firstNameBox;
    @FindBy(id = "ContactUsFrm_email")
    WebElement emailBox;
    @FindBy(id = "ContactUsFrm_enquiry")
    WebElement enquiryBox;
    @FindBy(css = "[title='Submit']")
    WebElement submitButton;

    public void contactUS(String firstName, String email, String enquiry) {

        setElementText(firstNameBox, firstName);
        setElementText(emailBox, email);
        setElementText(enquiryBox, enquiry);
        clickElementJS(submitButton);
    }

    @FindBy(xpath = "//*[@id=\"maincontainer\"]/div/div/div/div/section/p[2]")
    public WebElement successMessage;
}
