package com.cell.cellbackendapp.entity;


import lombok.*;

import javax.persistence.*;
import java.util.Date;

@ToString
@Getter
@Setter
@NoArgsConstructor
@Data
@Entity
@Table(name="CELL_TOWER")
public class CellTower {

    @Id
    @Column(name="TOWER_KEY")
    private String TOWER_KEY;

    @Column(name="CELLTOWERID")
    private String CELLTOWERID;

    @Column(name="BTS_ID")
    private String BTS_ID;

    @Column(name="AREADESCRIPTION")
    private String AREADESCRIPTION;

    @Column(name="SITEADDRESS")
    private String SITEADDRESS;

    @Column(name="LAT")
    private Double LAT;

    @Column(name="LONG")
    private Double LONG;

    @Column(name="AZIMUTH")
    private Double AZIMUTH;

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

