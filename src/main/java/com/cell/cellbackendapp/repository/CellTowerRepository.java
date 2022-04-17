package com.cell.cellbackendapp.repository;


import com.cell.cellbackendapp.entity.CellTower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public  interface CellTowerRepository extends JpaRepository<CellTower, Long> {

}
