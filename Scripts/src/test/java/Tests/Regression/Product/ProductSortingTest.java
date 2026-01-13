package Tests.Regression.Product;

import Base.TestBase;
import Header.HeaderComponents;
import Pages.Home;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Objects;

public class ProductSortingTest extends TestBase {

    HeaderComponents header;
    Home home;

    @Test(priority = 1)
    public void openFragrance() {

        header = new HeaderComponents(driver);
        header.selectFragrance();

        waitFor().until(ExpectedConditions.urlContains("product/category&path=49"));
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("product/category&path=49"));
    }

    @Test(priority = 2)
    public void sortByNameAToZ() {
        home = new Home(driver);
        waitFor().until(ExpectedConditions.visibilityOf(home.sortDropDown));
        waitFor().until(ExpectedConditions.elementToBeClickable(home.sortDropDown));
        home.selectAToZ();
        boolean isSorted = home.isSortedAToZ();
        Assert.assertTrue(isSorted, "Products not sorted alphabetically from A to Z");
    }

    @Test(priority =3)
    public void sortByNameZToA() {
        home = new Home(driver);
        waitFor().until(ExpectedConditions.visibilityOf(home.sortDropDown));
        waitFor().until(ExpectedConditions.elementToBeClickable(home.sortDropDown));
        home.selectZToA();
        boolean isSorted = home.isSortedZToA();
        Assert.assertTrue(isSorted, "Products not sorted alphabetically from Z to A");
    }

    @Test(priority = 4)
    public void sortByPriceLowToHigh() {
        home = new Home(driver);
        waitFor().until(ExpectedConditions.visibilityOf(home.sortDropDown));
        waitFor().until(ExpectedConditions.elementToBeClickable(home.sortDropDown));
        home.selectPriceLowToHigh();
        boolean isSorted = home.isSortedLowToHigh();
        Assert.assertTrue(isSorted, "Products not sorted from price low to high");
    }

    @Test(priority = 5)
    public void sortByPriceHighToLow() {
        home = new Home(driver);
        waitFor().until(ExpectedConditions.visibilityOf(home.sortDropDown));
        waitFor().until(ExpectedConditions.elementToBeClickable(home.sortDropDown));
        home.selectPriceHighToLow();
        boolean isSorted = home.isSortedHighToLow();
        Assert.assertTrue(isSorted, "Products not sorted from price high to low");
    }
}