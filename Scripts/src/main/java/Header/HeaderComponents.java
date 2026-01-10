package Header;

import Base.PagesBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HeaderComponents extends PagesBase {

    public HeaderComponents(WebDriver driver) {
        super(driver);
        PagesBase.driver = driver;
    }
    Actions actions = new Actions(driver);

    // Main Links
    @FindBy(linkText = "Login or register")
    WebElement LoginOrRegister;
    public void openLoginOrRegister() {
        waitFor().until(ExpectedConditions.elementToBeClickable(LoginOrRegister));
        clickElementJS(LoginOrRegister);
    }
    @FindBy(css = "a.top.menu_account")
    WebElement menuAccount;
    public void openAccount() {
        waitFor().until(ExpectedConditions.elementToBeClickable(menuAccount));
        clickElementJS(menuAccount);
    }
    @FindBy(css = "a.sub.menu_order")
    WebElement CheckOrder;
    public void openCheckOrder() {
        waitFor().until(ExpectedConditions.visibilityOf(menuAccount));
        actions.moveToElement(menuAccount).moveToElement(CheckOrder).click().perform();
    }
    @FindBy(css = "a.top.nobackground")
    WebElement Cart;
    public void openCart() {
        waitFor().until(ExpectedConditions.elementToBeClickable(searchBar));
        clickElementJS(Cart);
    }
    @FindBy(css = "a.top.menu_checkout")
    WebElement Checkout;
    public void openCheckout() {
        waitFor().until(ExpectedConditions.elementToBeClickable(Checkout));
        clickElementJS(Checkout);
    }

    @FindBy(id = "filter_keyword")
    WebElement searchBar;
    @FindBy(css = "div.button-in-search")
    WebElement searchButton;
    public void searchProduct(String productName) {
        setElementText(searchBar, productName);
        waitFor().until(ExpectedConditions.elementToBeClickable(searchBar));
        clickElementJS(searchButton);
    }

    // category menu
    @FindBy(linkText = "HOME")
    WebElement Home;
    public void selectHome() {
        waitFor().until(ExpectedConditions.elementToBeClickable(Home));
        clickElementJS(Home);
    }
    @FindBy(linkText = "APPAREL & ACCESSORIES")
    WebElement ApparelAndAccessories;
    public void selectApparelAndAccessories() {
        waitFor().until(ExpectedConditions.elementToBeClickable(ApparelAndAccessories));
        clickElementJS(ApparelAndAccessories);
    }
    @FindBy(linkText = "  MAKEUP")
    WebElement Makeup;
    public void selectMakeup() {
        waitFor().until(ExpectedConditions.elementToBeClickable(Makeup));
        clickElementJS(Makeup);
    }
    @FindBy(linkText = "  SKINCARE")
    WebElement   Skincare;
    public void selectSkincare() {
        waitFor().until(ExpectedConditions.elementToBeClickable(Skincare));
        clickElementJS(Skincare);
    }
    @FindBy(linkText = "  FRAGRANCE")
    WebElement   Fragrance;
    public void selectFragrance() {
        waitFor().until(ExpectedConditions.elementToBeClickable(Fragrance));
        clickElementJS(Fragrance);
    }
    @FindBy(linkText = "  HAIR CARE")
    WebElement   HairCare;
    public void selectHairCare() {
        waitFor().until(ExpectedConditions.elementToBeClickable(HairCare));
        clickElementJS(HairCare);
    }
    @FindBy(linkText = "  MEN")
    WebElement Men;
    public void selectMen() {
        waitFor().until(ExpectedConditions.elementToBeClickable(Men));
        clickElementJS(Men);
    }
    @FindBy(linkText = "  BOOKS")
    WebElement Books;
    public void selectBooks() {
        waitFor().until(ExpectedConditions.elementToBeClickable(Books));
        clickElementJS(Books);
    }

    // cart
    @FindBy(css = "span.label.label-orange.font14")
    public WebElement cartProductsNumber;
    public int getCartProductNumber() {
        waitFor().until(ExpectedConditions.visibilityOf(cartProductsNumber));
        String numberTxt = cartProductsNumber.getText();
        return Integer.parseInt(numberTxt);
    }

    // currency
    @FindBy(css = "a.dropdown-toggle")
    WebElement CurrentCurrency;
    @FindBy(linkText = "€ EURO")
    WebElement Euro;
    @FindBy(linkText = "£ POUND STERLING")
    WebElement PoundSterling;
    @FindBy(linkText = "$ US DOLLAR")
    WebElement USDollar;

    public void selectEuro() {
        actions.moveToElement(CurrentCurrency).moveToElement(Euro).click().perform();
    }
    public void selectPoundSterling() {
        actions.moveToElement(CurrentCurrency).moveToElement(PoundSterling).click().perform();
    }
    public void selectUSDollar() {
        actions.moveToElement(CurrentCurrency).moveToElement(USDollar).click().perform();
    }
}
