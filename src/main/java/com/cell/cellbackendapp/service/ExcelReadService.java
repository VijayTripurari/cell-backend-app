package com.cell.cellbackendapp.service;


import com.cell.cellbackendapp.entity.CellTower;
import com.cell.cellbackendapp.repository.CellTowerRepository;
import com.cell.cellbackendapp.util.CellTowerGenerator;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class ExcelReadService {

    @Autowired
    CellTowerRepository cellTowerRepository;

    @Autowired
    CellTowerGenerator cellTowerGenerator;

    static   Map<String , List<Integer>> provOpMap = new HashMap<>();
    static Map<String , Integer> stateKeyMap = new HashMap<>();

    static {
        provOpMap.put("CELLONE" , Arrays.asList(4,5));
        provOpMap.put("JIO" , Arrays.asList(16,16));
        provOpMap.put("IDEA" , Arrays.asList(5,4));
        provOpMap.put("VODAFONE" , Arrays.asList(5,4));
        provOpMap.put("AIRTEL" , Arrays.asList(2,3));

        stateKeyMap.put("ANDAMAN NICOBAR",1);
        stateKeyMap.put("ANDHRA PRADESH",2);
        stateKeyMap.put("TELANGANA",3);
        stateKeyMap.put("ASSAM",4);
        stateKeyMap.put("BIHAR",5);
        stateKeyMap.put("JHARKHAND",6);
        stateKeyMap.put("CHENNAI",7);
        stateKeyMap.put("DELHI",8);
        stateKeyMap.put("NCR",8);
        stateKeyMap.put("GUJARAT",9);
        stateKeyMap.put("HARYANA",10);
        stateKeyMap.put("HIMACHAL PRADESH",11);
        stateKeyMap.put("JAMMU KASHMIR",12);
        stateKeyMap.put("KARNATAKA",14);
        stateKeyMap.put("BANGALORE",14);
        stateKeyMap.put("KERALA",15);
        stateKeyMap.put("KOLKATA",16);
        stateKeyMap.put("MADHYA PRADESH",17);
        stateKeyMap.put("CHHATTISGARH",17);
        stateKeyMap.put("MAHARASHTRA",18);
        stateKeyMap.put("GOA",18);
        stateKeyMap.put("MUMBAI",22);
        stateKeyMap.put("NORTH EAST",24);
        stateKeyMap.put("ORISSA",25);
        stateKeyMap.put("PUNJAB",26);
        stateKeyMap.put("RAJASTHAN",27);
        stateKeyMap.put("TAMILNADU",28);
        stateKeyMap.put("CHENNAI",28);
        stateKeyMap.put("UP_EAST",30);
        stateKeyMap.put("UP_WEST",32);
        stateKeyMap.put("WEST BENGAL",32);
        stateKeyMap.put("NORTH EAST",24);

    }

    public void ReadDataFromExcel(String excelPath) throws EncryptedDocumentException , InvalidFormatException , IOException {
        Workbook workbook = WorkbookFactory.create(new File(excelPath));
        // Retrieve the no of sheets in workbook
        System.out.println("Workbook name : " + excelPath);
        System.out.println("Workbook has " + workbook.getNumberOfSheets() + "Sheets");
        System.out.println("Retrieving sheets using for - each loop");
        for (Sheet sheet : workbook) {
            System.out.println(" ----- " + sheet.getSheetName());
            if(sheet.getSheetName().equals("5G")) {
                int i = 0;
                for(Row row : sheet) {
                    if( i <= 11) {
                        System.out.println(i++ + " | " + row.getCell(0).getStringCellValue() +
                                " | " + row.getCell(1).getStringCellValue() +
                                " | " + row.getCell(2).getStringCellValue() +
                                " | " + row.getCell(3).getNumericCellValue() +
                                " | " + row.getCell(4).getNumericCellValue() +
                                " | " + row.getCell(5).getNumericCellValue() +
                                " | " + row.getCell(6).getStringCellValue() +
                                " | " + row.getCell(7).getStringCellValue() +
                                " | " + row.getCell(8).getNumericCellValue() +
                                " | " + row.getCell(9).getNumericCellValue() +
                                " | " + row.getCell(10).getStringCellValue() +
                                " | " + row.getCell(11).getNumericCellValue());

                    }
                }


            }

        }
    }

    public void read_AIRTEL_DataFromExcel(File file) throws EncryptedDocumentException , InvalidFormatException , IOException {
//        File file = new File(airtelPath);
        Workbook workbook = WorkbookFactory.create(file);
        System.out.println("Workbook name : " + file.getName());
        System.out.println("Workbook has " + workbook.getNumberOfSheets() + " Sheets");
        for (Sheet sheet : workbook) {
            System.out.println(" ----- " + sheet.getSheetName());
            if(sheet.getSheetName().equals("4G")) {
                CellTower cellTower = null;
                int i=1;
                for(Row row : sheet) {

                    if( i!=1) { // dont read first colum in sheet because they are column headers
                        String idValue = file.getName().substring(StringUtils.ordinalIndexOf(file.getName(), "_", 3) + 1, file.getName().lastIndexOf(".")) + "_" + cellTowerGenerator.generateId();
                        cellTower = new CellTower();
                        cellTower.setTOWER_KEY(idValue); // 0 default
                        cellTower.setCELLTOWERID(row.getCell(5).getStringCellValue().replace("_", "-")); // 4G Cell Id , _ replaced by -
                        cellTower.setBTS_ID(row.getCell(6).getStringCellValue());
                        String adr = row.getCell(7).getStringCellValue();
                        String siteAddr = adr.substring(StringUtils.ordinalIndexOf(adr, ",", 3) + 1);
                        cellTower.setAREADESCRIPTION(siteAddr);
                        cellTower.setSITEADDRESS(adr);
                        cellTower.setLAT(row.getCell(8).getNumericCellValue());
                        cellTower.setLONG(row.getCell(9).getNumericCellValue());
                        cellTower.setAZIMUTH(row.getCell(10).getNumericCellValue());
                        String op = idValue.substring(0,StringUtils.ordinalIndexOf(idValue, "_", 1));
                        cellTower.setOPERATOR(op);
                        String stateName = file.getName().substring(StringUtils.ordinalIndexOf(file.getName(), "_", 1) + 1, StringUtils.ordinalIndexOf(file.getName(), "_", 2));
                        cellTower.setSTATE(stateName);
                        cellTower.setOTYPE(sheet.getSheetName());
                        cellTower.setLASTUPDATE(new Date());
                        cellTower.setOPID(provOpMap.get(op).get(1));
                        cellTower.setSTATE_KEY(stateKeyMap.get(stateName));
                        cellTower.setPROVIDER_KEY(provOpMap.get(op).get(0));

                        System.out.println(" | " + row.getCell(0).getNumericCellValue() +
                                " | " + row.getCell(1).getNumericCellValue() +
                                " | " + row.getCell(2).getNumericCellValue() +
                                " | " + row.getCell(3).getNumericCellValue() +
                                " | " + row.getCell(4).getNumericCellValue() +
                                " | " + row.getCell(5).getStringCellValue() +
                                " | " + row.getCell(6).getStringCellValue() +
                                " | " + row.getCell(7).getStringCellValue() +
                                " | " + row.getCell(8).getNumericCellValue() +
                                " | " + row.getCell(9).getNumericCellValue() +
                                " | " + row.getCell(10).getNumericCellValue() +
                                " | " + row.getCell(11).getNumericCellValue());

                        CellTower tower = cellTowerRepository.save(cellTower);
                        System.out.println("4G Tower stored in DB =====> " + tower);
                    }
                    i++;
                }
            }
            else
            if(sheet.getSheetName().equals("3G")) {
                int i=1;
                CellTower cellTower = null;
                for(Row row : sheet) {
                    if(i!=1) {
                        String idValue = file.getName().substring(StringUtils.ordinalIndexOf(file.getName(), "_", 3) + 1, file.getName().lastIndexOf(".")) + "_" + cellTowerGenerator.generateId();
                        cellTower = new CellTower();
                        cellTower.setTOWER_KEY(idValue);
                        cellTower.setCELLTOWERID(String.valueOf(row.getCell(1).getNumericCellValue())); // 3G Cell Id , _ replaced by -);
                        cellTower.setBTS_ID(row.getCell(2).getStringCellValue());
                        String adr = row.getCell(4).getStringCellValue();
                        String areaDesc = adr.substring(StringUtils.ordinalIndexOf(adr, ",", 3) + 1);
                        cellTower.setAREADESCRIPTION(areaDesc);
                        cellTower.setSITEADDRESS(adr);
                        cellTower.setLAT(row.getCell(5).getNumericCellValue());
                        cellTower.setLONG(row.getCell(6).getNumericCellValue());
                        cellTower.setAZIMUTH(row.getCell(7).getNumericCellValue());
                        String op = idValue.substring(0,StringUtils.ordinalIndexOf(idValue, "_", 1));
                        cellTower.setOPERATOR(op);
                        String stateName = file.getName().substring(StringUtils.ordinalIndexOf(file.getName(), "_", 1) + 1, StringUtils.ordinalIndexOf(file.getName(), "_", 2));
                        cellTower.setSTATE(stateName);
                        cellTower.setOTYPE(sheet.getSheetName());
                        cellTower.setLASTUPDATE(new Date());
                        cellTower.setOPID(provOpMap.get(op).get(1));
                        cellTower.setSTATE_KEY(stateKeyMap.get(stateName));
                        cellTower.setPROVIDER_KEY(provOpMap.get(op).get(0));
                        System.out.println(" | " + row.getCell(0).getStringCellValue() +
                                " | " + row.getCell(1).getNumericCellValue() +
                                " | " + row.getCell(2).getStringCellValue() +
                                " | " + row.getCell(3).getStringCellValue() +
                                " | " + row.getCell(4).getStringCellValue() +
                                " | " + row.getCell(5).getNumericCellValue() +
                                " | " + row.getCell(6).getNumericCellValue() +
                                " | " + row.getCell(7).getNumericCellValue());
                        CellTower tower = cellTowerRepository.save(cellTower);
                        System.out.println("3G Tower stored in DB =====> " + tower);
                    }
                    i++;
                }
            }
            else
            if(sheet.getSheetName().equals("2G")) {
                for(Row row : sheet) {
                    System.out.println(" | " + row.getCell(0).getNumericCellValue() +
                            " | " + row.getCell(1).getStringCellValue() +
                            " | " + row.getCell(2).getStringCellValue() +
                            " | " + row.getCell(3).getStringCellValue() +
                            " | " + row.getCell(4).getStringCellValue() +
                            " | " + row.getCell(5).getStringCellValue() +
                            " | " + row.getCell(6).getStringCellValue() +
                            " | " + row.getCell(7).getNumericCellValue() +
                            " | " + row.getCell(8).getStringCellValue() +
                            " | " + row.getCell(9).getStringCellValue() +
                            " | " + row.getCell(10).getStringCellValue() +
                            " | " + row.getCell(11).getStringCellValue() +
                            " | " + row.getCell(12).getNumericCellValue() +
                            " | " + row.getCell(13).getStringCellValue() +
                            " | " + row.getCell(14).getStringCellValue() +
                            " | " + row.getCell(15).getStringCellValue() +
                            " | " + row.getCell(16).getStringCellValue() +
                            " | " + row.getCell(17).getNumericCellValue() +
                            " | " + row.getCell(18).getStringCellValue() +
                            " | " + row.getCell(19).getStringCellValue() +
                            " | " + row.getCell(20).getStringCellValue() +
                            " | " + row.getCell(21).getStringCellValue() +
                            " | " + row.getCell(1).getStringCellValue() +
                            " | " + row.getCell(22).getNumericCellValue() +
                            " | " + row.getCell(23).getNumericCellValue() +
                            " | " + row.getCell(24).getNumericCellValue() +
                            " | " + row.getCell(25).getNumericCellValue());
                }

            }
        }

    }
}
