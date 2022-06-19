package com.cell.cellbackendapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.File;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class SplitFileMap {
    private String sheetName;
    private File file;
    private int splitSize;
}
