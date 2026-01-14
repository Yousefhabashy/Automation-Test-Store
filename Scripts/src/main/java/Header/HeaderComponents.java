package Header;

import Base.PagesBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HeaderComponents extends PagesBase {

    public HeaderComponents(WebDriver driver) {
        super(driver);
        PagesBase.driver = driver;
        this.actions = new Actions(driver);
    }
    Actions actions;

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

    // Search
    @FindBy(id = "filter_keyword")
    WebElement searchBar;
    @FindBy(css = "div.button-in-search")
    WebElement searchButton;
    public void searchProduct(String productName) {
        waitFor().until(ExpectedConditions.visibilityOf(searchBar));
        waitFor().until(ExpectedConditions.elementToBeClickable(searchBar));
        setElementText(searchBar, productName);
        waitFor().until(ExpectedConditions.elementToBeClickable(searchBar));
        clickElementJS(searchButton);
    }

    public void openSearchBar() {
        searchBar.click();
    }
    @FindBy(id = "search-category")
    WebElement searchCategory;
    public void SelectAllCategories() {
        WebElement allCategories = searchCategory.findElement(By.id("category_0"));
        clickElementJS(allCategories);
    }
    public void SelectApparelAccessories() {
        WebElement apparelAndAccessoriess = searchCategory.findElement(By.id("category_68"));
        clickElementJS(apparelAndAccessoriess);
    }
    public void SelectMakeup() {
        WebElement makeup = searchCategory.findElement(By.id("category_36"));
        clickElementJS(makeup);
    }
    public void SelectSkincare() {
        WebElement skincare = searchCategory.findElement(By.id("category_43"));
        clickElementJS(skincare);
    }
    public void SelectFragrance() {
        WebElement Fragrance = searchCategory.findElement(By.id("category_49"));
        clickElementJS(Fragrance);
    }
    public void SelectMen() {
        WebElement men = searchCategory.findElement(By.id("category_58"));
        clickElementJS(men);
    }
    public void SelectHairCare() {
        WebElement HairCare = searchCategory.findElement(By.id("category_52"));
        clickElementJS(HairCare);
    }
    public void SelectBooks() {
        WebElement books = searchCategory.findElement(By.id("category_65"));
        clickElementJS(books);
    }

    public String getSelectedCategory() {
        searchBar.click();
        WebElement selectedCategory = driver.findElement(By.id("category_selected"));
        return selectedCategory.getText();
    }

    // category links
    public void selectHome() {
        WebElement HomeLink = driver.findElement(By.xpath("//*[@id=\"categorymenu\"]/nav/ul/li[1]/a"));
        clickElementJS(HomeLink);
    }
    public void selectApparelAndAccessories() {
        WebElement ApparelAndAccessoriesLink = driver.findElement(By.xpath("//*[@id=\"categorymenu\"]/nav/ul/li[2]/a"));
        clickElementJS(ApparelAndAccessoriesLink);
    }
    public void selectMakeup() {
        WebElement MakeupLink = driver.findElement(By.xpath("//*[@id=\"categorymenu\"]/nav/ul/li[3]/a"));
        clickElementJS(MakeupLink);
    }
    public void selectSkincare() {
        WebElement skincareLink = driver.findElement(By.xpath("//*[@id=\"categorymenu\"]/nav/ul/li[4]/a"));
        clickElementJS(skincareLink);
    }
    public void selectFragrance() {
        WebElement fragranceLink = driver.findElement(By.xpath("//*[@id=\"categorymenu\"]/nav/ul/li[5]/a"));
        clickElementJS(fragranceLink);
    }
    public void selectHairCare() {
        WebElement HairCareLink = driver.findElement(By.xpath("//*[@id=\"categorymenu\"]/nav/ul/li[6]/a"));
        clickElementJS(HairCareLink);
    }
    public void selectMen() {
        WebElement MenLink = driver.findElement(By.xpath("//*[@id=\"categorymenu\"]/nav/ul/li[7]/a"));
        clickElementJS(MenLink);
    }
    public void selectBooks() {
        WebElement BooksLink = driver.findElement(By.xpath("//*[@id=\"categorymenu\"]/nav/ul/li[8]/a"));
        clickElementJS(BooksLink);
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
