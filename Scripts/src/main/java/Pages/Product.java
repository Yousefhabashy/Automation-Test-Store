package Pages;

import Base.PagesBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Product extends PagesBase {
    public Product(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "h1.productname")
    WebElement productTitle;
    @FindBy(css = "select.form-control ")
    WebElement selectSize;

}
