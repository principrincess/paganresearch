package modules;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.w3c.dom.Node;
import utils.Helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;

import static utils.Constant.*;


public class SeleniumHandler {

    public WebDriver driver;
    public HashMap<String, ArrayList<String>> resultMap;

    public SeleniumHandler() {
        System.setProperty("webdriver.gecko.driver", "C:/Webdriver/geckodriver.exe");
        this.driver = new FirefoxDriver();
        this.resultMap = Helper.createEmptyHashMap(COL_NAME);
    }

    public void updateExcel() {
        int rowSize = this.resultMap.get(COL_NAME[0]).size();
        System.out.println("hashmap size  :" + rowSize);
        if (rowSize >= MAX_DATA_IN_EXCEL_FILE) {
            String fileName = "result.xlsx";
            String sheetName = "Company-Data";
            Helper.generateExcel(fileName, sheetName, this.resultMap);
            Helper.flushHashMap(this.resultMap);
        }
    }

    public void getPageData() {
        Helper.waitForPageReload(this.driver);
        String htmlSourceCode = Helper.getSourceCode(this.driver);
        Document doc = Jsoup.parse(htmlSourceCode);
        Elements tableRows = doc.select(".people-search-list");

        for (int i = 0; i < tableRows.size(); i++) {

            String name, company, designation, email, lastUpdated, phone;
            name = company = designation = email = lastUpdated = phone = "";

            Element row = tableRows.get(i);
            Elements directChildDiv = row.select(".people-search-list > div");


            try {
                name = directChildDiv.select("div").get(0).select("a").get(0).text();
                System.out.println( name);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("name not Found");
            }

            try {
                Element designationDiv = directChildDiv.get(1);
                designation = ((TextNode) designationDiv.children().get(0).childNodes().get(0)).text();
                company = designationDiv.select("a").get(0).text();
                System.out.println(company + ">>>" + designation);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("company not Found");
            }

            try {
                Elements emailPhoneDiv = directChildDiv.get(2).select("p");
                email = emailPhoneDiv.select("a").get(0).text();
                System.out.println(email);
                if (emailPhoneDiv.size() == 2) {
                    phone = emailPhoneDiv.select("strong").get(0).text();
                    System.out.println(phone);

                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("contact not Found");
            }

            try {
                Element lastUpdatedDiv = directChildDiv.get(3);
                lastUpdated = lastUpdatedDiv.select("p").get(0).text();
                System.out.println(lastUpdated);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("lastupdate not Found");
            }

            HashMap<String, String> result = new HashMap<String, String>();
            result.put(NAME, name);
            result.put(COMPANY, company);
            result.put(EMAIL, email);
            result.put(PHONE, phone);
            result.put(DESIGNATION, designation);
            result.put(LAST_UPDATED, lastUpdated);

            Helper.updateHashMap(this.resultMap, result);
        }

        updateExcel();
        //            this.driver.findElement(By.cssSelector())

    }


    public void login() {
        this.driver.get(LOGIN_URL);
        Helper.waitForPageReload(this.driver);
        System.out.println("Website open successfully");


//        UserName Input
        this.driver.findElement(By.xpath("/html/body/div/div/div[1]/div/div[2]/form/input[1]")).sendKeys(USERNAME);

        this.driver.findElement(By.xpath("/html/body/div/div/div[1]/div/div[2]/form/input[2]")).sendKeys(PASSWORD);
        this.driver.findElement(By.xpath("/html/body/div/div/div[1]/div/div[2]/form/input[3]")).click();
        System.out.println("Login Successfully");

//        Fin Contact Btn
        this.driver.findElement(By.xpath("/html/body/header/div/div/div/div/div[2]/div[2]/ul/li[2]/a")).click();
    }
}
