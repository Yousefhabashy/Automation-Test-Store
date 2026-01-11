package Pages;

import Base.PagesBase;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Product extends PagesBase {
    public Product(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "h1.productname")
    WebElement productTitle;
    @FindBy(css = "select.form-control ")
    WebElement selectSize;

    public String chooseSize(String size) {
        Select select = new Select(selectSize);
        List<WebElement> options = select.getOptions();
        options.removeIf(option ->
                option.getText().trim().isEmpty()
        );
        try {
            for (WebElement option : options) {
                String text = option.getText().trim();

                if (text.toLowerCase().startsWith(size.toLowerCase())) {
                    select.selectByVisibleText(text);
                    return text;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("No size starts with: " + size);
        }
        return "No size starts with: " + size;
    }

    public String getColor(String optionText) {
        if (!optionText.contains("/")) {
            throw new RuntimeException("Invalid option format: " + optionText);
        }
        String afterSlash = optionText.split("/")[1].trim();
        if (afterSlash.contains("$")) {
            return afterSlash.split("\\$")[0].trim();
        }
        if (afterSlash.contains("(")) {
            return afterSlash.split("\\(")[0].trim();
        }
        // fallback
        return afterSlash.trim();
    }

    @FindBy(id = "product_quantity")
    WebElement productQuantity;
    public int getQuantity() {
        String qty = productQuantity.getAttribute("value");
        assert qty != null;
        return Integer.parseInt(qty);
    }

    @FindBy(css = "span.total-price")
    WebElement totalPrice;
    public String getPrice() {
        return totalPrice.getText().trim();
    }
    @FindBy(css = "a.cart")
    WebElement cart;
    public void addToCart() {
        clickElementJS(cart);
    }
    @FindBy(css = "a.wishlist_add.btn.btn-large")
    WebElement wishlist;
    public void addToWishlist() {
        clickElementJS(wishlist);
    }
    @FindBy(css = "a.productprint.btn.btn-large")
    WebElement print;
    public void print() {
        clickElementJS(print);
    }

    // review
    @FindBy(css = "a[href='#review']")
    WebElement review;
    public void openReview() {
        clickElementJS(review);
    }
    private void selectStar(int i) {
        WebElement starDiv = driver.findElement(By.id("rating"+i));
        WebElement starLink = starDiv.findElement(By.tagName("a"));
        clickElementJS(starLink);
    }
    @FindBy(id = "name")
    WebElement nameBox;
    @FindBy(id = "text")
    WebElement reviewBox;
    @FindBy(id = "review_submit")
    WebElement reviewSubmit;
    public void addReview(int stars , String name , String review) {

        if(stars <= 0) {
            stars = 0;
        }
        if (stars >= 5) {
            stars = 5;
        }
        selectStar(stars);
        setElementText(nameBox, name);
        setElementText(reviewBox, review);
        System.out.println("Complete the code box and click Enter: ");
        Scanner scanner = new Scanner(System.in);
        String x = scanner.nextLine();
        clickElementJS(reviewSubmit);
    }


}
