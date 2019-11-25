package modules;//package tutorialselenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class login {
    public WebDriver driver;

    public static HashMap createEmptyObject() {
        HashMap map = new HashMap();
        map.put("NAME", new ArrayList<String>());
        map.put("COMPANY", new ArrayList<String>());
        map.put("EMAIL", new ArrayList<String>());
        map.put("LAST_UPDATED", new ArrayList<String>());
        return map;
    }

    public static void main(String[] args) {
        System.setProperty("webdriver.gecko.driver", "C:/Webdriver/geckodriver.exe");
        WebDriver driver = new FirefoxDriver();
        // Maximize the browser's window
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("https://paganresearch.io/login");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.findElement(By.xpath("/html/body/div/div/div[1]/div/div[2]/form/input[1]")).sendKeys("sanjay@proforte.co");
        driver.findElement(By.xpath("/html/body/div/div/div[1]/div/div[2]/form/input[2]")).sendKeys("Proforte@1234");
        driver.findElement(By.xpath("/html/body/div/div/div[1]/div/div[2]/form/input[3]")).click();

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.findElement(By.xpath("/html/body/header/div/div/div/div/div[2]/div[2]/ul/li[2]/a")).click();

        ///html/body/section[2]/div/div[3]/div[2]/div[1]/div/a  1st column xpath
        //html/body/section[2]/div/div[3]/div[3]/div[1]/div/a

        ///html/body/section[2]/div/div[3]/div[2]/div[2]/div    2nd column xpath
        ///html/body/section[2]/div/div[3]/div[3]/div[2]/div


        String beforepath_Name = "/html/body/section[2]/div/div[3]/div[";
        String afterpath_Name = "]/div[1]/div/a";

        String beforepath_Company = "/html/body/section[2]/div/div[3]/div[";
        String afterpath_Company = "]/div[2]/div";

        String beforepath_Contact = "/html/body/section[2]/div/div[3]/div[";
        String afterpath_Contact = "]/div[3]/div/p/a";

        String beforepath_LastUpdate = "/html/body/section[2]/div/div[3]/div[";
        String afterpath_LastUpdate = "]/div[4]/div/p";

        HashMap resultMap = createEmptyObject();

        for (int i = 2; i < 30; i++) {

            String Name, Company, Contact, LastUpdate = "";

            String actualpath_Name = beforepath_Name + i + afterpath_Name;
            Name = driver.findElement(By.xpath(actualpath_Name)).getText();
            System.out.println(Name);
            ((ArrayList) resultMap.get("NAME")).add(Name);

            String actualpath_Company = beforepath_Company + i + afterpath_Company;
            Company = driver.findElement(By.xpath(actualpath_Company)).getText();
            System.out.println(Company);
            ((ArrayList) resultMap.get("COMPANY")).add(Company);


            String actualpath_Contact = beforepath_Contact + i + afterpath_Contact;
            Contact = driver.findElement(By.xpath(actualpath_Contact)).getText();
            ((ArrayList) resultMap.get("EMAIL")).add(Contact);


            String actualpath_LastUpdate = beforepath_LastUpdate + i + afterpath_LastUpdate;
            LastUpdate = driver.findElement(By.xpath(actualpath_LastUpdate)).getText();
            ((ArrayList) resultMap.get("LAST_UPDATED")).add(LastUpdate);


        }














        // Find departing field
//        driver.findElement(By.xpath("//*[@id=\"divMain\"]/div/app-main-page/div[1]/div/div[1]/div/div/div[1]/div/app-jp-input/div[3]/form/div[3]/p-calendar/span/input")).click();
        //  Thread.sleep(3000);
        // Find the date to be selected
//        driver.findElement(By.xpath("//*[@id=\"divMain\"]/div/app-main-page/div[1]/div/div[1]/div/div/div[1]/div/app-jp-input/div[3]/form/div[3]/p-calendar/span/div/table/tbody/tr[1]/td[4]/a")).click();

        /*String departureDate = "28-10-2019";
        String departureQuery = "document.querySelector(\"input[placeholder='Journey Date(dd-mm-yyyy)*']\").value = '" + departureDate + "' ";
        System.out.println(departureQuery);
        if (driver instanceof JavascriptExecutor) {
            ((JavascriptExecutor)driver).executeScript(departureQuery);
        } else {
            throw new IllegalStateException("This driver does not support JavaScript!");
        }*/


    }
}