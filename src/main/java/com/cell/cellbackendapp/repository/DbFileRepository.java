package com.cell.cellbackendapp.repository;
import com.cell.cellbackendapp.entity.DbFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DbFileRepository extends JpaRepository<DbFile, Long> {
}
