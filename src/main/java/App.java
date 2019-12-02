
import modules.ExcelGenerator;
import modules.SeleniumHandler;
import utils.Helper;

import java.io.FileNotFoundException;

import static utils.Constant.*;
import static utils.Constant.LAST_UPDATED;

public class App {


    public static void main(String[] args) throws FileNotFoundException {
        SeleniumHandler sel = new SeleniumHandler();
        //int totalPageCount = sel.login();
        //for(int i=1 ; i<=totalPageCount; i++){
        int totalPages = sel.login();

        //find the total no of excel file
        int count = Helper.getFileCount(".xlsx");
        count = count > 0 ? (count - 1) : 0;

        //skip pages in the website already crawled
        int skip_pages = (count * MAX_DATA_IN_EXCEL_FILE) / MAX_DATA_IN_WEBSITE_TABLE;

        for (int i = skip_pages; i < totalPages; i++) {
            String url = BASE_URL + "/contact/" + (i * MAX_DATA_IN_WEBSITE_TABLE);
            sel.getPageData(url);
        }
    }
}
