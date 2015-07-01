package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class XlsToCsv 
{
    public static File xlsx(File inputFile) {
        // For storing data into CSV files
    	String nameWithoutExtension = inputFile.getName().substring(0, inputFile.getName().length()-4);
    	File outputFile = new File(nameWithoutExtension+".csv");
        StringBuffer data = new StringBuffer();

        try {
            FileOutputStream fos = new FileOutputStream(outputFile);
            // Get the workbook object for XLSX file
            XSSFWorkbook wBook = new XSSFWorkbook(new FileInputStream(inputFile));
            // Get first sheet from the workbook
            XSSFSheet sheet = wBook.getSheetAt(0);
            Row row;
            Cell cell;
            // Iterate through each rows from first sheet
            Iterator<Row> rowIterator = sheet.iterator();

            while (rowIterator.hasNext()) 
            {
                row = rowIterator.next();

                // For each row, iterate through each columns
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {

                    cell = cellIterator.next();

                    switch (cell.getCellType()) {
                        case Cell.CELL_TYPE_BOOLEAN:
                            data.append(cell.getBooleanCellValue() + ",");

                            break;
                        case Cell.CELL_TYPE_NUMERIC:
                            data.append(cell.getNumericCellValue() + ",");

                            break;
                        case Cell.CELL_TYPE_STRING:
                            data.append(cell.getStringCellValue() + ",");
                            break;

                        case Cell.CELL_TYPE_BLANK:
                            data.append("" + ",");
                            break;
                        default:
                            data.append(cell + ",");

                    }
                }
            }

            fos.write(data.toString().getBytes());
            fos.close();

        } catch (Exception ioe) {
            ioe.printStackTrace();
        }
        return outputFile;
    }
    public static File xls(File inputFile) {
        // For storing data into CSV files
    	String nameWithoutExtension = inputFile.getPath().substring(0, inputFile.getPath().length()-4);
    	File outputFile = new File(nameWithoutExtension+".tmc");
        StringBuffer data = new StringBuffer();

        try {
            FileOutputStream fos = new FileOutputStream(outputFile);
            // Get the workbook object for XLSX file
            HSSFWorkbook  wBook = new HSSFWorkbook (new FileInputStream(inputFile));
            // Get first sheet from the workbook
            HSSFSheet  sheet = wBook.getSheetAt(0);
            Row row;
            Cell cell;
            // Iterate through each rows from first sheet
            Iterator<Row> rowIterator = sheet.iterator();
            System.out.println("Trying to convert the xls file after reading");
            while (rowIterator.hasNext()) 
            {
                row = rowIterator.next();

                // For each row, iterate through each columns
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {

                    cell = cellIterator.next();

                    switch (cell.getCellType()) {
                        case Cell.CELL_TYPE_BOOLEAN:
                            data.append(cell.getBooleanCellValue() + ";");

                            break;
                        case Cell.CELL_TYPE_NUMERIC:
                            data.append(cell.getNumericCellValue() + ";");

                            break;
                        case Cell.CELL_TYPE_STRING:
                            data.append(cell.getStringCellValue() + ";");
                            break;

                        case Cell.CELL_TYPE_BLANK:
                            data.append("" + ";");
                            break;
                        default:
                            data.append(cell + ";");

                    }
                }
                data.append("\n");
            }
            fos.write(data.toString().getBytes());
            fos.close();
            System.out.println("File writed to " + outputFile.getPath());
        } catch (Exception ioe) 
        {
        	String outputIOE = ioe.getMessage();
        	if(outputIOE.contains("0x6D78206C6D74683C"))
        	{
        		String nameHTML = inputFile.getPath().substring(0, inputFile.getPath().length()-4)+".html";
        		OperatingSystem.copyRecentFile(inputFile, new File(nameHTML));
        	}
            //ioe.printStackTrace();
        }
        return outputFile;
    }

}
