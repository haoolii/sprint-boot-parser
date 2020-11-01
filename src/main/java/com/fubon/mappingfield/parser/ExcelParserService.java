package com.fubon.mappingfield.parser;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service("excelParserService")
public class ExcelParserService implements ParserService{

    private  final AtomicLong counter = new AtomicLong();
    @Override
    public List<ParserTerm> parse(MultipartFile file) {
        List<ParserTerm> list = new ArrayList<ParserTerm>();

        try {
            FileInputStream fis = new FileInputStream(this.convert(file));
            XSSFWorkbook myWorkBook = new XSSFWorkbook(fis);
            XSSFSheet mySheet = myWorkBook.getSheetAt(0);
            Iterator<Row> rowIterator = mySheet.iterator();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                String key = row.getCell(0).toString();
                String value = row.getCell(1).toString();
                list.add(new ParserTerm(key, value));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public XSSFWorkbook parseToExcel(MappingField mappingField) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(mappingField.getName());
        int index = -1;
        Iterator<ParserTerm> termIterator = mappingField.getItems().iterator();

        while (termIterator.hasNext()) {
            index++;
            ParserTerm term = termIterator.next();
            Row row = sheet.createRow(index);
            row.createCell(0).setCellValue(term.getKey());
            row.createCell(1).setCellValue(term.getValue());
        }
        return workbook;
    }

    private File convert(MultipartFile file) throws IOException {
            File convFile = new File(file.getOriginalFilename());
            convFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(convFile);
            fos.write(file.getBytes());
            fos.close();
        return convFile;
    }


}
