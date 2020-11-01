package com.fubon.mappingfield.parser;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface ParserService {
    List<ParserTerm> parse(MultipartFile file);

    XSSFWorkbook parseToExcel(MappingField mappingField) throws IOException;
}
