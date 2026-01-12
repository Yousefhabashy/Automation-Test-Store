package Pages;

import Base.PagesBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class SignIn extends PagesBase {
    public SignIn(WebDriver driver) {
        super(driver);
    }


    @FindBy(id = "loginFrm_loginname")
    WebElement loginNameBox;
    @FindBy(id = "loginFrm_password")
    WebElement passwordBox;
    @FindBy(css = "[title='Login'")
    WebElement loginButton;
    public void loginUser(String loginName, String password) {
        setElementText(loginNameBox, loginName);
        setElementText(passwordBox, password);
        clickElementJS(loginButton);
    }

    // forgot password
    @FindBy(linkText = "Forgot your password?")
    WebElement forgotPassword;
    public void openForgetPassword() {
        clickElementJS(forgotPassword);
    }
    @FindBy(id = "forgottenFrm_loginname")
    WebElement forgotLoginNameBox;
    @FindBy(id = "forgottenFrm_email")
    WebElement forgotEmailBox;
    @FindBy(css = "button.btn.btn-orange.pull-right.lock-on-click")
    WebElement continueButton;
    public void fillForgotPassword(String loginName, String email) {
        setElementText(forgotLoginNameBox, loginName);
        setElementText(forgotEmailBox, email);
        clickElementJS(continueButton);
    }

    // forgot login
    @FindBy(linkText = "Forgot your login?")
    WebElement forgotLogin;
    public void openForgetLogin() {
        clickElementJS(forgotLogin);
    }
    @FindBy(id = "forgottenFrm_lastname")
    WebElement forgotLastNameBox;
    public void fillForgotLogin(String lastName, String email) {
        setElementText(forgotLastNameBox, lastName);
        setElementText(forgotEmailBox, email);
        clickElementJS(continueButton);
    }
}
