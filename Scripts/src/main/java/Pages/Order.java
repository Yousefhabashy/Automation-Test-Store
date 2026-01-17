package Pages;

import Base.PagesBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Order extends PagesBase {
    public Order(WebDriver driver) {
        super(driver);
    }
    @FindBy(id = "button_edit")
    WebElement orderPage;
    public String openOrder() {
        waitFor().until(ExpectedConditions.elementToBeClickable(orderPage));
        String url = getOrderUrl();
        clickElementJS(orderPage);
        return url;
    }

    private String getOrderUrl() {
        WebElement orderIDDiv = driver.findElement(By.xpath("//*[@id=\"maincontainer\"]/div/div[1]/div/div/div[1]/div[1]"));
        return "order_id=" + orderIDDiv.getText().replaceAll("[^0-9]", "");
    }

    @FindBy(css = "table.table.table-striped.table-bordered")
    WebElement orderDetailsContainer;

    private Map<String, String> getOrderDetailsMap() {
        Map<String, String> data = new HashMap<>();

        WebElement td = orderDetailsContainer.findElements(By.tagName("td")).get(0);
        String fullText = td.getText();

        String[] lines = fullText.split("\n");

        for (int i = 0; i < lines.length - 1; i++) {
            String line = lines[i].trim();

            if (line.equals("Order ID")) {
                data.put("Order ID", lines[i + 1].trim());
            }
            if (line.equals("E-Mail")) {
                data.put("E-Mail", lines[i + 1].trim());
            }
            if (line.equals("Status")) {
                data.put("Status", lines[i + 1].trim());
            }
            if (line.equals("Shipping Method")) {
                data.put("Shipping Method", lines[i + 1].trim());
            }
            if (line.equals("Payment Method")) {
                data.put("Payment Method", lines[i + 1].trim());
            }
        }
        return data;
    }
    public String getOrderID() {
        return getOrderDetailsMap().get("Order ID");
    }

    public String getOrderEmail() {
        return getOrderDetailsMap().get("E-Mail");
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
            String value = product.findElements(By.tagName("td")).get(3).getText();
            return Integer.parseInt(value);
        }
        public String getUnitPrice() {
            return product.findElements(By.tagName("td")).get(4).getText();
        }
        public String getTotalPrice() {
            return product.findElements(By.tagName("td")).get(5).getText();
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

    // check order without login
    @FindBy(id = "CheckOrderFrm_order_id")
    WebElement orderIdBox;

    @FindBy(id = "CheckOrderFrm_email")
    WebElement emailBox;
    @FindBy(css = "button[title='Continue']")
    WebElement continueButton;

    public void checkOrder(String orderId, String email) {
        setElementText(orderIdBox, orderId);
        setElementText(emailBox, email);
        clickElementJS(continueButton);
    }
}
