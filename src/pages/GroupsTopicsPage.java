package pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import utils.DriverSetup;

public class GroupsTopicsPage extends BasePage {
	
	private static Logger log = Logger.getLogger(GroupsTopicsPage.class);
	
	@FindBy(xpath = "//input[@placeholder='Set Main Topic...']")
	WebElement topicNameLocator;
	
	@FindBy(xpath = "//textarea[@ng-model='vm.group.description']")
	WebElement topicDescriptionLocator;

	@FindBy(xpath = "//button[@ng-click='vm.createGroup()']")
	WebElement createTopicLocator;
	
	@FindBy(xpath = "//span[@class='icon-chat-main big']")
	WebElement topicMessageButtonLocator;
	
	@FindBy(xpath = "//textarea[contains(@ng-model,'vm.messageText')]")
	WebElement topicMessageTextLocator;
	
	 @FindBy(xpath = "//span[@class='icon-send']")
	 WebElement sendMessageLocator;
	 
	 @FindBy(xpath = "//pre[contains(@ng-bind-html,'::vm.data.message | mention: vm.data.mentionsMap:vm.data.mentionTags')]")
	 WebElement justSentMessageLocator;
	 
	 @FindBy(xpath = "//button[@ete='create-conclusion']")
	 WebElement createConclusionLocator;
	 
	 @FindBy(xpath = "//button[@ng-click='vm.propose()']")
	 WebElement proposeConclusionLocator;
	 	 
	 @FindBy(xpath = "//div[@class='md-toolbar-tools']")
	 WebElement frameLocator;
	 
	 @FindBy(xpath = "//span[contains(@class,'icon-close ng-scope')]")
	 WebElement closeFrameLocator;
	 
	 @FindBy(xpath = "//span[@ng-bind-html='::(vm.data.message)'][contains(.,'New conclusion approved.')]")
	 WebElement conclusionApproverMessageLocator;
	 
	 String xpathVoting = "//md-select[@ng-model='vm.conclusion.votingType']";
	 @FindBy(xpath = "//md-select[@ng-model='vm.conclusion.votingType']")
	 WebElement votingLocator;
	 
	 @FindBy(xpath = "//div[@class='md-text ng-binding'][contains(.,'Majority')]")
	 WebElement voteMajority;
	 
	  public GroupsTopicsPage(WebDriver driver) {
	        super(driver);
	    }

	public void createTopic(String topicName, String topicDesc) {
		log.debug("create topic");
		waitForElementToBeVisible(topicNameLocator);
		topicNameLocator.click();
		topicNameLocator.sendKeys(topicName);
		topicDescriptionLocator.click();
		topicDescriptionLocator.sendKeys(topicDesc);
		
		waitForElementToBeVisible(createTopicLocator);
		createTopicLocator.click();
		log.debug("created topic: " + topicName);
		
	}
	
	public void sendMessage(String text){
		waitForElementToBeVisible(topicMessageButtonLocator);
		topicMessageButtonLocator.click();
		
		waitForElementToBeVisible(topicMessageTextLocator);
		topicMessageTextLocator.sendKeys(text);
		sendMessageLocator.click();
	}
	
	public String getJustSentMessage(){
		waitForElementToBeVisible(justSentMessageLocator);
		return justSentMessageLocator.getText();
	}
	
	public void createConclusion(String conclusionText)
	{
		waitForElementToBeVisible(createConclusionLocator);
		createConclusionLocator.click();
				
		waitForElementToBeVisible(frameLocator);
		frameLocator.click();
			
		
		driver.switchTo().frame("ui-tinymce-1_ifr");
		WebElement element = driver.findElement(By.cssSelector("body"));
		element.sendKeys(conclusionText);
		
		if (DriverSetup.choosenBrowser.equals("chrome")){
			driver.switchTo().defaultContent();
		}
		else{
			driver.switchTo().parentFrame();
		}
		waitForElementToBeVisible(votingLocator);
		votingLocator.click();
		waitForElementToBeVisible(voteMajority);
		voteMajority.click();

		waitForElementToBeVisible(proposeConclusionLocator);
		proposeConclusionLocator.click();
	}
	
	public String getConclusionApproverMessage(){
		waitForElementToBeVisible(conclusionApproverMessageLocator);
		return conclusionApproverMessageLocator.getText();
	}
	
}
