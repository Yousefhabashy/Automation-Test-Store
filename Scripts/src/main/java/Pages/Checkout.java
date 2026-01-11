package Pages;

import Base.PagesBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.security.PublicKey;
import java.util.List;

public class Checkout extends PagesBase {
    public Checkout(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "table.table.confirm_shippment_options")
    WebElement shipDetails;

    public String getShipAddress() {
        WebElement addressBox = shipDetails.findElement(By.tagName("address"));
        return addressBox.getText();
    }
    @FindBy(css = "a.pull-right.mr10.btn.btn-default.btn-xs")
    WebElement editCartLink;
    public void editCart() {
        waitFor().until(ExpectedConditions.elementToBeClickable(editCartLink));
        clickElementJS(editCartLink);
    }
    public String getSubTotal() {
        return driver.findElements(By.cssSelector("span.cart_block_total")).getFirst().getText();
    }
    public String getFlatShipping() {
        return driver.findElements(By.cssSelector("span.cart_block_total")).get(1).getText();
    }
    public String getTotalPrice() {
        return driver.findElements(By.cssSelector("span.cart_block_total")).get(2).getText();
    }

    // Products
    @FindBy(css = "table.table.confirm_products")
    public WebElement productContainer;
    private List<WebElement> getCheckoutProducts() {
        return productContainer.findElements(By.tagName("tr"));
    }

    public CheckoutProduct getProduct(int productNumber) {
        int index = productNumber - 1;
        List<WebElement> products = getCheckoutProducts();
        if(index >= 0 && index < products.size()) {
            return  new Checkout.CheckoutProduct(products.get(index));
        }
        throw  new IndexOutOfBoundsException("Product index " + index + " not found!");
    }
    public static class CheckoutProduct {
        private WebElement product;

        public CheckoutProduct(WebElement productElement) {
            this.product = productElement;
        }

        public String getProductTitle() {
            return product.findElement(By.cssSelector("td a.checkout_heading")).getText();
        }

        public int getQuantity() {
            String value = product.findElements(By.tagName("td")).get(4).getText();
            return Integer.parseInt(value);
        }

        public String getUnitPrice() {
            return product.findElements(By.tagName("td")).get(3).getText();
        }

        public String getTotalPrice() {
            return product.findElements(By.tagName("td")).get(5).getText();
        }
    }

    @FindBy(id = "checkout_btn")
    WebElement confirmOrder;
    public void confirmOrder() {
        waitFor().until(ExpectedConditions.elementToBeClickable(confirmOrder));
        clickElementJS(confirmOrder);
    }

    @FindBy(id = "back")
    WebElement cancelOrder;
    public void cancelOrder() {
        waitFor().until(ExpectedConditions.elementToBeClickable(cancelOrder));
        clickElementJS(cancelOrder);
    }

    @FindBy(css = "span.maintext")
    public WebElement successMessage;

    public String getOrderID() {
        WebElement successfulOrderText = driver.findElement(By.cssSelector("div.contentpanel section"));
        WebElement orderId = successfulOrderText.findElements(By.tagName("p")).get(1);
        return "#" + orderId.getText().replaceAll("[^0-9]", "");
    }
    @FindBy(linkText = "invoice page")
    WebElement invoicePage;
    public void openInvoicePage() {
        waitFor().until(ExpectedConditions.elementToBeClickable(invoicePage));
        clickElementJS(invoicePage);
    }

    @FindBy(css = "a.btn.btn-default.mr10")
    WebElement home;
    public void openHome() {
        waitFor().until(ExpectedConditions.elementToBeClickable(home));
        clickElementJS(home);
    }
}
