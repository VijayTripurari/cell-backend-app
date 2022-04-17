package com.cell.cellbackendapp.controller;

import com.cell.cellbackendapp.util.JobScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cellid")
public class CellTowerController {

    @Autowired
    JobScheduler jobScheduler;

    @GetMapping("/loadfile")
   public void testExcelRead() {
     jobScheduler.readDataFromFiles();
   }

}