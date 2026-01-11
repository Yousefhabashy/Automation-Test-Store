package Pages;

import Base.PagesBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class Order extends PagesBase {
    public Order(WebDriver driver) {
        super(driver);
    }
    @FindBy(id = "button_edit")
    WebElement orderPage;
    public void openOrder() {
        waitFor().until(ExpectedConditions.elementToBeClickable(orderPage));
        clickElementJS(orderPage);
    }

    @FindBy(css = "table.table.table-striped.table-bordered")
    WebElement orderDetailsContainer;
    public String getOrderID() {
        WebElement orderId = orderDetailsContainer.findElement(
                By.xpath("//b[text()='Order ID']/following-sibling::text()[1]")
        );
        return orderId.getText().trim();
    }
    public String getOrderEmail() {
        WebElement email = orderDetailsContainer.findElement(
                By.xpath("//b[text()='E-Mail']/following-sibling::text()[1]")
        );
        return email.getText().trim();
    }

    @FindBy(css = "table.invoice_products.table.table-striped.table-bordered")
    WebElement orderProductsContainer;
    private List<WebElement> getOrderProducts() {
        List<WebElement> products = orderProductsContainer.findElements(By.tagName("tr"));
        products.removeFirst();
        return products;
    }

    public OrderProduct getProduct(int productNumber) {
        int index = productNumber - 1;
        List<WebElement> products = getOrderProducts();
        if(index >= 0 && index < products.size()) {
            return  new Order.OrderProduct(products.get(index));
        }
        throw  new IndexOutOfBoundsException("Product index " + index + " not found!");
    }

    public static class OrderProduct {
        private WebElement product;
        public OrderProduct(WebElement productElement) {
            this.product = productElement;
        }

        public String getProductTitle() {
            return product.findElement(By.cssSelector("td.align_left.valign_top a")).getText();
        }
        public int getQuantity() {
            String value = product.findElements(By.tagName("td")).get(4).getText();
            return Integer.parseInt(value);
        }
        public String getUnitPrice() {
            return product.findElements(By.tagName("td")).get(5).getText();
        }
        public String getTotalPrice() {
            return product.findElements(By.tagName("td")).get(6).getText();
        }
    }
    @FindBy(css = "table.table.table-striped.table-bordered")
    WebElement orderPricesContainer;
    private List<WebElement> getOrderPrices() {
        return orderPricesContainer.findElements(By.tagName("tr"));
    }
    public String getOrderSubTotalPrice() {
        List<WebElement> prices = getOrderPrices();
        return prices.getFirst().findElements(By.tagName("td")).getLast().getText();
    }
    public String getOrderFlatShippingPrice() {
        List<WebElement> prices = getOrderPrices();
        return prices.get(1).findElements(By.tagName("td")).getLast().getText();
    }
    public String getOrderTotalPrice() {
        List<WebElement> prices = getOrderPrices();
        return prices.get(2).findElements(By.tagName("td")).getLast().getText();
    }

    @FindBy(css = "a.btn.btn-orange.mr10.mb10.pull-right")
    WebElement printOrder;
    public void printOrder() {
        waitFor().until(ExpectedConditions.elementToBeClickable(printOrder));
        clickElementJS(printOrder);
    }
    @FindBy(css = "a.btn.btn-default.mr10.mb20")
    WebElement backAccountHistory;
    public void backAccountHistory() {
        waitFor().until(ExpectedConditions.elementToBeClickable(backAccountHistory));
        clickElementJS(backAccountHistory);
    }
}
