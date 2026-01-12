package Tests.Regression.User;

import Base.TestBase;
import Header.HeaderComponents;
import Pages.SignIn;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Objects;

public class SignInUserTest extends TestBase {

    HeaderComponents header;
    SignIn signIn;

    String loginNam = "WinfredZboncak008";
    String password = "2ne8oc1dsqqzw6";

    @Test(priority = 1)
    public void signInUser() {

        header = new HeaderComponents(driver);
        header.openLoginOrRegister();

        waitFor().until(ExpectedConditions.urlContains("account/login"));
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("account/login"));

        signIn = new SignIn(driver);
        signIn.loginUser(loginNam, password);

        waitFor().until(ExpectedConditions.urlContains("account/account"));
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("account/account"));
        isLoggedIn = true;
    }
}
