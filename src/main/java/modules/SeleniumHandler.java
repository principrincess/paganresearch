package modules;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import utils.Helper;

import java.util.ArrayList;
import java.util.HashMap;

import static utils.Constant.*;


public class SeleniumHandler {

    public WebDriver driver;
    public HashMap<String, ArrayList<String>> resultMap;

    //initialize webdriver
    public SeleniumHandler() {
        while (true) {
            try {
                System.setProperty("webdriver.gecko.driver", "C:/Webdriver/geckodriver.exe");
                this.driver = new FirefoxDriver();
                this.resultMap = Helper.createEmptyHashMap(COL_NAME);
                break;
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Unable to initialize app. Retrying again......");
            }
        }
    }


    //enter data into excel
    public void updateExcel() {
        int rowSize = this.resultMap.get(COL_NAME[0]).size();
        System.out.println("hashmap size  :" + rowSize);
        if (rowSize >= MAX_DATA_IN_EXCEL_FILE) {
            //return total exel file aleady generated
            int count = Helper.getFileCount(".xlsx");
            String fileName = "result_" + count + ".xlsx";
            String sheetName = "Company-Data";
            Helper.generateExcel(fileName, sheetName, this.resultMap);
            Helper.flushHashMap(this.resultMap);
        }
    }


    //get the data from the website
    public void getPageData(String url) {

        while (true) {
            try {
                this.driver.get(url);
                Helper.waitForPageReload(this.driver);
                break;
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Some error occurred while re-login. Retrying again....");
            }
        }


        //get the source code of the page using jsoup
        String htmlSourceCode = Helper.getSourceCode(this.driver);
        Document doc = Jsoup.parse(htmlSourceCode);

        //check weather user in logged in or not
        if (doc.select(".user_login_menu ul").size() > 0) {
            System.out.println(" User Logged In");
        } else {
            while (true) {
                try {
                    login();
                    this.driver.get(url);
                    Helper.waitForPageReload(this.driver);
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Some error occurred while re-login. Retrying again....");
                }
            }

            htmlSourceCode = Helper.getSourceCode(this.driver);
            doc = Jsoup.parse(htmlSourceCode);
        }

        Elements tableRows = doc.select(".people-search-list");

        for (int i = 0; i < tableRows.size(); i++) {


            //initialize column name with empty string............
            String name, company, designation, email, lastUpdated, phone;
            name = company = designation = email = lastUpdated = phone = "";

            Element row = tableRows.get(i);
            Elements directChildDiv = row.select(".people-search-list > div");

            //get the data from 1st column
            try {
                name = directChildDiv.select("div").get(0).select("a").get(0).text();
                System.out.println(name);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("name not Found");
            }

            //get the data from 2nd column
            try {
                Element designationDiv = directChildDiv.get(1);
                designation = ((TextNode) designationDiv.children().get(0).childNodes().get(0)).text();
                company = designationDiv.select("a").get(0).text();
                System.out.println(company + ">>>" + designation);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("company not Found");
            }

            //get the data from 3rd column
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

            //get the data from 4th column
            try {
                Element lastUpdatedDiv = directChildDiv.get(3);
                lastUpdated = lastUpdatedDiv.select("p").get(0).text();
                System.out.println(lastUpdated);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("lastupdate not Found");
            }

            //creating hashmap for single row everytime
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


        /*String xpathExpression;*/

        //click on next button
        // boolean isEnables = driver.findElement(new By.ByCssSelector(".pagination > li:nth-child(5) > a:nth-child(1)")).isEnabled();

//        do
//        {
//            WebElement element = driver.findElement(By.LINK_TEXT("Next"));
//             String classAttribute = element.GetAttribute("class");
//            element.click()
//        }
//        while(!classAttribute.contains("disabled"));

      /*  WebElement element = driver.findElement(new By.ByCssSelector(".pagination > li:nth-child(5) > a:nth-child(1)"));
        if (element.isEnabled()) {
            element.click();
        }*/

        // this.driver.findElement(By.cssSelector(".pagination > li:nth-child(5) > a:nth-child(1)")).isEnabled()
    }

    public int login() {
        while (true) {
            try {
                this.driver.get(LOGIN_URL);
                Helper.waitForPageReload(this.driver);
                System.out.println("Website open successfully");


//        UserName Input
                this.driver.findElement(By.xpath("/html/body/div/div/div[1]/div/div[2]/form/input[1]")).sendKeys(USERNAME);
                this.driver.findElement(By.xpath("/html/body/div/div/div[1]/div/div[2]/form/input[2]")).sendKeys(PASSWORD);
                this.driver.findElement(By.xpath("/html/body/div/div/div[1]/div/div[2]/form/input[3]")).click();
                System.out.println("Login Successfully");

//        Find Contact Btn
                this.driver.findElement(By.xpath("/html/body/header/div/div/div/div/div[2]/div[2]/ul/li[2]/a")).click();
//        Find total pages available in the website
                Helper.waitForPageReload(this.driver);

                //find the text of total contacts
                String maxContacts = driver.findElement(By.xpath("/html/body/section[2]/div/div[2]/form/div/div[2]/div/div[2]/p[1]")).getText();

                //find the total number of pages
                int totalPages = (int) (Math.ceil(Float.parseFloat(maxContacts) / MAX_DATA_IN_WEBSITE_TABLE));
                System.out.println("Total No of Pages :::: " + totalPages);

                return totalPages;
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(" Exception in Login. Retrying again..... ");

            }
        }

    }
}
