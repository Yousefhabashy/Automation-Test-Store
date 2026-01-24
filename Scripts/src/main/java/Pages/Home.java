package Pages;

import Base.PagesBase;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.*;
import java.util.NoSuchElementException;

public class Home extends PagesBase {
    public Home(WebDriver driver) {
        super(driver);
        PagesBase.driver = driver;
    }

    public List<WebElement> getAllProducts() {
        return driver.findElements(By.cssSelector("a.prdocutname"));
    }
    public WebElement getRandomProduct() {
        List<WebElement> products = getAllProducts();
        if(products== null || products.isEmpty()) {
            throw new RuntimeException("No products found!");
        }
        Random random = new Random();
        int randomIndex = random.nextInt(products.size());
        return products.get(randomIndex);
    }

    private List<WebElement> getAllProductsContainers() {
        return driver.findElements(By.cssSelector("div.col-md-3.col-sm-6.col-xs-12"));
    }
    public WebElement getRandomProductContainer() {
        List<WebElement> products = getAllProductsContainers();

        if (products.isEmpty()) {
            throw new NoSuchElementException("No products found on the page");
        }

        Random random = new Random();
        int randomIndex = random.nextInt(products.size());
        WebElement selectedProduct = products.get(randomIndex);

        // Scroll to product to ensure it's fully loaded
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", selectedProduct);

        return selectedProduct;
    }

    public String getProductTitle(WebElement productLink) {
        return Objects.requireNonNull(productLink.getAttribute("title"));
    }

    public String getHomeProductTitle(WebElement productElement) {
        WebElement productNameLink = productElement.findElement(By.cssSelector("a.prdocutname"));
        return productNameLink.getText().trim().toUpperCase();
    }
    public String getHomeProductPrice(WebElement productLink) {
        WebElement priceContainer = null;

        try {
            // Wait for price container to be present
            priceContainer = new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.presenceOfNestedElementLocatedBy(
                            productLink, By.cssSelector("div.price")
                    ));
        } catch (TimeoutException e) {
            throw new NoSuchElementException("Could not find div.price in product element");
        }
        try {
            WebElement salePrice = priceContainer.findElement(By.cssSelector("div.pricenew"));
            return salePrice.getText().trim();
        } catch (NoSuchElementException e) {
            // No sale price, continue to regular price
        }
        try {
            WebElement regularPrice = priceContainer.findElement(By.cssSelector("div.oneprice"));
            return regularPrice.getText().trim();
        } catch (NoSuchElementException e) {
            // No regular price either
        }
        String priceText = priceContainer.getText().trim();
        if (!priceText.isEmpty()) {
            return priceText;
        }
        throw new NoSuchElementException("Price container is empty - no pricenew, oneprice, or text found");
    }


    public void openProductPage(WebElement productElement) {
        WebElement productNameLink = productElement.findElement(By.cssSelector("a.prdocutname"));
        waitFor().until(ExpectedConditions.elementToBeClickable(productNameLink));
        clickElementJS(productNameLink);
    }

    @FindBy(id = "sort")
    public WebElement sortDropDown;
    public String getSelectedOption() {
        Select select = new Select(sortDropDown);
        return select.getFirstSelectedOption().getText();
    }

    // Sort A To Z
    public void selectAToZ() {
        Select select = new Select(sortDropDown);
        select.selectByVisibleText("Name A - Z");
    }
    public boolean isSortedAToZ() {
        List<WebElement> products  = getAllProducts();
        List<String> productsNames = new ArrayList<>();
        for (WebElement product : products) {
            productsNames.add(product.getText().toLowerCase());
        }

        List<String> sortedNames = new ArrayList<>(productsNames);
        Collections.sort(sortedNames);

        return productsNames.equals(sortedNames);
    }

    // Sort Z To A
    public void selectZToA() {
        Select select = new Select(sortDropDown);
        select.selectByVisibleText("Name Z - A");
    }

    public boolean isSortedZToA() {
        List<WebElement> products  = getAllProducts();
        List<String> productsNames = new ArrayList<>();
        for (WebElement product : products) {
            productsNames.add(product.getText().toLowerCase());
        }

        List<String> sortedNames = new ArrayList<>(productsNames);
        sortedNames.sort(Collections.reverseOrder(String.CASE_INSENSITIVE_ORDER));

        return productsNames.equals(sortedNames);
    }

    private List<Double> getAllProductPrices() {
        List<WebElement> prices = driver.findElements(By.cssSelector("div.onePrice"));
        List<Double> doublePrices =  new ArrayList<>();
        for (WebElement price : prices) {
            doublePrices.add(Double.parseDouble(price.getText().replace("$", "")));
        }
        return doublePrices;
    }

    // Sort High To Low
    public void selectPriceHighToLow() {
        Select select = new Select(sortDropDown);
        select.selectByVisibleText("Price High > Low");
    }
    public boolean isSortedHighToLow() {
        List<Double> prices = getAllProductPrices();

        List<Double> sortedPrices = new ArrayList<>(prices);
        Collections.sort(sortedPrices);
        return prices.equals(sortedPrices);
    }

    // Sort Low To High
    public void selectPriceLowToHigh() {
        Select select = new Select(sortDropDown);
        select.selectByVisibleText("Price Low > High");
    }
    public boolean isSortedLowToHigh() {
        List<Double> prices = getAllProductPrices();

        List<Double> reversedPrices = new ArrayList<>(prices);
        reversedPrices.sort(Collections.reverseOrder());
        return prices.equals(reversedPrices);
    }
}