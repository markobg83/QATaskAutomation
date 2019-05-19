package pages;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ConclusionsViewPage extends BasePage {

	private static Logger log = Logger.getLogger(HomePage.class);
	  
	  @FindBy(xpath = "//span[@class='ng-binding']")
	  WebElement conclusionInitiatorLocator;
	
	  public ConclusionsViewPage(WebDriver driver) {
	        super(driver);
	    }
	  
	  public String getConclusionsInitiator(){
		  log.debug("getConclusionsInitiator");
		  waitForElementToBeVisible(conclusionInitiatorLocator);
		  return conclusionInitiatorLocator.getText();
	  }
	  
}
