
import modules.ExcelGenerator;
import modules.SeleniumHandler;

import java.io.FileNotFoundException;

import static utils.Constant.*;
import static utils.Constant.LAST_UPDATED;

public class App {


    public static void main(String[] args) throws FileNotFoundException {
        SeleniumHandler sel = new SeleniumHandler();
        sel.login();
        sel.getPageData();


    }
}
