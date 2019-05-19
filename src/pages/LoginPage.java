package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.apache.log4j.Logger;

public class LoginPage extends BasePage{
	
	private static Logger log = Logger.getLogger(LoginPage.class);
	String loginUrl = "https://9mentors.multitenancy.dev-9mentors.com";

	@FindBy(id="username")
	private WebElement usernameLocator;
	
	@FindBy(id="password")
	private WebElement passwordLocator;
	
	@FindBy(id="kc-login")
	private WebElement buttonSignInLocator;
	
	@FindBy(id="rememberMe")
	private WebElement rememberMeCheckBoxLocator;
	
	
	  public LoginPage(WebDriver driver) {
	        super(driver);
	    }
	

		public LoginPage open() {
			log.debug("Open login page");	
			driver.get(loginUrl);
			return this;
		}

		
	public HomePage signIn(String username, String pass)
	{
		log.debug("Sign in");		
		waitForElementToBeVisible(usernameLocator);
		usernameLocator.sendKeys(username);	
		passwordLocator.sendKeys(pass);	
		checkUncheckRememberMe();
		buttonSignInLocator.click();
		return new HomePage(driver);
	}
	
	public void checkUncheckRememberMe()
	{
		log.debug("Uncheck 'remember me' checkbox");		
		rememberMeCheckBoxLocator.click();
	}

}
