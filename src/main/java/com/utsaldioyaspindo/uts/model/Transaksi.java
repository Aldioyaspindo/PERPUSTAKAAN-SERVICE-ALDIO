package com.utsaldioyaspindo.uts.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "transaksis")
public class Transaksi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String pelanggan;
    private int meterBulanIni;
    private int meterBulanLalu;
    private int pemakaian;
    private double tarifPerMeter;
    private double total;
}
