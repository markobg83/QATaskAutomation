package pages;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {

	private static Logger log = Logger.getLogger(HomePage.class);

	@FindBy(xpath = "//span[@class='icon-chevron-down']")
	private WebElement menuLocator;

	@FindBy(xpath = "//p[contains(.,'User profile')]")
	private WebElement userProfileLocator;

	@FindBy(xpath = "//p[contains(.,'Log out')]")
	private WebElement logoutLocator;

	@FindBy(xpath = "//span[@class='icon-group-add']")
	private WebElement createGroupLocator;

	@FindBy(xpath = "//h3[@ng-if='!vm.liveTopics.showConclusionsFromTopic']")
	private WebElement myGroupConclusionsHeader;

	public HomePage(WebDriver driver) {
		super(driver);
	}

	public ProfilePage clickOnUserProfile() {
		log.debug("Click on user profile");

		waitForElementToBeVisible(menuLocator);
		menuLocator.click();

		waitForElementToBeVisible(userProfileLocator);
		userProfileLocator.click();
		return new ProfilePage(driver);
	}

	public GroupsTopicsPage createGroupFromHomePage() {
		log.debug("createGroupFromHomePage");

		waitForElementToBeVisible(createGroupLocator);
		createGroupLocator.click();
		return new GroupsTopicsPage(driver);
	}

	public ConclusionsViewPage openConlusion(String concludedTopicName) {
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		waitForElementToBeVisible(myGroupConclusionsHeader);
		WebElement conclusion = driver.findElement(
				By.xpath("//h3[@ui-sref='app.conclusion-view({conclusionId : conclusion.id })'][contains(.,'" + concludedTopicName + "')]"));

		conclusion.click();
		return new ConclusionsViewPage(driver);
	}

}
