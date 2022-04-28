package com.cell.cellbackendapp.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="DB_FILE")
public class DbFile {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "db_file_seq")
    @SequenceGenerator(name="db_file_seq", sequenceName="db_file_seq", allocationSize = 1)
    @Column(name="DB_FILE_ID")
    private Long DB_FILE_ID;

    @Column(name="FILE_NAME")
    private String FILE_NAME;

    @Column(name="RECEIVED_DATE")
    private Date RECEIVED_DATE;

    public DbFile(String fileName, Date date) {
        this.FILE_NAME=fileName;
        this.RECEIVED_DATE = date;
    }
}
