import org.junit.*;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

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
    public void test03_ValidateEmail() {

    }

    @Test
    public void test04_ValidatePassword() {

    }

    @Test
    public void test05_ContinueClick() {

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
