import org.checkerframework.checker.units.qual.C;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class xpath {
    String website = "https://www.hyrtutorials.com/p/add-padding-to-containers.html";
    WebDriver driver ;
    @Before
    public void init(){
        driver = new ChromeDriver();
    }
    @Test
    public void getFirstName(){
        driver.get(website);
        String xpath =  "//div[@class='container']/label[2]";
        WebElement element = driver.findElement(By.xpath(xpath));
        System.out.println(element.getText());
    }
    @Test
     public void setFirstName(){
        driver.get(website);
        String xpath =  "//div[@class='container']/input[1]";
        WebElement element = driver.findElement(By.xpath(xpath));
        element.sendKeys("Rodrigo ");
    }
}
