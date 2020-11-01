package com.fubon.mappingfield.parser;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Service("textParserService")
public class TextParserService implements ParserService{
    @Override
    public List<ParserTerm> parse(MultipartFile file) {
        return null;
    }

    @Override
    public XSSFWorkbook parseToExcel(MappingField mappingField) {
        return null;
    }
}
