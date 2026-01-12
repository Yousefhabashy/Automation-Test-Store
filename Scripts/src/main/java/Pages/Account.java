package Pages;

import Base.PagesBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class Account extends PagesBase {
    public Account(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "div.alert.alert-success")
    public WebElement successMessage;

    @FindBy(css = "span.maintext")
    public WebElement mainText;

    @FindBy(css = "ul.side_account_list")
    WebElement accountList;
    public void openAccountDashboard() {
        WebElement accountDashboard = accountList.findElements(By.tagName("li")).getFirst();
        WebElement dashboardLink = accountDashboard.findElement(By.tagName("a"));
        clickElementJS(dashboardLink);
    }

    // Wishlist
    public void openAccountWishlist() {
        WebElement accountWishlist = accountList.findElements(By.tagName("li")).get(1);
        WebElement wishlistLink = accountWishlist.findElement(By.tagName("a"));
        clickElementJS(wishlistLink);
    }
    @FindBy(css = "table.table.table-striped.table-bordered")
    WebElement wishlistTable;
    private List<WebElement> getWishlistProducts() {
        List<WebElement> products = wishlistTable.findElements(By.tagName("tr"));
        products.removeFirst();
        return products;
    }
    public Wishlist getProduct(int productNumber) {
        int index = productNumber - 1;
        List<WebElement> products = getWishlistProducts();
        if(index >= 0 && index < products.size()) {
            return  new Wishlist(products.get(index));
        }
        throw  new IndexOutOfBoundsException("Product index " + index + " not found!");
    }
    public static class Wishlist {
        private WebElement product;
        public Wishlist(WebElement productElement) {
            this.product = productElement;
        }

        public String getProductTitle() {
            return product.findElement(By.cssSelector("td.align_left a")).getText();
        }
        public int getQuantity() {
            String value = product.findElements(By.tagName("td")).get(4).getText();
            return Integer.parseInt(value);
        }
        public String getUnitPrice() {
            return product.findElement(By.cssSelector("td div.oneprice")).getText();
        }

        public void addProductToCart() {
            WebElement addToCartLink = product.findElement(By.cssSelector("td.align_center a.btn.btn-sm.btn-primary"));
            clickElementJS(addToCartLink);
        }
        public void removeProduct() {
            WebElement removeLink = product.findElement(By.cssSelector("td.align_center a.btn.btn-sm.btn-default.btn-remove"));
            clickElementJS(removeLink);
        }
    }

    // Account details
    public void openEditAccount() {
        WebElement editAccount = accountList.findElements(By.tagName("li")).get(2);
        WebElement editAccountLink = editAccount.findElement(By.tagName("a"));
        clickElementJS(editAccountLink);
    }
    @FindBy(id = "AccountFrm_firstname")
    WebElement firstNameBox;
    @FindBy(id = "AccountFrm_lastname")
    WebElement lastNameBox;
    @FindBy(id = "AccountFrm_email")
    WebElement emailBox;
    @FindBy(id = "AccountFrm_telephone")
    WebElement telephoneBox;
    @FindBy(id = "AccountFrm_fax")
    WebElement faxBox;
    @FindBy(css = "a[title='Continue']")
    WebElement continueButton;
    public void changeAccountDetails(String firstName, String lastName,
                                     String email, String telephone, String fax) {
        firstNameBox.clear();
        setElementText(firstNameBox, firstName);
        lastNameBox.clear();
        setElementText(lastNameBox, lastName);
        emailBox.clear();
        setElementText(emailBox, email);
        telephoneBox.clear();
        setElementText(telephoneBox, telephone);
        faxBox.clear();
        setElementText(faxBox, fax);
        clickElementJS(continueButton);
    }
    public void changeAccountDetails(String firstName, String lastName, String email) {
        firstNameBox.clear();
        setElementText(firstNameBox, firstName);
        lastNameBox.clear();
        setElementText(lastNameBox, lastName);
        emailBox.clear();
        setElementText(emailBox, email);
        clickElementJS(continueButton);
    }

    // Account Password
    public void openChangePassword() {
        WebElement changePassword = accountList.findElements(By.tagName("li")).get(3);
        WebElement changePasswordLink = changePassword.findElement(By.tagName("a"));
        clickElementJS(changePasswordLink);
    }
    @FindBy(id = "")
    WebElement currentPasswordBox;
    @FindBy(id = "")
    WebElement newPasswordBox;
    @FindBy(id = "")
    WebElement confirmNewPasswordBox;
    @FindBy(css = "a[title='Continue']")
    WebElement changePassword;
    public void changePassword(String oldPassword, String newPassword) {
        setElementText(currentPasswordBox, oldPassword);
        setElementText(newPasswordBox, newPassword);
        setElementText(confirmNewPasswordBox, newPassword);
        clickElementJS(changePassword);
    }

    // Account Address
    public void openManageAddress() {
        WebElement manageAddress = accountList.findElements(By.tagName("li")).get(4);
        WebElement manageAddressLink = manageAddress.findElement(By.tagName("a"));
        clickElementJS(manageAddressLink);
    }
    @FindBy(css = "button[title='Edit']")
    WebElement editAddress;
    @FindBy(css = "button[title='New Address']")
    WebElement addNewAddress;
    @FindBy(id = "AddressFrm_firstname")
    WebElement addressFirstNameBox;
    @FindBy(id = "AddressFrm_lastname")
    WebElement addressLastNameBox;
    @FindBy(id = "AddressFrm_company")
    WebElement companyBox;
    @FindBy(id = "AddressFrm_address_1")
    WebElement address1Box;
    @FindBy(id = "AddressFrm_address_2")
    WebElement address2Box;
    @FindBy(id = "AddressFrm_city")
    WebElement cityBox;
    @FindBy(id = "AddressFrm_zone_id")
    WebElement selectStateBox;
    @FindBy(id = "AddressFrm_country_id")
    WebElement selectCountryBox;
    @FindBy(id = "AddressFrm_postcode")
    WebElement postalCodeBox;
    @FindBy(id = "AddressFrm_default1")
    WebElement setDefaultAddress;
    @FindBy(id = "AddressFrm_default0")
    WebElement notSetDefaultAddress;
    @FindBy(css = "a[title='Continue']")
    WebElement changeAddress;
    public void openEditAddress() {
        clickElementJS(editAddress);
    }
    public void changeCurrentAddress(String firstName, String lastName, String company,
                         String address1, String  address2, String city, String state,
                         String country, String postalCode, boolean setDefault) {
        addressFirstNameBox.clear();
        setElementText(addressFirstNameBox, firstName);
        addressLastNameBox.clear();
        setElementText(addressLastNameBox, lastName);
        companyBox.clear();
        setElementText(companyBox, company);
        address1Box.clear();
        setElementText(address1Box, address1);
        address2Box.clear();
        setElementText(address2Box, address2);
        cityBox.clear();
        setElementText(cityBox, city);
        Select selectCountry = new Select(selectCountryBox);
        selectCountry.deSelectByContainsVisibleText(country);
        Select selectState = new Select(selectStateBox);
        selectState.deSelectByContainsVisibleText(state);
        postalCodeBox.clear();
        setElementText(postalCodeBox, postalCode);
        if(setDefault) {
            clickElementJS(setDefaultAddress);
        } else {
            clickElementJS(notSetDefaultAddress);
        }
        clickElementJS(changeAddress);
    }

    public void changeCurrentAddress(String firstName, String lastName, String address1, String city,
                                     String state, String country, String postalCode, boolean setDefault) {
        addressFirstNameBox.clear();
        setElementText(addressFirstNameBox, firstName);
        addressLastNameBox.clear();
        setElementText(addressLastNameBox, lastName);
        address1Box.clear();
        setElementText(address1Box, address1);
        cityBox.clear();
        setElementText(cityBox, city);
        Select selectCountry = new Select(selectCountryBox);
        selectCountry.deSelectByContainsVisibleText(country);
        Select selectState = new Select(selectStateBox);
        selectState.deSelectByContainsVisibleText(state);
        postalCodeBox.clear();
        setElementText(postalCodeBox, postalCode);
        if(setDefault) {
            clickElementJS(setDefaultAddress);
        } else {
            clickElementJS(notSetDefaultAddress);
        }
        clickElementJS(changeAddress);
    }

    public void addNewAddress(String firstName, String lastName, String company,
                                     String address1, String  address2, String city, String state,
                                     String country, String postalCode, boolean setDefault) {
        setElementText(addressFirstNameBox, firstName);
        setElementText(addressLastNameBox, lastName);
        setElementText(companyBox, company);
        setElementText(address1Box, address1);
        setElementText(address2Box, address2);
        setElementText(cityBox, city);
        Select selectCountry = new Select(selectCountryBox);
        selectCountry.deSelectByContainsVisibleText(country);
        Select selectState = new Select(selectStateBox);
        selectState.deSelectByContainsVisibleText(state);
        setElementText(postalCodeBox, postalCode);
        if(setDefault) {
            clickElementJS(setDefaultAddress);
        } else {
            clickElementJS(notSetDefaultAddress);
        }
        clickElementJS(changeAddress);
    }
    public void addNewAddress(String firstName, String lastName, String address1, String city,
                              String state, String country, String postalCode, boolean setDefault) {
        setElementText(addressFirstNameBox, firstName);
        setElementText(addressLastNameBox, lastName);
        setElementText(address1Box, address1);
        setElementText(cityBox, city);
        Select selectCountry = new Select(selectCountryBox);
        selectCountry.deSelectByContainsVisibleText(country);
        Select selectState = new Select(selectStateBox);
        selectState.deSelectByContainsVisibleText(state);
        setElementText(postalCodeBox, postalCode);
        if(setDefault) {
            clickElementJS(setDefaultAddress);
        } else {
            clickElementJS(notSetDefaultAddress);
        }
        clickElementJS(changeAddress);
    }

    public void openOrderHistory() {
        WebElement orderHistory = accountList.findElements(By.tagName("li")).get(5);
        WebElement orderHistoryLink = orderHistory.findElement(By.tagName("a"));
        clickElementJS(orderHistoryLink);
    }

    public void logoutUser() {
        WebElement logout = accountList.findElements(By.tagName("li")).getLast();
        WebElement logoutLink = logout.findElement(By.tagName("a"));
        clickElementJS(logoutLink);
    }
}
