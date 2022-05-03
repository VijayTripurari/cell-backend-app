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
@NoArgsConstructor
@Data
@Entity
@Table(name="cell")
public class Cell {
    @Id
    @Column(name="TOWER_KEY")
    private Long TOWER_KEY;

    @Column(name="CELLTOWERID")
    private String CELLTOWERID;

    @Column(name="BTS_ID")
    private String BTS_ID;

    @Column(name="LAT")
    private String LAT;

    @Column(name="LONG")
    private String LONG;

    @Column(name="AZIMUTH")
    private String AZIMUTH;

    @Column(name = "OPERATOR")
    private String OPERATOR;

    @Column(name="STATE")
    private String STATE;

    @Column(name="OTYPE")
    private String OTYPE;

    @Column(name="LASTUPDATE")
    private Date LASTUPDATE;

    @Column(name="OPID")
    private int OPID;

    @Column(name="STATE_KEY")
    private int STATE_KEY;

    @Column(name="PROVIDER_KEY")
    private int PROVIDER_KEY;

}
