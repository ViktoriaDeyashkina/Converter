import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.util.ArrayList;

public class ExcelWorker {

    XSSFSheet sheet;
    ArrayList<String> listForHeaders;
    ArrayList<String> listForFilling;
    String sheetName;

    ExcelWorker (ArrayList<String> listForHeaders, ArrayList<String> listForFilling, String sheetName){
        this.listForHeaders = listForHeaders;
        this.listForFilling = listForFilling;
        this.sheetName = sheetName;
    }

    public void start() {
       createSheets(sheetName);
       createHeader(listForHeaders);
       fillTable(listForFilling);
    }
    public void createSheets(String sheetName){
        sheet = Copy.workbook.createSheet(sheetName);

    }
    public void createHeader(ArrayList<String>listForHeaders){
        int rowNum = 0;
        Row rowhead = sheet.createRow(rowNum);
        String[] str = listForHeaders.get(0).split("\t");
        for (int i=0; i< str.length; i++){
            rowhead.createCell(i).setCellValue(str[i]);
        }
    }

    public void fillTable(ArrayList<String> listForFilling){
        int rowNum=0;
        for (int i = 0; i < listForFilling.size(); i++) {
            Row row = sheet.createRow(++rowNum);
            String[] str = listForFilling.get(i).split("\t");
            for (int j = 0; j < str.length; j++) {
                row.createCell(j).setCellValue(str[j]);
            }
        }
    }

}
