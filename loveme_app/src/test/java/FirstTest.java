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

    static WebDriver driver;

    @BeforeClass
    public static void setConnection() throws MalformedURLException {
        System.out.print("Creating connection...");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("deviceName", "SM-A320");
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("appPackage", "com.crunchyroll.crunchyroid");
        capabilities.setCapability("appActivity", "com.crunchyroll.crunchyroid.activities.SplashActivity");
        driver = new RemoteWebDriver(new URL("http://192.168.43.66:4723/wd/hub"), capabilities);
        System.out.println("Done");
    }

    @Test
    public void test01_StartApp() throws InterruptedException {
        System.out.print("Loading...");
        while (isLoading()) {
            Thread.sleep(50);
        }
        System.out.println("Done");
    }

    @Test
    public void test02_OpenLoginWindow() {
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
    }

    @Test
    public void test03_ValidateLoginEmail1() throws InterruptedException {
        while (driver.findElements(By.id("com.crunchyroll.crunchyroid:id/login_field")).size() == 0) {
            Thread.sleep(50);
        }
        WebElement continueButton = driver.findElement(By.id("com.crunchyroll.crunchyroid:id/continue_button"));
        continueButton.click();
        while (driver.findElements(By.id("com.crunchyroll.crunchyroid:id/loading_view")).size() > 0) {
            Thread.sleep(50);
        }
        if (!driver.findElement(By.id("android:id/message")).getText().equals("You forgot to put in your User Name or Email.")) {
            fail();
        }
        driver.findElement(By.id("android:id/button1")).click(); // click on back button
    }

    @Test
    public void test04_ValidateLoginEmail2() throws InterruptedException {
        WebElement emailField = driver.findElement(By.id("com.crunchyroll.crunchyroid:id/login_field"));
        WebElement continueButton = driver.findElement(By.id("com.crunchyroll.crunchyroid:id/continue_button"));
        emailField.sendKeys("SomethingWrong");
        continueButton.click();
        while (driver.findElements(By.id("com.crunchyroll.crunchyroid:id/loading_view")).size() > 0) {
            Thread.sleep(50);
        }
        if (!driver.findElement(By.id("android:id/message")).getText().equals("You forgot to put in your password.")) {
            fail();
        }
        driver.findElement(By.id("android:id/button1")).click(); // click on back button
    }

    @Test
    public void test05_ValidateLoginPassword() throws InterruptedException {
        WebElement passwordField = driver.findElement(By.id("com.crunchyroll.crunchyroid:id/password_field"));
        WebElement continueButton = driver.findElement(By.id("com.crunchyroll.crunchyroid:id/continue_button"));
        passwordField.sendKeys("Something Very Wrong here");
        continueButton.click();
        while (driver.findElements(By.id("com.crunchyroll.crunchyroid:id/loading_view")).size() > 0) {
            Thread.sleep(50);
        }
        if (!driver.findElement(By.id("android:id/message")).getText().equals("Incorrect login information.")) {
            fail();
        }
        driver.findElement(By.id("android:id/button1")).click(); // click on back button
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
        while (driver.findElements(By.id("com.crunchyroll.crunchyroid:id/loading_view")).size() > 0) {
            Thread.sleep(50);
        }
        if (!driver.findElement(By.id("android:id/message")).getText().equals("Please enter an email address.")) {
            fail();
        }
        driver.findElement(By.id("android:id/button1")).click(); // click on back button
    }

    @Test
    public void test08_ValidateCreateEmail2() throws InterruptedException {
        WebElement emailField = driver.findElement(By.id("com.crunchyroll.crunchyroid:id/login_field"));
        WebElement continueButton = driver.findElement(By.id("com.crunchyroll.crunchyroid:id/continue_button"));
        emailField.sendKeys("something@very.correct");
        continueButton.click();
        while (driver.findElements(By.id("com.crunchyroll.crunchyroid:id/loading_view")).size() > 0) {
            Thread.sleep(50);
        }
        if (!driver.findElement(By.id("android:id/message")).getText().equals("Please enter a password.")) {
            fail();
        }
        driver.findElement(By.id("android:id/button1")).click(); // click on back button
    }

    @Test
    public void test09_ValidateCreateAccount1() throws InterruptedException {
        WebElement emailField = driver.findElement(By.id("com.crunchyroll.crunchyroid:id/login_field"));
        WebElement passwordField = driver.findElement(By.id("com.crunchyroll.crunchyroid:id/password_field"));
        WebElement continueButton = driver.findElement(By.id("com.crunchyroll.crunchyroid:id/continue_button"));
        emailField.sendKeys("SomethingWrong");
        passwordField.sendKeys("Also wrong");
        continueButton.click();
        while (driver.findElements(By.id("com.crunchyroll.crunchyroid:id/loading_view")).size() > 0) {
            Thread.sleep(50);
        }
        if (!driver.findElement(By.id("android:id/message")).getText().equals("Invalid email address.  Please use a different one.")) {
            fail();
        }
        driver.findElement(By.id("android:id/button1")).click(); // click on back button
    }

    @Test
    public void test10_ValidateCreateAccount2() throws InterruptedException {
        WebElement emailField = driver.findElement(By.id("com.crunchyroll.crunchyroid:id/login_field"));
        WebElement passwordField = driver.findElement(By.id("com.crunchyroll.crunchyroid:id/password_field"));
        WebElement continueButton = driver.findElement(By.id("com.crunchyroll.crunchyroid:id/continue_button"));
        emailField.sendKeys("something@very.correct");
        passwordField.sendKeys("1"); // short password
        continueButton.click();
        while (driver.findElements(By.id("com.crunchyroll.crunchyroid:id/loading_view")).size() > 0) {
            Thread.sleep(50);
        }
        if (!driver.findElement(By.id("android:id/message")).getText().equals("Please use a longer password.")) {
            fail();
        }
        driver.findElement(By.id("android:id/button1")).click(); // click on back button
    }

    @Test
    public void test11_ValidateCreateAccount3() throws InterruptedException {
        WebElement emailField = driver.findElement(By.id("com.crunchyroll.crunchyroid:id/login_field"));
        WebElement passwordField = driver.findElement(By.id("com.crunchyroll.crunchyroid:id/password_field"));
        WebElement continueButton = driver.findElement(By.id("com.crunchyroll.crunchyroid:id/continue_button"));
        emailField.sendKeys("something@very.correct");
        passwordField.sendKeys("a very good password");
        continueButton.click();
        while (driver.findElements(By.id("com.crunchyroll.crunchyroid:id/loading_view")).size() > 0) {
            Thread.sleep(50);
        }
    }

    @Test
    public void test12_ClickForgotPassword() {
        // click on Log In
        // click on Forgot my Password
    }

    @Test
    public void test13_ValidateForgotEmail() {
        // click on continue
        // check if appropriate message appears
        // write some invalid data
        // check if appropriate message appears
        // write some valid data
        // check if appropriate message appears

        // remember to open forgot password again
    }

    @Test
    public void test14_CancelClick() {
        // click on cancel
        // check if returned to previous window
    }

    @Test
    public void test15_LoginValidData() {
        // write valid email
        // write valid password
        // which we used to register
        // click on continue
    }

    private boolean isLoading() {
        // Check if menu or Just Explore text appears
        return driver.findElements(By.className("android.widget.ImageButton")).size() + driver.findElements(By.id("com.crunchyroll.crunchyroid:id/just_explore")).size() == 0;
    }

    @AfterClass
    public static void End() throws InterruptedException {
        Thread.sleep(10000);
        driver.quit();
    }
}
