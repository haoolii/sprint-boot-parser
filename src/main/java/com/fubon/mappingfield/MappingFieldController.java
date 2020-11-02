package com.fubon.mappingfield;

import com.fubon.mappingfield.parser.MappingField;
import com.fubon.mappingfield.parser.ParserService;
import com.fubon.mappingfield.parser.ParserTerm;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@RestController
public class MappingFieldController {

    private List<MappingField> mappingFieldList = new ArrayList<MappingField>();
    private final ParserService excelParserService;
    private final ParserService textParserService;
    private final AtomicLong counter = new AtomicLong();

    @Autowired
    public MappingFieldController(
            @Qualifier("excelParserService") ParserService excelParserService,
            @Qualifier("textParserService") ParserService textParserService
    ) {
        this.excelParserService = excelParserService;
        this.textParserService = textParserService;
    }

    @PostMapping("/")
    @CrossOrigin(origins = "*")
    public ResponseEntity handleFieldUpload(
            @RequestParam("file") MultipartFile file,
            @RequestParam("name") String name,
            RedirectAttributes redirectAttributes
    ) {
        mappingFieldList.add(new MappingField(counter.incrementAndGet(), name, excelParserService.parse(file)));
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/mappingField")
    @CrossOrigin(origins = "*")
    public ResponseEntity<MappingField> getMappingFields(@RequestParam long id) {
        Optional<MappingField> optionalResultMappingField = mappingFieldList.stream()
                                                        .filter(m -> m.getId() == id)
                                                        .findFirst();
        if (optionalResultMappingField.isPresent()){
            return new ResponseEntity<>(optionalResultMappingField.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/mappingFields")
    @CrossOrigin(origins = "*")
    public List<MappingField> getMappingFields() {
        return mappingFieldList;
    }

    @PostMapping("/mappingField")
    @CrossOrigin(origins = "*")
    public MappingField addMappingFields(@RequestBody MappingField mappingField) {
        mappingField.setId(counter.incrementAndGet());
        mappingFieldList.add(mappingField);
        return mappingField;
    }

    @GetMapping("/excel")
    @CrossOrigin(origins = "*")
    public ResponseEntity<StreamingResponseBody> excel(@RequestParam long id) throws IOException {
        Optional<MappingField> optionalResultMappingField = mappingFieldList.stream()
                                                            .filter(m -> m.getId() == id)
                                                            .findFirst();
        if (optionalResultMappingField.isPresent()) {
            Workbook out = excelParserService.parseToExcel(optionalResultMappingField.get());
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\""+ optionalResultMappingField.get().getName()+".xlsx\"")
                    .header("Access-Control-Expose-Headers", "*")
                    .body(out::write);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
//attachment; filename="file.docx"