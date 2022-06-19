package com.cell.cellbackendapp.service;


import com.cell.cellbackendapp.CellDAO;
import com.cell.cellbackendapp.entity.Cell;
import com.cell.cellbackendapp.entity.CellTower;
import com.cell.cellbackendapp.model.SplitFileMap;
import com.cell.cellbackendapp.repository.CellRepository;
import com.cell.cellbackendapp.repository.CellTowerRepository;
import com.cell.cellbackendapp.util.CellTowerGenerator;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ExcelReadService {

    @Autowired
    CellTowerRepository cellTowerRepository;

    @Autowired
    CellTowerGenerator cellTowerGenerator;

    @Autowired
    CellRepository cellRepository;

    @Autowired
    CellDAO cellDAO;
    Workbook currentJIOWorkbook;
    Sheet currentJIOSheet;
    Long preTowerMaxKey = 0L;
    Long currentTowerKayMax = 0L;

    @Value("${jio.batch.size}")
    int jioBatchSize;
    @Value("${jio.file.split.size}")
    int splitSize;

    static Map<String, List<Integer>> provOpMap = new HashMap<>();
    static Map<String, Integer> stateKeyMap = new HashMap<>();

    static {
        provOpMap.put("CELLONE", Arrays.asList(4, 5));
        provOpMap.put("JIO", Arrays.asList(16, 16));
        provOpMap.put("IDEA", Arrays.asList(5, 4));
        provOpMap.put("VODAFONE", Arrays.asList(5, 4));
        provOpMap.put("AIRTEL", Arrays.asList(2, 3));

        stateKeyMap.put("ANDAMAN NICOBAR", 1);
        stateKeyMap.put("ANDHRAPRADESH", 2);
        stateKeyMap.put("TELANGANA", 3);
        stateKeyMap.put("ASSAM", 4);
        stateKeyMap.put("BIHAR", 5);
        stateKeyMap.put("JHARKHAND", 6);
        stateKeyMap.put("CHENNAI", 7);
        stateKeyMap.put("DELHI", 8);
        stateKeyMap.put("NCR", 8);
        stateKeyMap.put("GUJARAT", 9);
        stateKeyMap.put("HARYANA", 10);
        stateKeyMap.put("HIMACHAL PRADESH", 11);
        stateKeyMap.put("JAMMU KASHMIR", 12);
        stateKeyMap.put("KARNATAKA", 14);
        stateKeyMap.put("BANGALORE", 14);
        stateKeyMap.put("KERALA", 15);
        stateKeyMap.put("KOLKATA", 16);
        stateKeyMap.put("MADHYA PRADESH", 17);
        stateKeyMap.put("CHHATTISGARH", 17);
        stateKeyMap.put("MAHARASHTRA", 18);
        stateKeyMap.put("GOA", 18);
        stateKeyMap.put("MUMBAI", 22);
        stateKeyMap.put("NORTH EAST", 24);
        stateKeyMap.put("ORISSA", 25);
        stateKeyMap.put("PUNJAB", 26);
        stateKeyMap.put("RAJASTHAN", 27);
        stateKeyMap.put("TAMILNADU", 28);
        stateKeyMap.put("CHENNAI", 28);
        stateKeyMap.put("UP_EAST", 30);
        stateKeyMap.put("UP_WEST", 32);
        stateKeyMap.put("WEST BENGAL", 32);
        stateKeyMap.put("NORTH EAST", 24);

    }

    public void ReadDataFromExcel(String excelPath) throws EncryptedDocumentException, InvalidFormatException, IOException {
        Workbook workbook = WorkbookFactory.create(new File(excelPath));
        // Retrieve the no of sheets in workbook
        System.out.println("Workbook name : " + excelPath);
        System.out.println("Workbook has " + workbook.getNumberOfSheets() + "Sheets");
        System.out.println("Retrieving sheets using for - each loop");
        for (Sheet sheet : workbook) {
            System.out.println(" ----- " + sheet.getSheetName());
            if (sheet.getSheetName().equals("5G")) {
                int i = 0;
                for (Row row : sheet) {
                    if (i <= 11) {
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

    public void read_AIRTEL_DataFromExcel(File file) throws EncryptedDocumentException, InvalidFormatException, IOException {
//        File file = new File(airtelPath);
        Workbook workbook = WorkbookFactory.create(file);
        System.out.println("Workbook name : " + file.getName());
        System.out.println("Workbook has " + workbook.getNumberOfSheets() + " Sheets");
        for (Sheet sheet : workbook) {
            System.out.println(" ----- " + sheet.getSheetName());
            if (sheet.getSheetName().equals("4G")) {
                CellTower cellTower = null;
                int i = 1;
                List<CellTower> cellTowerList = new ArrayList<>();
                for (Row row : sheet) {

                    if (i != 1) { // dont read first colum in sheet because they are column headers
                        String idValue = file.getName().substring(StringUtils.ordinalIndexOf(file.getName(), "_", 3) + 1, file.getName().lastIndexOf(".")) + "_" + cellTowerGenerator.generateId();
                        cellTower = new CellTower();
//                        cellTower.setTOWER_KEY(idValue); // 0 default
                        if (row.getCell(5) != null)
                            cellTower.setCELLTOWERID(row.getCell(5).getStringCellValue().replace("_", "-")); // 4G Cell Id , _ replaced by -
                        else
                            cellTower.setCELLTOWERID("");
                        cellTower.setBTS_ID(row.getCell(6).getStringCellValue());
                        String adr = row.getCell(7).getStringCellValue();
                        String siteAddr = adr.substring(StringUtils.ordinalIndexOf(adr, ",", 3) + 1);
                        cellTower.setAREADESCRIPTION(siteAddr);
                        cellTower.setSITEADDRESS(adr);
                        cellTower.setLAT(row.getCell(8).getNumericCellValue());
                        cellTower.setLONG(row.getCell(9).getNumericCellValue());
                        cellTower.setAZIMUTH(row.getCell(10).getNumericCellValue());
                        String op = idValue.substring(0, StringUtils.ordinalIndexOf(idValue, "_", 1));
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
                        cellTowerList.add(cellTower);
//                        CellTower tower = cellTowerRepository.save(cellTower);
//                        System.out.println("4G Tower stored in DB =====> " + tower);
                    }
                    i++;
                }
                cellTowerRepository.saveAll(cellTowerList);
                System.out.println("4G Tower stored in DB =====> ");
            }
//            else
//            if(sheet.getSheetName().equals("3G")) {
//                int i=1;
//                CellTower cellTower = null;
//                for(Row row : sheet) {
//                    if(i!=1) {
//                        String idValue = file.getName().substring(StringUtils.ordinalIndexOf(file.getName(), "_", 3) + 1, file.getName().lastIndexOf(".")) + "_" + cellTowerGenerator.generateId();
//                        cellTower = new CellTower();
////                        cellTower.setTOWER_KEY(idValue);
//                        cellTower.setCELLTOWERID(String.valueOf(row.getCell(1).getNumericCellValue())); // 3G Cell Id , _ replaced by -);
//                        cellTower.setBTS_ID(row.getCell(2).getStringCellValue());
//                        String adr = row.getCell(4).getStringCellValue();
//                        String areaDesc = adr.substring(StringUtils.ordinalIndexOf(adr, ",", 3) + 1);
//                        cellTower.setAREADESCRIPTION(areaDesc);
//                        cellTower.setSITEADDRESS(adr);
//                        cellTower.setLAT(row.getCell(5).getNumericCellValue());
//                        cellTower.setLONG(row.getCell(6).getNumericCellValue());
//                        cellTower.setAZIMUTH(row.getCell(7).getNumericCellValue());
//                        String op = idValue.substring(0,StringUtils.ordinalIndexOf(idValue, "_", 1));
//                        cellTower.setOPERATOR(op);
//                        String stateName = file.getName().substring(StringUtils.ordinalIndexOf(file.getName(), "_", 1) + 1, StringUtils.ordinalIndexOf(file.getName(), "_", 2));
//                        cellTower.setSTATE(stateName);
//                        cellTower.setOTYPE(sheet.getSheetName());
//                        cellTower.setLASTUPDATE(new Date());
//                        cellTower.setOPID(provOpMap.get(op).get(1));
//                        cellTower.setSTATE_KEY(stateKeyMap.get(stateName));
//                        cellTower.setPROVIDER_KEY(provOpMap.get(op).get(0));
//                        System.out.println(" | " + row.getCell(0).getStringCellValue() +
//                                " | " + row.getCell(1).getNumericCellValue() +
//                                " | " + row.getCell(2).getStringCellValue() +
//                                " | " + row.getCell(3).getStringCellValue() +
//                                " | " + row.getCell(4).getStringCellValue() +
//                                " | " + row.getCell(5).getNumericCellValue() +
//                                " | " + row.getCell(6).getNumericCellValue() +
//                                " | " + row.getCell(7).getNumericCellValue());
//                        CellTower tower = cellTowerRepository.save(cellTower);
//                        System.out.println("3G Tower stored in DB =====> " + tower);
//                    }
//                    i++;
//                }
//            }
//            else
//            if(sheet.getSheetName().equals("2G")) {
//                for(Row row : sheet) {
//                    System.out.println(" | " + row.getCell(0).getNumericCellValue() +
//                            " | " + row.getCell(1).getStringCellValue() +
//                            " | " + row.getCell(2).getStringCellValue() +
//                            " | " + row.getCell(3).getStringCellValue() +
//                            " | " + row.getCell(4).getStringCellValue() +
//                            " | " + row.getCell(5).getStringCellValue() +
//                            " | " + row.getCell(6).getStringCellValue() +
//                            " | " + row.getCell(7).getNumericCellValue() +
//                            " | " + row.getCell(8).getStringCellValue() +
//                            " | " + row.getCell(9).getStringCellValue() +
//                            " | " + row.getCell(10).getStringCellValue() +
//                            " | " + row.getCell(11).getStringCellValue() +
//                            " | " + row.getCell(12).getNumericCellValue() +
//                            " | " + row.getCell(13).getStringCellValue() +
//                            " | " + row.getCell(14).getStringCellValue() +
//                            " | " + row.getCell(15).getStringCellValue() +
//                            " | " + row.getCell(16).getStringCellValue() +
//                            " | " + row.getCell(17).getNumericCellValue() +
//                            " | " + row.getCell(18).getStringCellValue() +
//                            " | " + row.getCell(19).getStringCellValue() +
//                            " | " + row.getCell(20).getStringCellValue() +
//                            " | " + row.getCell(21).getStringCellValue() +
//                            " | " + row.getCell(1).getStringCellValue() +
//                            " | " + row.getCell(22).getNumericCellValue() +
//                            " | " + row.getCell(23).getNumericCellValue() +
//                            " | " + row.getCell(24).getNumericCellValue() +
//                            " | " + row.getCell(25).getNumericCellValue());
//                }

//            }
        }

    }

    public void read_JIO_DataFromExcel_v1(File file) throws EncryptedDocumentException, InvalidFormatException, IOException
        {
            try {
                Workbook workbook = WorkbookFactory.create(file);
                System.out.println("Workbook name : " + file.getName());
                String fileName = file.getName();
                System.out.println("Workbook has " + workbook.getNumberOfSheets() + " Sheets");
                Map<String, SplitFileMap> fileMap = new HashMap<>(); // store all sheet details of file in map
                for (Sheet sheet : workbook) {
                    System.out.println(" ----- " + sheet.getSheetName());
                    int rowTotal = 0; // sheet row count initialize size 0
                    if (sheet.getSheetName().equals("JIO")) {
                        rowTotal = sheet.getLastRowNum(); // calculate total rows of curr sheet
                        if ((rowTotal > 0) || (sheet.getPhysicalNumberOfRows() > 0)) {
                            rowTotal++;
                        }
                        fileMap.put("JIO", new SplitFileMap("JIO", file, rowTotal));
                    }
                }
                workbook.close(); // close workbook after getting  all sheet details
                // start processing the each sheet with limit 50000
                Set<String> keySet = fileMap.keySet();
                for (String key : keySet) {
                    SplitFileMap splitFileMap = fileMap.get(key);
                    // check if sheet size is less than 50000 rows
                    List<String> cellTowerIDRangeList;
                    int fileRowSize = splitFileMap.getSplitSize();
                    Long towerKayMaxFromCell = cellDAO.getMaxCellForProviderKey(16);
                    if (fileRowSize <= splitSize) {
                        read_JIO_DataFromExcel(file, 0, splitFileMap.getSplitSize(),towerKayMaxFromCell);
                    } else {
                        int itr = fileRowSize / splitSize;
                        int lastItr = fileRowSize % splitSize;
                        int j, k;
                        for (j = 0, k = 1; j <= fileRowSize && k <= itr; j = j + splitSize, k++) {
//                           cellTowerIDRangeList = cellTowerListForRange(j - 1, batch_size, sheet);
                            if(this.preTowerMaxKey == 0)
                               read_JIO_DataFromExcel(file, j, splitSize,towerKayMaxFromCell);
                            else
                                read_JIO_DataFromExcel(file, j, splitSize,preTowerMaxKey);
//                            read_JIO_Data_For_Range(j - 1, splitSize, file, currentJIOWorkbook, currentJIOSheet, fileName, cellTowerIDRangeList, towerKayMaxFromCell, currentTowerKayMax, preTowerMaxKey);
                        }
//                       cellTowerIDRangeList = cellTowerListForRange(j - 1, lastItr, sheet);
                        read_JIO_DataFromExcel(file, j, lastItr,preTowerMaxKey);
//                        read_JIO_Data_For_Range(j - 1, lastItr, file, currentJIOWorkbook, currentJIOSheet, fileName, cellTowerIDRangeList, towerKayMaxFromCell, currentTowerKayMax, preTowerMaxKey);

                    }
                }
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }


        public void read_JIO_DataFromExcel (File file,int startOfFileIndex, int endOfFileIndex, Long towerKayMaxFromCell) throws
        EncryptedDocumentException, InvalidFormatException, IOException
        {
            try {
                Workbook workbook = WorkbookFactory.create(file);
                this.currentJIOWorkbook = workbook;
                System.out.println("Workbook name : " + file.getName());
                String fileName = file.getName();
                System.out.println("Workbook has " + workbook.getNumberOfSheets() + " Sheets");
                for (Sheet sheet : workbook) {
                    this.currentJIOSheet = sheet;

                    System.out.println(" ----- " + sheet.getSheetName());
                    if (sheet.getSheetName().equals("JIO")) {
//                        Long towerKayMaxFromCell = cellDAO.getMaxCellForProviderKey(16);
//                   int rowTotal = sheet.getLastRowNum();
                        int rowTotal = endOfFileIndex;
//                        if ((rowTotal > 0) || (sheet.getPhysicalNumberOfRows() > 0)) {
//                            rowTotal++;
//                        }
                        int batch_size = jioBatchSize;
                        List<String> cellTowerIDRangeList;
                        cellTowerIDRangeList = cellTowerListForRange(startOfFileIndex, rowTotal, sheet);
                        if (rowTotal <= batch_size) {  // less than batch size
//                       cellTowerIDRangeList = cellTowerListForRange(0, rowTotal, sheet);
                            read_JIO_Data_For_Range(startOfFileIndex, rowTotal, file, currentJIOWorkbook, currentJIOSheet, fileName, cellTowerIDRangeList, towerKayMaxFromCell, currentTowerKayMax, preTowerMaxKey);

                        } else {                     // more batches to execute
                            int itr = rowTotal / batch_size;
                            int lastItr = rowTotal % batch_size;
                            int j, k;
                            for (j = startOfFileIndex, k = 1; j <= rowTotal && k <= itr; j = j + batch_size, k++) {
//                           cellTowerIDRangeList = cellTowerListForRange(j - 1, batch_size, sheet);
                                read_JIO_Data_For_Range(j , batch_size, file, currentJIOWorkbook, currentJIOSheet, fileName, cellTowerIDRangeList, towerKayMaxFromCell, currentTowerKayMax, preTowerMaxKey);
                            }
//                       cellTowerIDRangeList = cellTowerListForRange(j - 1, lastItr, sheet);
                            read_JIO_Data_For_Range(j , lastItr, file, currentJIOWorkbook, currentJIOSheet, fileName, cellTowerIDRangeList, towerKayMaxFromCell, currentTowerKayMax, preTowerMaxKey);
                        }
                    }
                }
                workbook.close();
                currentJIOWorkbook.close();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
        private List<String> cellTowerListForRange ( int index, int batch_size, Sheet sheet){
            List<Cell> cellList = new ArrayList<>();
            List<String> resultCellList = new ArrayList<>();
            int size = index + batch_size;
            for (int i = index; i < size; i++) {
                cellList = cellRepository.findByCELLTOWERID(sheet.getRow(i).getCell(0).getStringCellValue());
                if (!cellList.isEmpty()) {
//                cellList.parallelStream().map(cell -> cell.getCELLTOWERID()).forEach(cellTowerId -> resultCellList.add(cellTowerId));
                    resultCellList.add(cellList.get(0).getCELLTOWERID());
                }
            }
      System.out.println("resultCellList size : " + resultCellList.size());
            if (!resultCellList.isEmpty()) {
                System.out.println(" records already exists and will be skipped : " + resultCellList.size());
            }

            return resultCellList;
        }
        private void read_JIO_Data_For_Range ( int index, int batch_size, File file, Workbook workbook, Sheet
        sheet, String fileName, List < String > cellTowerIDRangeList, Long towerKayMaxFromCell, Long
        currentTowerKayMax, Long preTowerMaxKey) throws EncryptedDocumentException, InvalidFormatException, IOException
        {
//                workbook = WorkbookFactory.create(file);
            CellTower cellTower = null;
            for (Sheet curSheet : workbook) {
                if (curSheet.getSheetName().equalsIgnoreCase(sheet.getSheetName())) {
                    currentJIOSheet = curSheet;
                }
            }

            int i = 1;
            List<CellTower> cellTowerList = new ArrayList<>();
            int size = index + batch_size;
            for (int rowNo = index; rowNo < size; rowNo++) {
                final int rowNumb = rowNo;
                List<String> result = cellTowerIDRangeList
                        .parallelStream()
                        .filter(x -> x.contains(sheet.getRow(rowNumb).getCell(0).getStringCellValue()))
                        .collect(Collectors.toList());
                if (!result.isEmpty()) {
                    System.out.println("Record with Cell Tower ID: " + sheet.getRow(rowNo).getCell(0).getStringCellValue() + " Already exists");
                } else {
                    String idValue = fileName.substring(StringUtils.ordinalIndexOf(fileName, "_", 3) + 1, fileName.lastIndexOf(".")) + "_" + cellTowerGenerator.generateId();
                    cellTower = new CellTower();
                    cellTower.setCELLTOWERID(sheet.getRow(rowNo).getCell(0).getStringCellValue()); // 3G Cell Id , _ replaced by -);
                    cellTower.setBTS_ID(sheet.getRow(rowNo).getCell(0).getStringCellValue());
                    String adr;
                    if (null != sheet.getRow(rowNo).getCell(6))
                        adr = sheet.getRow(rowNo).getCell(6).getStringCellValue();
                    else
                        adr = null;
                    String areaDesc;
                    if (adr != null)
                        areaDesc = adr.substring(StringUtils.ordinalIndexOf(adr, ",", 1) + 1);
                    else
                        areaDesc = null;
                    cellTower.setAREADESCRIPTION(areaDesc);
                    cellTower.setSITEADDRESS(adr);


                    cellTower.setLAT(sheet.getRow(rowNo).getCell(8).getNumericCellValue());
                    cellTower.setLONG(sheet.getRow(rowNo).getCell(9).getNumericCellValue());
                    if (sheet.getRow(rowNo).getCell(11) != null)
                        cellTower.setAZIMUTH(sheet.getRow(rowNo).getCell(11).getNumericCellValue());
                    else
                        cellTower.setAZIMUTH(0d);

                    String op = idValue.substring(0, StringUtils.ordinalIndexOf(idValue, "_", 1));
                    cellTower.setOPERATOR(op);
                    String stateName = fileName.substring(StringUtils.ordinalIndexOf(fileName, "_", 1) + 1, StringUtils.ordinalIndexOf(fileName, "_", 2));
                    cellTower.setSTATE(stateName);
                    cellTower.setOTYPE(sheet.getSheetName());
                    cellTower.setLASTUPDATE(new Date());
                    cellTower.setOPID(provOpMap.get(op).get(1));
                    cellTower.setSTATE_KEY(stateKeyMap.get(stateName));
                    cellTower.setPROVIDER_KEY(provOpMap.get(op).get(0));
//                        System.out.println(" | " + sheet.getRow(rowNo).getCell(0).getStringCellValue() +
//                                " | " + sheet.getRow(rowNo).getCell(0).getStringCellValue() +
//                                " | " + adr +
//                                " | " + sheet.getRow(rowNo).getCell(8).getNumericCellValue() +
//                                " | " + sheet.getRow(rowNo).getCell(9).getNumericCellValue());

                    if (preTowerMaxKey == 0) {
                        currentTowerKayMax = towerKayMaxFromCell + 1;
                        preTowerMaxKey = currentTowerKayMax;
                    } else {
                        currentTowerKayMax = preTowerMaxKey + 1;
                        preTowerMaxKey = currentTowerKayMax;
                    }
                    this.currentTowerKayMax = currentTowerKayMax;
                    this.preTowerMaxKey = preTowerMaxKey;
                    cellTower.setTOWER_KEY(currentTowerKayMax);
                    cellTowerList.add(cellTower);

                }
            }
            cellTowerRepository.saveAll(cellTowerList);
            System.out.println(batch_size + " records stored successfully");
//                workbook.close();
        }
    }




