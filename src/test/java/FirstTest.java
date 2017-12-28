import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeClass;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;


public class FirstTest {
	private static WebDriver driver;
	 
    @BeforeClass
    public static void setup() throws InterruptedException {
    	System.setProperty("webdriver.chrome.driver", "/home/chuck/eclipse/jee-neon/eclipse/chromedriver/chromedriver");
    	driver = new ChromeDriver();
    	
    	
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("https://www.instagram.com/accounts/login/");
        
        WebElement loginField = driver.findElement(By.xpath("//*[@name=\"username\"]"));
        loginField.sendKeys("nesquic");
        WebElement passwordField = driver.findElement(By.xpath("//*[@name=\"password\"]"));
        passwordField.sendKeys("qwert12345");
        
        WebElement loginButton = driver.findElement(By.xpath("//*[@class=\"_qv64e _gexxb _4tgw8 _njrw0\"]"));
        loginButton.click();
        
        
        WebElement searchTagButton = driver.findElement(By.xpath("//*[@class=\"_avvq0 _o716c\"]"));
        searchTagButton.sendKeys("#girls");
        searchTagButton.sendKeys(Keys.ENTER);
        searchTagButton.sendKeys(Keys.ENTER);
        String searchTagClick = driver.findElement(By.xpath("//*[@class=\"_mck9w _gvoze _f2mse\"]")).getAttribute("href");
        System.out.println(searchTagClick);
        
       /* Actions actions = new Actions(driver);
        WebElement searchTagClick1 = driver.findElement(By.xpath("//*[@class=\"_si7dy\"]"));
        actions.moveToElement(searchTagClick1);
        actions.click().build().perform();*/
        
       
        	   
      
        /*List<WebElement> photos = driver.findElements(By.xpath("//*[@class=\"_si7dy\"]"));
        for (int i = 1; i < photos.size(); i++)
        {
            System.out.println(photos.get(i).getAttribute("alt"));
            
        }*/
        
		
      /*  JavascriptExecutor jse = (JavascriptExecutor)driver;
        jse.executeScript("window.scrollBy(100,700)", "");*/
       if (driver instanceof JavascriptExecutor) {
    		((JavascriptExecutor) driver)
    			.executeScript("window.scrollTo(0, 500);");
    	}
        
    	
        

        
        /*WebElement confirmButton = driver.findElement(By.xpath("//*[@class=\"_qv64e _gexxb _4tgw8 _njrw0\"]"));
        confirmButton.click();
        ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
        driver.switchTo().window(tabs.get(2)); 
        driver.get("https://gmail.com");*/
        
      
        
    }
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		
		setup();

	}

}
