package utilites;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


//class has all methods handle excel file so we can call them any time and use them easily
public class ExcelUtility {

    private FileInputStream fis = null;
    private FileOutputStream fileOut = null;
    private String projpath = null;
    private static XSSFWorkbook workbook = null;
    private static XSSFSheet sheet = null;
    private static XSSFRow row = null;
    private static XSSFCell cell = null;
    String path = null;


    public ExcelUtility(String fileName) {
        try {
            path = System.getProperty("user.dir") + "\\Excel\\" + fileName;
            fis = new FileInputStream(path);
            workbook = new XSSFWorkbook(fis);
            sheet = workbook.getSheetAt(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int getRowCount(String sheetName) {
        try {

            int index = workbook.getSheetIndex(sheetName);
            sheet = workbook.getSheetAt(index);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            ;
            System.out.println(e.getCause());
            e.printStackTrace();
        }
        return (sheet.getLastRowNum() + 1);
    }

    public static int getColCount(String sheetName) {
        try {
            //Provide total number of columns in sheet - testcase
            int index = workbook.getSheetIndex(sheetName);
            sheet = workbook.getSheetAt(index);
            row = sheet.getRow(0);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            ;
            System.out.println(e.getCause());
            e.printStackTrace();
        }
        return (row.getLastCellNum());
    }

    public String getCellDataString(String sheetName, int rowNum, int colNum) {
        try {
            int index = workbook.getSheetIndex(sheetName);
            sheet = workbook.getSheetAt(index);
            //  cell.setCellType(Cell.CELL_TYPE_STRING);
            row = sheet.getRow(rowNum);
            cell = row.getCell(colNum);
            CellType type = cell.getCellTypeEnum();
            if (type == CellType.STRING) {
                return (cell.getStringCellValue().toString());
            } else if (type == CellType.NUMERIC) {
                Double d = new Double(cell.getNumericCellValue());
                return d.toString();
            } else if (type == CellType.BLANK) {
                return "";
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            ;
            System.out.println(e.getCause());
            e.printStackTrace();
        }

        return (cell.getStringCellValue());
    }

    public static int getCellDataNumber(String sheetName, int rowNum, int colNum) {

        int cellData = (int) 0.0;
        try {
            int index = workbook.getSheetIndex(sheetName);
            sheet = workbook.getSheetAt(index);
            row = sheet.getRow(rowNum);
            cell = row.getCell(colNum);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            ;
            System.out.println(e.getCause());
            e.printStackTrace();
        }
        cellData = (int) cell.getNumericCellValue();
        return cellData;
    }

    public void setCellData(String sheetName, int colNum, int rowNum, String str) {
        int index = workbook.getSheetIndex(sheetName);
        sheet = workbook.getSheetAt(index);
        row = sheet.getRow(rowNum);
        cell = row.createCell(colNum);
        cell.setCellValue(str);

        try {
            fileOut = new FileOutputStream(path);
            try {
                workbook.write(fileOut);
                fileOut.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


    public void fillarrayfromeexcel(Object[][] arr, String sheetname) {
        for (int row = 0; row < arr.length; row++) {
            for (int col = 0; col < arr[row].length; col++) {
                arr[row][col] = this.getCellDataString(sheetname, row, col);
            }
        }
    }


}
