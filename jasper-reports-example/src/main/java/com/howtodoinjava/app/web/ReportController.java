package com.howtodoinjava.app.web;

import com.howtodoinjava.app.dao.ItemRepository;
import com.howtodoinjava.app.jasper.JasperReportService;
import java.io.IOException;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ReportController {

  @Autowired
  ItemRepository itemRepository;

  @Autowired
  JasperReportService jasperReportService;

  @GetMapping("item-report/{format}")
  public ResponseEntity<Resource> getItemReport(@PathVariable String format)
      throws JRException, IOException {

    byte[] reportContent = jasperReportService.getItemReport(itemRepository.findAll(), format);

    ByteArrayResource resource = new ByteArrayResource(reportContent);
    return ResponseEntity.ok()
        .contentType(MediaType.APPLICATION_OCTET_STREAM)
        .contentLength(resource.contentLength())
        .header(HttpHeaders.CONTENT_DISPOSITION,
            ContentDisposition.attachment()
                .filename("item-report." + format)
                .build().toString())
        .body(resource);
  }
}
