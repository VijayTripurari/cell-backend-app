package com.cell.cellbackendapp.util;

import com.cell.cellbackendapp.service.ExcelReadService;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class JobScheduler {

    @Autowired
    ExcelReadService excelReadService;

    private final Path root = Paths.get("uploads");
    @Scheduled(cron = "0/10 * * * * *")
    public void readDataFromFiles() {

        String rootPath = String.valueOf(this.root.toAbsolutePath());
        File mainDir = new File(rootPath);
        if(mainDir.exists() && mainDir.isDirectory()) {
            File[] files = mainDir.listFiles();
            System.out.println("List of files from uploads folder : ");
            if(null != files && ( files.length > 0) ) {
                for (File file1 : files) {
                    System.out.println("Reading : " + file1);
                    try {
                        excelReadService.read_AIRTEL_DataFromExcel(file1);
                    } catch (InvalidFormatException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
