package utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Parameters;

public class DriverSetup {
 
    public static WebDriver driver;
    public static final Log log = LogFactory.getLog("seleniumTestSuite");
    public static String choosenBrowser;
    
    public static WebDriver setUpWebBrowser(String browser) {
    	choosenBrowser = browser;
    	switch (browser) {
		case "chrome":
			driver = initChromeDriver();
			break;
		case "firefox":
			driver = initFirefoxDriver();
			break;
		default:
			System.out.println("Browser type : " + browser
					+ " is invalid, launching Chrome as default.");
			driver = initChromeDriver();
		}
		log.debug("setUpWebBrowser(browser) - returns HomePage with chosen browser driver");
		return driver;

    }

    private static WebDriver initFirefoxDriver() {
		System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "\\drivers\\win32\\geckodriver.exe");
		 // Configure the firefox profile.
       FirefoxProfile profile = new FirefoxProfile();
       profile.setPreference("dom.ipc.plugins.enabled.libflashplayer.so", false); // disable flash
       profile.setPreference("plugin.state.flash", 0); // again, try another way to disable flash
       profile.setAcceptUntrustedCertificates(true);
       profile.setAssumeUntrustedCertificateIssuer(true);
       // Set the capabilities
       DesiredCapabilities capability = DesiredCapabilities.firefox();
       capability.setCapability(FirefoxDriver.PROFILE, profile);

       driver = new FirefoxDriver(capability);		
       driver.manage().window().maximize();
	   return driver;
	}

	private static WebDriver initChromeDriver() {
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\drivers\\chromedriver.exe");

		ChromeOptions options = new ChromeOptions();
		options.addArguments("disable-popup-blocking");
		options.addArguments("--test-type");
		options.addArguments("--allow-running-insecure-content");
		options.addArguments("--no-sandbox");
		options.addArguments("disable-infobars");

		driver = new ChromeDriver(options);
		driver.manage().window().maximize();
		return driver;
	}

    public WebDriver getDriver() {
        return driver;
    }
}
