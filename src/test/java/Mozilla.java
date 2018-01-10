import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.Action;


public class Mozilla {
	

	public static void main(String[] args) throws InterruptedException, ClassNotFoundException, IOException, SQLException {
	    
	    MysqlDAO mysql = new MysqlDAO();
		PageManipulator manipulator = new PageManipulator();
		Mozilla m = new Mozilla();
	    
	    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	    
	    System.out.println("enter your username");
	    
		String username = reader.readLine();
		String password = null;
		
		String sql = "SELECT username FROM users WHERE username = '"+username+"';";
		String login =mysql.select(sql,"username");
		System.out.println(login);
		if(login.length()==0){
			System.out.println("Enter password");
			password = reader.readLine();
			String query = "INSERT INTO users (username, password) VALUES ('"+username+"','"+password+"');";
			mysql.insert(query);
		}else if (login.length()>0){
			String passwordQuery = "SELECT password FROM users WHERE username = '"+username+"'";
			password = mysql.select(passwordQuery,"password");
		}
	    
		//bbtour.info
		//tourapp45
		
		/*System.setProperty("webdriver.chrome.driver", "/home/chuck/workspace/HelloTest/chromedriver/chromedriver");
		WebDriver driver = new ChromeDriver();*/
		System.setProperty("webdriver.gecko.driver", "/home/chuck/eclipse/jee-neon/eclipse/mozilla/geckodriver");
	    WebDriver driver = new FirefoxDriver();
		driver.manage().window().maximize();
	    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	    driver.get("https://www.instagram.com/accounts/login/");
	    
	    WebElement loginField = driver.findElement(By.xpath("//*[@name=\"username\"]"));
	    loginField.sendKeys(username);
	    WebElement passwordField = driver.findElement(By.xpath("//*[@name=\"password\"]"));
	    passwordField.sendKeys(password);
	        
	    WebElement loginButton = driver.findElement(By.xpath("//*[@class=\"_qv64e _gexxb _4tgw8 _njrw0\"]"));
	    loginButton.click();
	    	
	    System.out.println("press 1 for follow, 2 - unfollow");
		
		String input = reader.readLine();
		
		
		
	    //follow block
		if (input.equals("1")){
			
	    	WebElement searchTagButton = driver.findElement(By.xpath("//*[@class=\"_avvq0 _o716c\"]"));
	    	searchTagButton.sendKeys("#animals"+ "\n");
	    	Thread.sleep(1000);
	    	searchTagButton.sendKeys(Keys.ENTER);
	    	Thread.sleep(1000);
	    	searchTagButton.sendKeys(Keys.ENTER);
	       
	    	
	    	for (int i=0;i < 1;i++){
	    		JavascriptExecutor jse = (JavascriptExecutor)driver;
	    		jse.executeScript("window.scrollBy(0,2000)", "");
	    		Thread.sleep(3000);
	    	}    
	    
	    	List<WebElement> list=driver.findElements(By.xpath("//*[@class=\"_mck9w _gvoze _f2mse\"]"));
	    
	    	List<String>finalListofPhotos= m.generateFinalList(list);
	    	System.out.println(finalListofPhotos);
	    	System.out.println("List size "+ list.size());
	    
	    	for(int i=0;i<finalListofPhotos.size();i++){
    		
	    		System.out.println("CurrentLink: "+finalListofPhotos.get(i).toString());
    		
	    		driver.get(finalListofPhotos.get(i));
	    		
	    		double followers = 0;
    			double following = 0;
    		
	    		try{
	    			//like button
	    			WebElement like  = driver.findElement(By.xpath("//*[@class=\"_8scx2 coreSpriteHeartOpen\"]"));
	    			manipulator.clickElement(driver, like);
	    			//profile icon
	    			WebElement profileIcon = driver.findElement(By.xpath("//*[@class=\"_rewi8\"]"));
	    			manipulator.clickElement(driver, profileIcon);
    			
	    			//followers block
	    			List<WebElement> followersClass=driver.findElements(By.xpath("//*[@class=\"_bnq48\"]"));
	    			
    			
	    			for(WebElement classElement :followersClass){
	    				
    						if(classElement.getText().contains("followers")){
    							followers = manipulator.getFollowersByClassText(classElement.getText());
    							System.out.println("follwers "+followers);
    						}else if(classElement.getText().contains("following")){
    							following = manipulator.getFollowersByClassText(classElement.getText());
    							System.out.println("following "+following);
    						}	
    				}
    			
	    			//end followers block
	    			if (followers / following >= 2.1 && followers <= 100000){
	    				Thread.sleep(getRandomNumberInRange(2000,2999));
	    				continue;
	    			}
    			
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
    				Thread.sleep(getRandomNumberInRange(200,500));
    				
    				String allUsersQuery = "INSERT INTO allUsers(login,followers,following) VALUES('"+driver.getCurrentUrl()+"','"+followers+"','"+following+"')";
    				mysql.insert(allUsersQuery);
    	    		Thread.sleep(getRandomNumberInRange(8000,10500));
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
	 
	 //unfollowBlock
	 }else if(input.equals("2")){
		 List<String> list = mysql.selectFollowers("1");
		 
		 for(int i=0;i<list.size();i++){
			 driver.get(list.get(i));
			 WebElement unfollowIcon = null;
			 do{
				 try{
					 
					 unfollowIcon = driver.findElement(By.xpath("//*[@class=\"_qv64e _t78yp _r9b8f _njrw0\"]"));
					 manipulator.clickElement(driver, unfollowIcon);
					 mysql.updateField("0",list.get(i));
					 System.out.println("AAA "+unfollowIcon);
					 System.out.println("Login: "+ list.get(i)+" unfollowed");
					 Thread.sleep(getRandomNumberInRange(1000,1500));
					 
				 }catch( org.openqa.selenium.StaleElementReferenceException ex){
					 unfollowIcon = null;
					 ex.printStackTrace();
					 System.out.println("Element destroyed, trying again..");	
				 
				 }catch(org.openqa.selenium.NoSuchElementException ex){
	        			ex.printStackTrace();
	        			System.out.println("no element!");
	        			break;
	        			
	        	}
				 
			 }while(unfollowIcon == null);
			 
		 }
	 
	 }else if(input.equals("3")){
		 driver.get("https://www.instagram.com/nesquic/following/");
		 List<WebElement> list=driver.findElements(By.xpath("//*[@class=\"_2g7d5 notranslate _o5iw8\"]"));
		 ArrayList<String> unfollowAccounts = m.generateFinalList(list);
		 System.out.println(list);
		 
		 
	 }
}


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
