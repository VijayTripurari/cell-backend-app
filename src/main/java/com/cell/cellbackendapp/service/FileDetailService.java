package com.cell.cellbackendapp.service;

import com.cell.cellbackendapp.entity.DbFile;
import com.cell.cellbackendapp.repository.DbFileRepository;
import com.cell.cellbackendapp.util.CellTowerGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileDetailService {

     @Autowired
     DbFileRepository dbFileRepository;

     @Autowired
     CellTowerGenerator cellTowerGenerator;

    public void storeFileDetails(String fileName) {
        DbFile dbFile = null;
        try {
          dbFile = dbFileRepository.save(new DbFile(fileName, new Date()));
        }
        catch(Exception ex) {
                throw new RuntimeException("Could not Load name file details");
            }
        System.out.println("File name stored in DB:"+dbFile.getFILE_NAME());
    }

    public List<DbFile> getFileDetails(String fileName) {
        List<DbFile> dbFileList = null;
        List<DbFile> list = null;
        try {
            dbFileList = dbFileRepository.findAll();
            if(null != dbFileList && dbFileList.size() > 0){
               list = dbFileList.stream().filter(dbFile -> dbFile.getFILE_NAME().equalsIgnoreCase(fileName)).collect(Collectors.toList());
            }
            else
                list = null;
        }
        catch(Exception ex) {
            throw new RuntimeException("Could not find name file List from DB: ");
        }
       return  list;
    }

}
