package Pages;

import Base.PagesBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;
import java.util.Objects;
import java.util.Random;

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
    public String getProductTitle(WebElement productLink) {
        return Objects.requireNonNull(productLink.getAttribute("title"));
    }
}
