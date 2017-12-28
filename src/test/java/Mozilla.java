import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.Action;


public class Mozilla {
	

	public static void main(String[] args) throws InterruptedException, ClassNotFoundException, IOException {
		// TODO Auto-generated method stub
		
		System.setProperty("webdriver.gecko.driver", "/home/chuck/eclipse/jee-neon/eclipse/mozilla/geckodriver");
		WebDriver driver = new FirefoxDriver();
		driver.manage().window().maximize();
	    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	    driver.get("https://www.instagram.com/accounts/login/");
	    
	    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	    String input = reader.readLine();
	    
	    WebElement loginField = driver.findElement(By.xpath("//*[@name=\"username\"]"));
	    loginField.sendKeys("bbtour.info");
	    WebElement passwordField = driver.findElement(By.xpath("//*[@name=\"password\"]"));
	    passwordField.sendKeys("tourapp45");
	        
	    WebElement loginButton = driver.findElement(By.xpath("//*[@class=\"_qv64e _gexxb _4tgw8 _njrw0\"]"));
	    loginButton.click();
	            
	    WebElement searchTagButton = driver.findElement(By.xpath("//*[@class=\"_avvq0 _o716c\"]"));
	    searchTagButton.sendKeys("#advanture"+ "\n");
	    Thread.sleep(1000);
	    searchTagButton.sendKeys(Keys.ENTER);
	    Thread.sleep(1000);
	    searchTagButton.sendKeys(Keys.ENTER);
	       
	    Mozilla m = new Mozilla();
	    for (int i=0;i < 50;i++){
	    	JavascriptExecutor jse = (JavascriptExecutor)driver;
		    jse.executeScript("window.scrollBy(0,2000)", "");
		    Thread.sleep(3000);
	    }    
	    
	    List<WebElement> list=driver.findElements(By.xpath("//*[@class=\"_mck9w _gvoze _f2mse\"]"));
	    
    	List<String>finalListofPhotos= m.generateFinalList(list);
	    System.out.println(finalListofPhotos);
	    System.out.println("List size "+ list.size());
	    
		MysqlDAO mysql = new MysqlDAO();
		
		for(int i=0;i<finalListofPhotos.size();i++){
    		
    		System.out.println("CurrentLink: "+finalListofPhotos.get(i).toString());
    		
    		driver.get(finalListofPhotos.get(i));
    		
    		PageManipulator manipulator = new PageManipulator();
    		try{
    			//like button
    			WebElement like  = driver.findElement(By.xpath("//*[@class=\"_8scx2 coreSpriteHeartOpen\"]"));
    			manipulator.clickElement(driver, like);
    			//profile icon
    			WebElement profileIcon = driver.findElement(By.xpath("//*[@class=\"_rewi8\"]"));
    			manipulator.clickElement(driver, profileIcon);
    			Thread.sleep(getRandomNumberInRange(1000,1200));
    		}catch(org.openqa.selenium.NoSuchElementException ex){
    			ex.printStackTrace();
    			System.out.println("error!");
    			continue;
    		}
    		
    		WebElement followIcon=null;
    		do{
    			try{
    				//follow button
    				followIcon = driver.findElement(By.xpath("//*[@class=\"_qv64e _gexxb _r9b8f _njrw0\"]"));
    				Thread.sleep(getRandomNumberInRange(1000,1300));
    				manipulator.clickElement(driver, followIcon);
    				mysql.insert(driver.getCurrentUrl(), 1);
    	    		Thread.sleep(getRandomNumberInRange(15000,18000));
    			}catch( org.openqa.selenium.StaleElementReferenceException ex){
    				ex.printStackTrace();
    				System.out.println("Element destroyed, trying again..");
    				
    				
    			}catch(org.openqa.selenium.NoSuchElementException ex){
        			ex.printStackTrace();
        			System.out.println("error break!");
        			break;
        		}
    			
    		}while(followIcon == null);
    		
    			
    		}
    	}
    	
	    //ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
	    
	    //driver.switchTo().window(tabs.get(0));

 ArrayList<String> generateFinalList(List<WebElement> list) throws InterruptedException{
	 ArrayList<String> links = new ArrayList();
	 for(WebElement e : list){
		
    	WebElement link = e.findElement(By.tagName("a")); 
    	String photoLink = link.getAttribute("href");
    	links.add(photoLink);
    }

	 return links;
	 
 }
 
  static int getRandomNumberInRange(int min, int max) {

		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}

		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}
}
