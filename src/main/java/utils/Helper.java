package utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import static utils.Constant.*;

public class Helper {


    public static void generateExcel(String fileName, String sheetName, HashMap<String, ArrayList<String>> map) {

        ///create workbook
        XSSFWorkbook workbook = new XSSFWorkbook();


        // Create a blank sheet
        XSSFSheet sheet = workbook.createSheet(sheetName);

        // Creating Bold Style for  Worksheet
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);

        // List of Key of HashMap
        Object[] keyset = map.keySet().toArray();

        int maxRowSize = map.get((String) keyset[0]).size();

        // Write Header File First
        Row row = sheet.createRow(0);
        for (int p = 0; p < keyset.length; p++) {
            // this line creates a cell in the next column of that row
            Cell cell = row.createCell(p);
            cell.setCellStyle(style);
            cell.setCellValue((String) keyset[p]);
        }

        // Printing the Array List Data as Row

        for (int i = 0; i < maxRowSize; i++) {

            // this creates a new row in the sheet (Starts from Row 2)
            row = sheet.createRow(i + 1);

            for (int j = 0; j < keyset.length; j++) {
                // this line creates a cell in the next column of that row
                Cell cell = row.createCell(j);
                String key = (String) keyset[j];
                String cellVal = map.get(key).get(i);
                cell.setCellValue(cellVal);
            }

            // Here i = row, j = col
        }

        while (true) {
            try {
                // this Writes the workbook on excel
                FileOutputStream out = new FileOutputStream(new File(fileName));
                workbook.write(out);
                out.close();
                System.out.println(fileName + " written successfully on disk.");
                break;
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(fileName + " already Open. Please close the file . Retrying in 10 sec..... ");
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException ex) {

                }
            }
        }

    }


    public static HashMap createEmptyHashMap(String[] keys) {
        HashMap<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();

        for (String key : keys) {
            map.put(key, new ArrayList<String>());
        }
        return map;
    }

    //    Both HasmMap Key should be same
    public static void updateHashMap(HashMap<String, ArrayList<String>> largeResult, HashMap<String, String> shortResult) {
        Set<String> keys = largeResult.keySet();
        for (String key : keys) {
            String val = shortResult.get(key);
            largeResult.get(key).add(val);
        }
    }

    public static void flushHashMap(HashMap<String, ArrayList<String>> map) {
        Set<String> keys = map.keySet();
        for (String key : keys) map.get(key).clear();
    }

    public static void waitForPageReload(WebDriver driver) {
        new WebDriverWait(driver, PAGE_TIMEOUT).until(
                (webDriver) -> {
                    return ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete");
                });
    }

    public static String getSourceCode(WebDriver driver) {
        return driver.getPageSource();
    }

    public static int getFileCount(String fileType) {
        int fileCount = 0;
        Path currentRelativePath = Paths.get("");
        String absolutePath = currentRelativePath.toAbsolutePath().toString();
        try (Stream<Path> walk = Files.walk(Paths.get(absolutePath))) {
            List<String> result = walk.map(x -> x.toString())
                    .filter(f -> f.endsWith(fileType)).collect(Collectors.toList());
          //  result.forEach(System.out::println);
            fileCount = result.size();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileCount;
    }


}


