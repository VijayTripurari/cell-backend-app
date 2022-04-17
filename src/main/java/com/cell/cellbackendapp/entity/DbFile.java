package com.cell.cellbackendapp.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
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
    @Column(name="DB_FILE_ID")
    private String DB_FILE_ID;

    @Column(name="FILE_NAME")
    private String FILE_NAME;

    @Column(name="RECEIVED_DATE")
    private Date RECEIVED_DATE;

}
