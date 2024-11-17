import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;


import javax.swing.*;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class test {
    String website = "https://the-internet.herokuapp.com/";
    WebDriver chrome;
    WebDriverWait wait;

    @Before
    public void init(){

        chrome = new ChromeDriver();
        wait = new WebDriverWait(chrome,Duration.ofSeconds(10));

    }
    //@Test
    public void openSite(){
        chrome.get(website);
    }
    //@Test
    public void addRemoveElements()  {
        String addXpath = "//div[@class='example']//button[text()='Add Element']";
        String deleteName = "//div[@id='elements']//button[text()='Delete']";
        int nClick = 5;
        chrome.get(website+ "add_remove_elements/");
        clickElement(addXpath,nClick);
        clickElement(deleteName,4);
    }

   // @Test
    public void digestauthenticationTest(){
         HasAuthentication auth = (HasAuthentication) chrome;
         auth.register(() -> new UsernameAndPassword("admin", "admin"));

        chrome.get(website+"digest_auth");

    }
   // @Test
    public void basicauthenticationTest(){
        HasAuthentication auth = (HasAuthentication) chrome;
        auth.register(() -> new UsernameAndPassword("admin", "admin"));

        chrome.get(website+"basic_auth");

    }
    //@Test
    public void testImage() throws IOException {
        chrome.get(website+"broken_images");
        List <WebElement> images = chrome.findElements(By.tagName("img"));
        for(WebElement element: images){

        String srcImage = element.getAttribute("src");
        System.out.println(srcImage);
        assertEquals(200,getRequest(srcImage));
        }

    }
    //@Test
    public void testDynamicButtons(){

        chrome.get(website+"challenging_dom");
        String xpathbutton  = "//div[@class='large-2 columns']/a[1]";
        String xpathbuttonAlert  = "//div[@class='large-2 columns']/a[2]";
        String xpathbuttonSuccess  = "//div[@class='large-2 columns']/a[3]";
        WebElement button = chrome.findElement(By.xpath(xpathbutton));
        clickElement(button,xpathbutton,2);
        WebElement buttonAlert =  wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathbuttonAlert)));
        clickElement(buttonAlert,xpathbuttonAlert,3);
        WebElement buttonSuccess =  wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathbuttonSuccess)));
        clickElement(buttonSuccess,xpathbuttonSuccess,3);

    }


    //@Test
    public void testTableData(){
        chrome.get(website+"challenging_dom");
        String tableXpath = "//div[@class='large-10 columns']";
        List<String> rowData = getRowData(tableXpath,3);
        List<String> columnData = getColumnData(tableXpath,2);
        printList(columnData);
    }
    //@Test
    public void testCheckBox(){
        String xpathcheckbox1 = "//form[@id='checkboxes']/input[1]";
        String xpathcheckbox2 = "//form[@id='checkboxes']/input[2]";
        chrome.get("https://the-internet.herokuapp.com/checkboxes");

        clickElement(xpathcheckbox1,10);
        clickElement(xpathcheckbox2,10);
    }
    //@Test
    public void testContextMenu() throws InterruptedException {
        chrome.get(website+"context_menu");
        Actions action = new Actions(chrome);
        WebElement contextMenu = chrome.findElement(By.id("hot-spot"));
        action.contextClick(contextMenu).perform();
        chrome.switchTo().alert().accept();
    }

    //@Test
    public void testDisappearingElement(){
        By gallerXpath = By.xpath("//div[@class='large-12 columns']//li/a[text()='Gallery']");
        chrome.get(website+"disappearing_elements");
        while(!isElementPresent(gallerXpath)){
            chrome.navigate().refresh();
        }
        WebElement gallery = chrome.findElement(gallerXpath);
        gallery.click();

    }
    //@Test
    public void testDragnDrop(){
        chrome.get(website+"drag_and_drop");
        Actions action = new Actions(chrome);
        WebElement columnA = chrome.findElement(By.id("column-a"));
        WebElement columnB = chrome.findElement(By.id("column-b"));
        Action dragNdrop = action.clickAndHold(columnA)
                .moveToElement(columnB)
                .release(columnB).build();
        dragNdrop.perform();
        dragNdrop.perform();
    }
    //@Test
    public void selectElement(){
        chrome.get(website+"dropdown");
        WebElement selectElement = chrome.findElement(By.id("dropdown"));
        Select select = new Select(selectElement);
        List <WebElement> dropDownValues = select.getOptions();
        printWebElementList(dropDownValues);
        select.selectByValue("1");
        select.selectByValue("2");
    }
    //@Test
    public void textDynamicContent(){
        chrome.get(website+"dynamic_content?with_content=static");
        By textLocator = By.xpath("//div[@class='large-10 columns']");
        List <WebElement> textElements = chrome.findElements(textLocator);
        printWebElementList(textElements);
    }
    //@Test
    public void imageDynamicContent() throws IOException {
        chrome.get(website+"dynamic_content");
        By textLocator = By.xpath("//div[@class='large-2 columns']//img");
        List <WebElement> imageElements = chrome.findElements(textLocator);
        for (WebElement imageElement : imageElements){
           System.out.println(getRequest(imageElement.getAttribute("src")));
        }
    }
    //@Test
    public void dynamicControlTest(){
        chrome.get(website+"dynamic_controls");
        By remove = By.xpath("//form[@id='checkbox-example']/button");
        By inputText = By.xpath("//form[@id='input-example']/input");
        By checkBox = By.xpath("//div[@id='checkbox']/input");
        By enableDisable = By.xpath("//form[@id='input-example']/button");

        WebElement checkbox = chrome.findElement(checkBox);
        checkbox.click();

        WebElement removeButton = chrome.findElement(remove);
        removeButton.click();

        WebElement addButton = wait.until(ExpectedConditions.elementToBeClickable(remove));
        addButton.click();
        By checkBox2 = By.xpath("//div//input[@id='checkbox']");
        WebElement waitedCheckbox = wait.until(ExpectedConditions.visibilityOfElementLocated(checkBox2));
        waitedCheckbox.click();
        WebElement inputTextElement = chrome.findElement(inputText);
        WebElement enableDisableButton = chrome.findElement(enableDisable);
        if(!inputTextElement.isEnabled()) {
            enableDisableButton.click();
        }
        inputTextElement = wait.until(ExpectedConditions.elementToBeClickable(inputTextElement));
        inputTextElement.sendKeys("This is enabled");

    }
    //@Test
    public void dynamic_loading(){
        chrome.get(website+"dynamic_loading/2");
        WebElement element = chrome.findElement(By.xpath("//div[@id='start']/button[1]"));
        element.click();
        By textelement = By.xpath("//div[@id='finish']/h4");
        WebElement textElemnt = wait.until(ExpectedConditions.visibilityOfElementLocated(textelement));
        System.out.println(textElemnt.getText());
    }
    @Test
    public void modalFooter() {
        chrome.get(website+"entry_ad");
        By element = By.xpath("//div[@class='modal-footer']/p");
        WebElement closeElement = wait.until(ExpectedConditions.visibilityOfElementLocated(element));
        closeElement.click();

    }




    public boolean isElementPresent(By locator) {
        return !chrome.findElements(locator).isEmpty();
    }
    public String getRowDataXpath(String tableXpath,int row){

        String tableRowXpath = "//tr["+String.valueOf(row)+"]";
        String tableColumnXpath = "/td";
        String firstData = tableXpath+tableRowXpath+tableColumnXpath;
        return firstData;
    }
    public List<String> getRowData(String tableXpath, int row){
        List <WebElement> element =  chrome.findElements(By.xpath(getRowDataXpath(tableXpath,row)));
        List <String> rowData = new ArrayList<>();

        for (int i  = 0 ; i < element.size(); i++){
            rowData.add(element.get(i).getAccessibleName());
        }
        return rowData;

    }
    public String getColumnDataXpath(String tableXpath, int column) {
        // Construct the XPath to locate all cells in a specific column
        String columnXpath = tableXpath + "//tr/td[" + column + "]";
        return columnXpath;
    }
    public List<String> getColumnData(String tableXpath, int column){
        List <WebElement> element =  chrome.findElements(By.xpath(getColumnDataXpath(tableXpath,column)));
        List <String> columnData = new ArrayList<>();

        for (int i  = 0 ; i < element.size(); i++){
            columnData.add(element.get(i).getAccessibleName());
        }
        return columnData;

    }
    public void printList(List<String> list){
        for (String listData : list){
            System.out.print(listData + " ");
        }
    }
    public void printWebElementList(List<WebElement> list){
        for (WebElement listData : list){
            System.out.println(listData.getText());
        }
    }


    public int getRequest(String url) throws IOException {
    URL webUrl = new URL(url);
    HttpURLConnection con = (HttpURLConnection) webUrl.openConnection();
    con.setRequestMethod("GET");
    return con.getResponseCode();
    }




    public void clickElement(WebElement element, String xpath, int numberOfTimes){

        for (int i = 0 ; i < numberOfTimes; i++){
            element.click();
            element = chrome.findElement(By.xpath(xpath));
            System.out.println(element.getAccessibleName());
        }

    }

    public void clickElement(String xpath,int numberOfTimes){
        WebElement element = chrome.findElement(By.xpath(xpath));

        for (int i = 0 ; i < numberOfTimes; i++){
            element.click();
            element = chrome.findElement(By.xpath(xpath));
            System.out.println(element.getAccessibleName());
        }

    }





}
