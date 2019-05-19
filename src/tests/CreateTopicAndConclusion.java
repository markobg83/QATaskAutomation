package tests;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.GroupsTopicsPage;
import pages.HomePage;
import pages.LoginPage;
import utils.DriverSetup;

public class CreateTopicAndConclusion {
	
  private WebDriver driver;	
  private LoginPage loginPage;
  private String username = "marko.bogdanovic@mailinator.com";
  private String password = "Marko123!";
  private String topicName = "topic test " + System.currentTimeMillis();
  private String topicDesc = "description test";
  private String messageText = "message test";
  private String conclusionText = "conclusion test";
  private String expectedConclusionMessage = "New conclusion approved.";
  private static Logger log = Logger.getLogger(GroupsTopicsPage.class);

//	@BeforeTest
//	@Parameters({ "browser" })
	public void setUp(String browser) {
		driver = DriverSetup.setUpWebBrowser(browser);
		log.info("setUp()");
	}

  @Test
  public void testCreateTopicAndConclusion() {
	  
	  log.debug("testCreateTopicAndConclusion");
		System.out.println("testCreateTopicAndConclusion");
		
		// comment following line and uncomment @BeforeTest if you don't run test from testng.xml 
		driver = DriverSetup.setUpWebBrowser("chrome");
		
		loginPage = new LoginPage(driver);
		loginPage.open();
		HomePage homePage = loginPage.signIn(username, password);
		
		GroupsTopicsPage groupsTopicsPage = homePage.createGroupFromHomePage();
		
		
		groupsTopicsPage.createTopic(topicName, topicDesc);
		groupsTopicsPage.sendMessage(messageText);
		
		String actualSentMessage = groupsTopicsPage.getJustSentMessage();
		assert messageText.equals(actualSentMessage) : "Expected: " + messageText + " but got: " + actualSentMessage;
		
		groupsTopicsPage.createConclusion(conclusionText);
		
		assert groupsTopicsPage.getConclusionApproverMessage().equals(expectedConclusionMessage) : "There is no message as: " + expectedConclusionMessage;
		
		groupsTopicsPage.logout();
  }
  
	@AfterClass
	public void tearDown() {
		if(driver!=null) {
			log.debug("Closing  browser");
			driver.quit();
		}
	}
  
}
