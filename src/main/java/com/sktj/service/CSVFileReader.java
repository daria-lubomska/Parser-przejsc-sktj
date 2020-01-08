package com.sktj.service;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
class CSVFileReader {

  List<String[]> readFile(Resource resource) throws IOException, CsvValidationException {
    InputStream in = resource.getInputStream();
    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
    CSVReader csvReader = new CSVReader(reader);
    List<String[]> list = new ArrayList<>();
    String[] line;
    while ((line = csvReader.readNext()) != null) {
      list.add(line);
    }
    reader.close();
    csvReader.close();
    return list;
  }
}
