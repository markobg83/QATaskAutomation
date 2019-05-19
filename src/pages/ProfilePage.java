package pages;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

public class ProfilePage extends BasePage {

	private static Logger log = Logger.getLogger(ProfilePage.class);
	
	@FindBy(xpath = "//a[contains(.,'Profile')]")
	private WebElement profileSectionLocator;

	@FindBy(xpath = "//div[@class='profile-image upload ng-scope']")
	private WebElement imageLocator;

	@FindBy(xpath = "//span[contains(.,'Upload')]")
	private WebElement uploadButtonLocator;

	//@FindBy(xpath = "//span[@class='ng-scope'][contains(.,'Select Picture')]")
	@FindBy(xpath = "//span[contains(@class,'icon-photo-size ng-scope')]")
	private WebElement buttonSelectPictureLocator;
	
	@FindBy(xpath = "//md-dialog-actions[@layout='row']")
	private WebElement imageDialogActionsLocator;

	public ProfilePage(WebDriver driver) {
		super(driver);
	}

	public void clikcProfileSection(){
		waitForElementToBeVisible(profileSectionLocator);
		profileSectionLocator.click();
	}
	
	public void clickOnImage() {
		log.debug("Click on user image");
		waitForElementToBeVisible(imageLocator);
		imageLocator.click();
	}

	public void uploadImage() {
		// method which is working if button has 'input' tag
		WebElement selectImageLocator;
		try {
			Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(30, TimeUnit.SECONDS)
					.pollingEvery(5, TimeUnit.SECONDS).ignoring(NoSuchElementException.class);
			selectImageLocator = wait.until(new Function<WebDriver, WebElement>() {

				public WebElement apply(WebDriver driver) {
					return driver.findElement(By.xpath("//div[@ng-if='!vm.file']"));
				}
			});
		} catch (TimeoutException tex) {
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			selectImageLocator = driver.findElement(By.xpath("//div[@ng-if='!vm.file']"));
		}

		selectImageLocator.sendKeys(System.getProperty("user.dir") + "\\resources\\images\\Lighthouse.jpg");

		waitForElementToBeVisible(uploadButtonLocator);
		uploadButtonLocator.click();
	}

	public void uploadFile(String filePath) {		
		// method with robots class, which we are using if button doesn't have input tag
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		waitForElementToBeVisible(buttonSelectPictureLocator);
		buttonSelectPictureLocator.click();
		StringSelection stringSelection = new StringSelection(filePath);
		log.debug("Upload file from: " + filePath);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(stringSelection, null);

		Robot robot = null;

		try {
			robot = new Robot();
		} 
		catch (AWTException e) {
			log.debug("Exception occured");
			e.printStackTrace();
		}

		robot.delay(250);

		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);

		
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.delay(1500);
		robot.keyRelease(KeyEvent.VK_ENTER);
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

		saveUpload();
	}

	private void saveUpload() {
		log.debug("confirm upload");
		driver.switchTo().activeElement();
		waitForElementToBeClickable(uploadButtonLocator);
		imageDialogActionsLocator.click();
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		executor.executeScript("arguments[0].click();", uploadButtonLocator);
	//	uploadButtonLocator.click();
	}

	public boolean isProfileImageDisplayed() {
		try {
			waitForElementToBeVisible(imageLocator);
			imageLocator.findElement(By.tagName("img")).getAttribute("src");
			log.debug("Image is displayed");
			return true;
		} catch (NoSuchElementException e) {
			log.debug(e.getMessage());
			return false;
		}
	}
}
