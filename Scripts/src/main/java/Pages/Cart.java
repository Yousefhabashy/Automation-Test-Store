package Pages;

import Base.PagesBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class Cart extends PagesBase {
    public Cart(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "table.table.table-striped.table-bordered")
    public WebElement productContainer;
    private List<WebElement> getCartProducts() {
        List<WebElement> products = productContainer.findElements(By.tagName("tr"));
        products.removeFirst();
        return products;
    }

    public CartProduct getProduct(int productNumber) {
        int index = productNumber - 1;
        List<WebElement> products = getCartProducts();
        if(index >= 0 && index < products.size()) {
            return  new CartProduct(products.get(index));
        }
        throw  new IndexOutOfBoundsException("Product index " + index + " not found!");
    }

    public static class CartProduct {
        private WebElement product;
        public CartProduct(WebElement productElement) {
            this.product = productElement;
        }

        public String getProductTitle() {
            return product.findElement(By.cssSelector("td.align_left a")).getText();
        }
        private List<String> splitColorSize(WebElement element) {
            String text = element.getText().replace("- Color&Size ", "");
            return List.of(text.split("/"));
        }
        public String getProductSize() {
            WebElement sizeColor = product.findElement(By.cssSelector("td.align_left small"));
            List<String> list = splitColorSize(sizeColor);
            return list.getFirst();
        }
        public String getProductColor() {
            WebElement sizeColor = product.findElement(By.cssSelector("td.align_left small"));
            List<String> list = splitColorSize(sizeColor);
            return list.get(1);
        }
        public int getQuantity() {
            String value = product.findElement(By.cssSelector("input[type='text']")).getAttribute("value");
            assert value != null;
            return Integer.parseInt(value);
        }
        public void updateQuantity(int newQuantity) {
            WebElement quantityInput =  product.findElement(By.cssSelector("input[type='text']"));
            quantityInput.clear();
            setElementText(quantityInput, String.valueOf(newQuantity));
            WebElement cartUpdate = driver.findElement(By.id("cart_update"));
            clickElementJS(cartUpdate);
        }
        public void removeProduct() {
            WebElement remove = product.findElement(By.cssSelector("a.btn.btn-sm.btn-default"));
            waitFor().until(ExpectedConditions.elementToBeClickable(remove));
            clickElementJS(remove);
        }
        public String getUnitPrice() {
                 return product.findElements(By.cssSelector("td.align_right")).getFirst().getText();
        }
        public String getTotalPrice() {
            return product.findElements(By.cssSelector("td.align_right")).get(1).getText();
        }
    }
    @FindBy(id = "cart_checkout1")
    WebElement cartCheckout;
    public void openCheckout() {
        waitFor().until(ExpectedConditions.elementToBeClickable(cartCheckout));
        clickElementJS(cartCheckout);
    }
    @FindBy(css = "div.contentpanel")
    public WebElement emptyCartMessage;
    @FindBy(css = "a.btn.btn-default.mr10")
    WebElement continueShopping;
    public void continueShopping() {
        waitFor().until(ExpectedConditions.elementToBeClickable(continueShopping));
        clickElementJS(continueShopping);
    }
}
