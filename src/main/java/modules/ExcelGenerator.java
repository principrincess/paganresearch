package modules;/*
package modules;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class ExcelGenerator {

    public static String excelFileName = "C:/Users/CHANCHAL/Desktop/TestingData.xlsx";//name of excel file
    public static String sheetName = "Sheet1";//name of sheet


    XSSFWorkbook wb = new XSSFWorkbook();
    XSSFSheet sheet = wb.createSheet("sheet2") ;

    public void inputData() throws FileNotFoundException {
        FileOutputStream fileOut = new FileOutputStream("TestingData.xlsx");
    }

}
*/


import org.apache.poi.ss.formula.SheetNameFormatter;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.util.*;
import java.util.HashMap;

public class ExcelGenerator {


    public static void generateExcel(String fileName, HashMap<String, ArrayList<String>> map) {

        XSSFWorkbook workbook = new XSSFWorkbook();

        // Create a blank sheet
        XSSFSheet sheet = workbook.createSheet("CompanyData");

        // Creating Bold Style for  Worksheet
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        ((XSSFFont) font).setBold(true);
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


        for (int i = 0; i < maxRowSize; i++) {

            // this creates a new row in the sheet
            row = sheet.createRow(i + 1);

            for (int j = 0; j < keyset.length; j++) {
                // this line creates a cell in the next column of that row
                Cell cell = row.createCell(j);
                String cellVal = map.get((String) keyset[j]).get(i);
                cell.setCellValue(cellVal);
            }
        }

        try {
            // this Writes the workbook on excel
            FileOutputStream out = new FileOutputStream(new File(fileName));
            workbook.write(out);
            out.close();
            System.out.println(fileName + " written successfully on disk.");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static void main(String[] args) {

        ArrayList<String> al = new ArrayList<String>();
        ArrayList<String> al1 = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            al.add("" + i);
            al1.add("Person" + i);
        }


        HashMap<String, ArrayList<String>> mp = new HashMap<String, ArrayList<String>>();
        mp.put("A1", al);
        mp.put("A2", al1);

        String fileName = "TestingData.xlsx";
        ExcelGenerator.generateExcel(fileName, mp);
    }

    public void generateExcel() {
    }
}

