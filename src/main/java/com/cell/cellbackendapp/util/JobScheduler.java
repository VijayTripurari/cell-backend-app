package com.cell.cellbackendapp.util;

import com.cell.cellbackendapp.entity.DbFile;
import com.cell.cellbackendapp.service.ExcelReadService;
import com.cell.cellbackendapp.service.FileDetailService;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Component
public class JobScheduler {

    @Autowired
    ExcelReadService excelReadService;

    @Autowired
    FileDetailService fileDetailService;

    private final Path root = Paths.get("uploads");
//    @Scheduled(cron = "30 * * * * ?")
    public void readDataFromFiles() {

        String rootPath = String.valueOf(this.root.toAbsolutePath());
        File mainDir = new File(rootPath);
        if(mainDir.exists() && mainDir.isDirectory()) {
            File[] files = mainDir.listFiles();
            System.out.println("List of files from uploads folder : " + files);
            if(null != files ) {
                for (File file1 : files) {
                    String provider = file1.getName().substring(StringUtils.ordinalIndexOf(file1.getName(), "_", 3) + 1, StringUtils.ordinalIndexOf(file1.getName(), "_", 4));
                    List<DbFile> dbFileList = fileDetailService.getFileDetails(file1.getName());
                     if(dbFileList == null) {
                         System.out.println("Reading file name: " + file1.getName());
                        try {
                            fileDetailService.storeFileDetails(file1.getName());
                            if(provider.equalsIgnoreCase("AIRTEL")) {
                                excelReadService.read_AIRTEL_DataFromExcel(file1);
                            }
                            else
                                if(provider.equalsIgnoreCase("JIO")) {
                                    excelReadService.read_JIO_DataFromExcel(file1);
                                }
                        } catch (InvalidFormatException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                     else
                     {
                         System.out.println("Already file loaded into DB:"+ file1.getName());
                     }
                }
            }
        }
    }

}
