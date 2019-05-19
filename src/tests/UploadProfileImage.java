package tests;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import pages.ProfilePage;
import utils.DriverSetup;

public class UploadProfileImage {
  
	private WebDriver driver;	
	private LoginPage loginPage;
	private String username = "marko.bogdanovic@mailinator.com";
	private String password = "Marko123!";
	private String filePath = System.getProperty("user.dir") + "\\resources\\images\\iipsrv.jpg";
	private static Logger log = Logger.getLogger(ProfilePage.class);

	//@BeforeTest
	//@Parameters({ "browser" })
	public void setUp(String browser) {
		driver = DriverSetup.setUpWebBrowser(browser);
		log.info("setUp()");
	}

	@Test
	public void testUploadProfileImage() {
	
		log.debug("testUploadProfileImage");
		
	// comment following line and uncomment @BeforeTest if you don't run test from testng.xml 
		driver = DriverSetup.setUpWebBrowser("chrome");  
		loginPage = new LoginPage(driver);
		loginPage.open();
		HomePage homePage = loginPage.signIn(username, password);
		
		ProfilePage profilePage = homePage.clickOnUserProfile();
	
		profilePage.clikcProfileSection();
		profilePage.clickOnImage();
		profilePage.uploadFile(filePath);
		
		assert profilePage.isProfileImageDisplayed() : "Profile image is not visible";		
		profilePage.logout();
		log.debug("Successfull first test");	    

	}
  
	@AfterTest
	public void tearDown() {
		if(driver!=null) {
			log.debug("Closing  browser");
			driver.quit();
		}
	}
}
