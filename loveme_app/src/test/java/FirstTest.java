import org.junit.*;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FirstTest {

    private final long WHILE_WAIT_TIME = 300;

    private static WebDriver driver;
    private String email;
    private String password;

    @BeforeClass
    public static void setConnection() throws MalformedURLException {
        System.out.print("Creating connection...");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("deviceName", "SM-A320");
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("appPackage", "com.crunchyroll.crunchyroid");
        capabilities.setCapability("appActivity", "com.crunchyroll.crunchyroid.activities.SplashActivity");
        capabilities.setCapability("unicodeKeyboard", true);
        capabilities.setCapability("resetKeyboard", true);
        driver = new RemoteWebDriver(new URL("http://192.168.43.66:4723/wd/hub"), capabilities);
        System.out.println("Done");
    }

    @Test
    public void test01_StartApp() throws InterruptedException {
        System.out.print("Loading...");
        do {
            Thread.sleep(WHILE_WAIT_TIME);
        } while (isLoading());
        System.out.println("Done");
    }

    @Test
    public void test02_OpenLoginWindow() throws InterruptedException {
        System.out.println("Login...");
        if (driver.findElements(By.id("com.crunchyroll.crunchyroid:id/just_explore")).size() == 1) {
            WebElement loginBtn = driver.findElement(By.id("com.crunchyroll.crunchyroid:id/login"));
            loginBtn.click();
        } else {
            WebElement menu = driver.findElement(By.className("android.widget.ImageButton"));
            menu.click();
            WebElement loginBtn = driver.findElement(By.id("com.crunchyroll.crunchyroid:id/login_account_text"));
            loginBtn.click();
        }
        while (driver.findElements(By.id("com.crunchyroll.crunchyroid:id/login_field")).size() == 0) {
            Thread.sleep(WHILE_WAIT_TIME);
        }
    }

    @Test
    public void test03_ValidateLoginEmail1() throws InterruptedException {
        WebElement continueButton = driver.findElement(By.id("com.crunchyroll.crunchyroid:id/continue_button"));
        continueButton.click();
        do {
            Thread.sleep(WHILE_WAIT_TIME);
        } while (driver.findElements(By.id("com.crunchyroll.crunchyroid:id/loading_view")).size() > 0);
        Thread.sleep(WHILE_WAIT_TIME);
        if (!driver.findElement(By.id("android:id/message")).getText().equals("You forgot to put in your User Name or Email.")) {
            driver.findElement(By.id("android:id/button1")).click(); // click on back button
            fail();
        } else {
            driver.findElement(By.id("android:id/button1")).click(); // click on back button
        }
    }

    @Test
    public void test04_ValidateLoginEmail2() throws InterruptedException {
        WebElement emailField = driver.findElement(By.id("com.crunchyroll.crunchyroid:id/login_field"));
        WebElement continueButton = driver.findElement(By.id("com.crunchyroll.crunchyroid:id/continue_button"));
        emailField.sendKeys("SomethingWrongo");
        continueButton.click();
        do {
            Thread.sleep(WHILE_WAIT_TIME);
        } while (driver.findElements(By.id("com.crunchyroll.crunchyroid:id/loading_view")).size() > 0);
        Thread.sleep(WHILE_WAIT_TIME);
        if (!driver.findElement(By.id("android:id/message")).getText().equals("You forgot to put in your password.")) {
            driver.findElement(By.id("android:id/button1")).click(); // click on back button
            fail();
        } else {
            driver.findElement(By.id("android:id/button1")).click(); // click on back button
        }
    }

    @Test
    public void test05_ValidateLoginPassword() throws InterruptedException {
        WebElement passwordField = driver.findElement(By.id("com.crunchyroll.crunchyroid:id/password_field"));
        WebElement continueButton = driver.findElement(By.id("com.crunchyroll.crunchyroid:id/continue_button"));
        passwordField.sendKeys("Something Very Wrong here");
        continueButton.click();
        do {
            Thread.sleep(WHILE_WAIT_TIME);
        } while (driver.findElements(By.id("com.crunchyroll.crunchyroid:id/loading_view")).size() > 0);
        Thread.sleep(WHILE_WAIT_TIME);
        if (!driver.findElement(By.id("android:id/message")).getText().equals("Incorrect login information.")) {
            driver.findElement(By.id("android:id/button1")).click(); // click on back button
            fail();
        } else {
            driver.findElement(By.id("android:id/button1")).click(); // click on back button
        }
    }

    @Test
    public void test06_ClickOnCreateAccount() {
        WebElement createBtn = driver.findElement(By.id("com.crunchyroll.crunchyroid:id/button_right_text"));
        createBtn.click();
    }

    @Test
    public void test07_ValidateCreateEmail1() throws InterruptedException {
        WebElement continueButton = driver.findElement(By.id("com.crunchyroll.crunchyroid:id/continue_button"));
        continueButton.click();
        do {
            Thread.sleep(WHILE_WAIT_TIME);
        } while (driver.findElements(By.id("com.crunchyroll.crunchyroid:id/loading_view")).size() > 0);
        Thread.sleep(WHILE_WAIT_TIME);
        if (!driver.findElement(By.id("android:id/message")).getText().equals("Please enter an email address.")) {
            driver.findElement(By.id("android:id/button1")).click(); // click on back button
            fail();
        } else {
            driver.findElement(By.id("android:id/button1")).click(); // click on back button
        }
    }

    @Test
    public void test08_ValidateCreateEmail2() throws InterruptedException {
        WebElement emailField = driver.findElement(By.id("com.crunchyroll.crunchyroid:id/login_field"));
        WebElement continueButton = driver.findElement(By.id("com.crunchyroll.crunchyroid:id/continue_button"));
        emailField.sendKeys("something@very.correct");
        continueButton.click();
        do {
            Thread.sleep(WHILE_WAIT_TIME);
        } while (driver.findElements(By.id("com.crunchyroll.crunchyroid:id/loading_view")).size() > 0);
        Thread.sleep(WHILE_WAIT_TIME);
        if (!driver.findElement(By.id("android:id/message")).getText().equals("Please enter a password.")) {
            driver.findElement(By.id("android:id/button1")).click(); // click on back button
            fail();
        } else {
            driver.findElement(By.id("android:id/button1")).click(); // click on back button
        }
    }

    @Test
    public void test09_ValidateCreateAccount1() throws InterruptedException {
        WebElement emailField = driver.findElement(By.id("com.crunchyroll.crunchyroid:id/login_field"));
        WebElement passwordField = driver.findElement(By.id("com.crunchyroll.crunchyroid:id/password_field"));
        WebElement continueButton = driver.findElement(By.id("com.crunchyroll.crunchyroid:id/continue_button"));
        emailField.sendKeys("SomethingWrong");
        passwordField.sendKeys("Also wrong");
        continueButton.click();
        do {
            Thread.sleep(WHILE_WAIT_TIME);
        } while (driver.findElements(By.id("com.crunchyroll.crunchyroid:id/loading_view")).size() > 0);
        Thread.sleep(WHILE_WAIT_TIME);
        if (!driver.findElement(By.id("android:id/message")).getText().equals("Invalid email address. Please use a different one.")) {
            driver.findElement(By.id("android:id/button1")).click(); // click on back button
            fail();
        } else {
            driver.findElement(By.id("android:id/button1")).click(); // click on back button
        }
        Thread.sleep(WHILE_WAIT_TIME);
    }

    @Test
    public void test10_ValidateCreateAccount2() throws InterruptedException {
        WebElement emailField = driver.findElement(By.id("com.crunchyroll.crunchyroid:id/login_field"));
        WebElement passwordField = driver.findElement(By.id("com.crunchyroll.crunchyroid:id/password_field"));
        WebElement continueButton = driver.findElement(By.id("com.crunchyroll.crunchyroid:id/continue_button"));
        emailField.sendKeys("something@veryy.correcte");
        passwordField.sendKeys("1224"); // short password
        continueButton.click();
        do {
            Thread.sleep(WHILE_WAIT_TIME);
        } while (driver.findElements(By.id("com.crunchyroll.crunchyroid:id/loading_view")).size() > 0);
        Thread.sleep(WHILE_WAIT_TIME);
        if (!driver.findElement(By.id("android:id/message")).getText().equals("Please use a longer password.")) {
            driver.findElement(By.id("android:id/button1")).click(); // click on back button
            fail();
        } else {
            driver.findElement(By.id("android:id/button1")).click(); // click on back button
        }
    }

    @Test
    public void test11_ValidateCreateAccount3() throws InterruptedException {
        WebElement emailField = driver.findElement(By.id("com.crunchyroll.crunchyroid:id/login_field"));
        WebElement passwordField = driver.findElement(By.id("com.crunchyroll.crunchyroid:id/password_field"));
        WebElement continueButton = driver.findElement(By.id("com.crunchyroll.crunchyroid:id/continue_button"));
        this.email = new RandomString().getRandomEmail();
        this.password = new RandomString().getRandomString(12);
        System.out.println(email);
        System.out.println(password);
        emailField.sendKeys(email);
        passwordField.sendKeys(password);
        continueButton.click();
        do {
            Thread.sleep(WHILE_WAIT_TIME);
        } while (driver.findElements(By.id("com.crunchyroll.crunchyroid:id/loading_view")).size() > 0);
        Thread.sleep(WHILE_WAIT_TIME);
        driver.findElement(By.id("com.crunchyroll.crunchyroid:id/close")).click();
    }


    @Test
    public void test12_LogoutButton() {
        WebElement menu = driver.findElement(By.className("android.widget.ImageButton"));
        menu.click();
        WebElement userName = driver.findElement(By.id("com.crunchyroll.crunchyroid:id/login_account"));
        userName.click();
        WebElement logout = driver.findElement(By.id("android:id/button1"));
        logout.click();
    }

    @Test
    public void test13_ClickForgotPassword() throws InterruptedException {
        WebElement menu = driver.findElement(By.className("android.widget.ImageButton"));
        menu.click();
        WebElement loginBtn = driver.findElement(By.id("com.crunchyroll.crunchyroid:id/login_account_text"));
        loginBtn.click();
        while (driver.findElements(By.id("com.crunchyroll.crunchyroid:id/login_field")).size() == 0) {
            Thread.sleep(WHILE_WAIT_TIME);
        }
        WebElement forgotBtn = driver.findElement(By.id("com.crunchyroll.crunchyroid:id/forgot_password_button"));
        forgotBtn.click();
    }

    @Test
    public void test14_ValidateForgotEmail1() throws InterruptedException {
        WebElement resetButton = driver.findElement(By.id("com.crunchyroll.crunchyroid:id/reset_password"));
        resetButton.click();
        do {
            Thread.sleep(WHILE_WAIT_TIME);
        } while (driver.findElements(By.id("com.crunchyroll.crunchyroid:id/loading_view")).size() > 0);
        Thread.sleep(WHILE_WAIT_TIME);
        if (!driver.findElement(By.id("android:id/message")).getText().equals("You forgot to insert your Email.")) {
            driver.findElement(By.id("android:id/button1")).click(); // click on back button
            fail();
        } else {
            driver.findElement(By.id("android:id/reset_password")).click(); // click on back button
        }
    }

    @Test
    public void test15_ClickForgotPassword() {
        WebElement forgotBtn = driver.findElement(By.id("com.crunchyroll.crunchyroid:id/forgot_password_button"));
        forgotBtn.click();
    }

    @Test
    public void test16_ValidateForgotEmail2() throws InterruptedException {
        WebElement emailField = driver.findElement(By.id("com.crunchyroll.crunchyroid:id/email"));
        WebElement resetBtn = driver.findElement(By.id("com.crunchyroll.crunchyroid:id/reset_password"));
        emailField.sendKeys("SomethingWrong");
        resetBtn.click();
        do {
            Thread.sleep(WHILE_WAIT_TIME);
        } while (driver.findElements(By.id("com.crunchyroll.crunchyroid:id/loading_view")).size() > 0);
        Thread.sleep(WHILE_WAIT_TIME);
        if (!driver.findElement(By.id("android:id/message")).getText().equals("You inserted an invalid Email.")) {
            driver.findElement(By.id("android:id/button1")).click(); // click on back button
            fail();
        } else {
            driver.findElement(By.id("android:id/reset_password")).click(); // click on back button
        }
    }

    @Test
    public void test17_ClickForgotPassword() {
        WebElement forgotBtn = driver.findElement(By.id("com.crunchyroll.crunchyroid:id/forgot_password_button"));
        forgotBtn.click();
    }

    @Test
    public void test18_ValidateForgotEmail3() throws InterruptedException {
        WebElement emailField = driver.findElement(By.id("com.crunchyroll.crunchyroid:id/email"));
        WebElement resetBtn = driver.findElement(By.id("com.crunchyroll.crunchyroid:id/reset_password"));
        emailField.sendKeys(new RandomString().getRandomEmail());
        resetBtn.click();
        do {
            Thread.sleep(WHILE_WAIT_TIME);
        } while (driver.findElements(By.id("com.crunchyroll.crunchyroid:id/loading_view")).size() > 0);
        Thread.sleep(WHILE_WAIT_TIME);
        if (!driver.findElement(By.id("android:id/message")).getText().equals("A link was sent in your Inbox.")) {
            driver.findElement(By.id("android:id/button1")).click(); // click on back button
            fail();
        } else {
            driver.findElement(By.id("android:id/reset_password")).click(); // click on back button
        }
    }

    @Test
    public void test19_ClickForgotPassword() {
        WebElement forgotBtn = driver.findElement(By.id("com.crunchyroll.crunchyroid:id/forgot_password_button"));
        forgotBtn.click();
    }

    @Test
    public void test20_CancelClick() {
        WebElement cancelButton = driver.findElement(By.id("com.crunchyroll.crunchyroid:id/cancel"));
        cancelButton.click();
        assertEquals(1, driver.findElements(By.id("com.crunchyroll.crunchyroid:id/continue_button")).size());
    }

    @Test
    public void test21_LoginValidData() throws InterruptedException {
        WebElement emailField = driver.findElement(By.id("com.crunchyroll.crunchyroid:id/login_field"));
        WebElement passwordField = driver.findElement(By.id("com.crunchyroll.crunchyroid:id/password_field"));
        WebElement continueButton = driver.findElement(By.id("com.crunchyroll.crunchyroid:id/continue_button"));

        System.out.println(this.email);
        System.out.println(this.password);
        emailField.sendKeys(email);
        passwordField.sendKeys(password);
        do {
            Thread.sleep(WHILE_WAIT_TIME);
        } while (driver.findElements(By.id("com.crunchyroll.crunchyroid:id/loading_view")).size() > 0);
        Thread.sleep(WHILE_WAIT_TIME);
    }

    @Test
    public void test22_LogoutButton() {
//        WebElement logout = driver.findElement(By.id("com.crunchyroll.crunchyroid:id/logout_field"));
//        logout.click();
    }

    private boolean isLoading() {
        // Check if menu or Just Explore text appears
        return driver.findElements(By.className("android.widget.ImageButton")).size() + driver.findElements(By.id("com.crunchyroll.crunchyroid:id/just_explore")).size() == 0;
    }

    @AfterClass
    public static void End() throws InterruptedException {
//        Thread.sleep(10000);
//        driver.quit();
    }
}
