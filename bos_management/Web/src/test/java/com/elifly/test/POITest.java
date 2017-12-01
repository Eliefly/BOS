package com.elifly.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

/**
 * ClassName:POITest <br/>
 * Function: <br/>
 * Date: 2017年12月1日 下午10:21:41 <br/>
 */

public class POITest {

    /**
     * main:. <br/>
     * 
     * @param args
     * @throws IOException
     * @throws FileNotFoundException
     */
    public static void main(String[] args) throws Exception {

        File file = new File("C:/Users/eliefly/Desktop/aaa.xls");
        // 创建个workbook，根据POIFSFileSystem对象
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(new FileInputStream(file));

        HSSFSheet sheet = hssfWorkbook.getSheetAt(0);

        for (Row row : sheet) {

            for (Cell cell : row) {
                String value = cell.getStringCellValue();
                System.out.print(value + " ");
            }
            System.out.println();
        }
        hssfWorkbook.close();
    }

}
