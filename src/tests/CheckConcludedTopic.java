package tests;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.ConclusionsViewPage;
import pages.GroupsTopicsPage;
import pages.HomePage;
import pages.LoginPage;
import utils.DriverSetup;

public class CheckConcludedTopic {
	
  private WebDriver driver;	
  private LoginPage loginPage;
  private String username = "marko.bogdanovic@mailinator.com";
  private String password = "Marko123!";
  private String concludedTopicName = "topic test 1558296278781";
  private String expectedInitiator = "Marko Bogdanovic";
  private static Logger log = Logger.getLogger(GroupsTopicsPage.class);

//	@BeforeTest
//	@Parameters({ "browser" })
	public void setUp(String browser) {
		driver = DriverSetup.setUpWebBrowser(browser);
		log.info("setUp()");
	}

  @Test
  public void testCheckConcludedTopic() {
	  
	  log.debug("testCheckConcludedTopic");
		System.out.println("testCheckConcludedTopic");
		
		// comment following line and uncomment @BeforeTest if you don't run test from testng.xml 
		driver = DriverSetup.setUpWebBrowser("chrome");
		
		loginPage = new LoginPage(driver);
		loginPage.open();
		HomePage homePage = loginPage.signIn(username, password);
		
		ConclusionsViewPage conclusionsViewPage = homePage.openConlusion(concludedTopicName);
		
		String actualInitiator = conclusionsViewPage.getConclusionsInitiator();
		assert actualInitiator.equals(expectedInitiator) : "Expected initiator to be: " + expectedInitiator + " but got: " + actualInitiator;
		
		conclusionsViewPage.logout();
  }
  
  @AfterClass
	public void tearDown() {
		if(driver!=null) {
			log.debug("Closing  browser");
			driver.quit();
		}
	}
}
