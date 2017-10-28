import org.junit.*;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
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
        // Created object of DesiredCapabilities class.
        DesiredCapabilities capabilities = new DesiredCapabilities();

        // Set android deviceName desired capability. Set your device name.
        capabilities.setCapability("deviceName", "SM-A320");

        // Set BROWSER_NAME desired capability. It's Android in our case here.
//        capabilities.setCapability(CapabilityType.BROWSER_NAME, "Android");

        // Set android VERSION desired capability. Set your mobile device's OS version.
//        capabilities.setCapability(CapabilityType.VERSION, "7.0");

        // Set android platformName desired capability. It's Android in our case here.
        capabilities.setCapability("platformName", "Android");

        // Set android appPackage desired capability. It is
        // com.android.calculator2 for calculator application.
        // Set your application's appPackage if you are using any other app.
        capabilities.setCapability("appPackage", "com.crunchyroll.crunchyroid");

        // Set android appActivity desired capability. It is
        // com.android.calculator2.Calculator for calculator application.
        // Set your application's appPackage if you are using any other app.
        capabilities.setCapability("appActivity", "com.crunchyroll.crunchyroid.activities.SplashActivity");

        // Created object of RemoteWebDriver will all set capabilities.
        // Set appium server address and port number in URL string.
        // It will launch calculator app in android device.

        driver = new RemoteWebDriver(new URL("http://192.168.43.66:4723/wd/hub"), capabilities);
        System.out.println("Done");
    }

    @Test
    public void test01_StartApp() throws InterruptedException {
        System.out.println("Loading...");
        while (isLoading()) {
            Thread.sleep(50);
        }
        System.out.println("Done");
        if (driver.findElements(By.id("com.crunchyroll.crunchyroid:id/just_explore")).size() == 1) {
            System.out.println("User is on Just Explore...");
        } else {
            System.out.println("User is on app...");
        }
        assertEquals(1, 1);
    }

    @Test
    public void test02_Login() {
        System.out.println("Login");
    }

    private boolean isLoading() {
        // Check if menu or Just Explore text appears
        return driver.findElements(By.className("android.widget.ImageButton")).size() + driver.findElements(By.id("com.crunchyroll.crunchyroid:id/just_explore")).size() == 0;
    }

    @AfterClass
    public static void End() {
        driver.quit();
    }
}
