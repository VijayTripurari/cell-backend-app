package com.cell.cellbackendapp.repository;

import com.cell.cellbackendapp.entity.Cell;
import com.cell.cellbackendapp.entity.CellTower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CellRepository extends JpaRepository<Cell, Long> {
public List<Cell> findByCELLTOWERID(String CELLTOWERID);
}
