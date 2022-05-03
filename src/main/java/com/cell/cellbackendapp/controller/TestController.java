package com.cell.cellbackendapp.controller;

import com.cell.cellbackendapp.CellDAO;
import com.cell.cellbackendapp.entity.Cell;
import com.cell.cellbackendapp.repository.CellRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {
    @Autowired
    CellRepository cellRepository;

    @Autowired
    CellDAO cellDAO;

    @GetMapping("/test/celltowerid/{cellTowerId}")
    public List<Cell> getCellByCellTowerId(@PathVariable("cellTowerId") String CELLTOWERID) {
        return cellRepository.findByCELLTOWERID(CELLTOWERID);
    }

    @GetMapping("/test/providerKeyJio")
    public Long getProviderKey() {
        // JIO - 16
        return cellDAO.getMaxCellForProviderKey(16);
    }
}
