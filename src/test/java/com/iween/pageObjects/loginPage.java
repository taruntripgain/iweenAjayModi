package com.iween.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class loginPage extends BasePage{

	 // Constructor of loginPage calls the BasePage constructor with driver
    public loginPage(WebDriver driver) {
        super(driver);// calls BasePage constructor
    }
    
    @FindBy(id="agent_userid")
	WebElement userName;
 
 
	@FindBy(id="agent_password")
	WebElement password;
 
	@FindBy(xpath="//button[@type='submit']")
	WebElement submit;
 
	public void UserLogin(String uName, String pwd) throws InterruptedException
	{
		try
		{
		System.out.println(uName);
		System.out.println(pwd);
		Thread.sleep(2000);
		userName.sendKeys(uName);
		password.sendKeys(pwd);
		submit.click();
		}
		catch(Exception e)
		{
		  e.printStackTrace();
		}
 
	} 
    		
    		 
    		
    		 
	
}
