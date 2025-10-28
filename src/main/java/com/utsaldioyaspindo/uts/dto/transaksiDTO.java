package com.utsaldioyaspindo.uts.dto;

import com.utsaldioyaspindo.uts.model.Transaksi;

public class transaksiDTO {
    private String pelanggan;
    private int meterBulanIni;
    private int meterBulanLalu;
    private double tarifPerMeter;

    // ===== GETTER & SETTER =====
    public String getPelanggan() { 
        return pelanggan; 
    }
    public void setPelanggan(String pelanggan) { 
        this.pelanggan = pelanggan; 
    }

    public int getMeterBulanIni() { 
        return meterBulanIni; 
    }
    public void setMeterBulanIni(int meterBulanIni) { 
        this.meterBulanIni = meterBulanIni; 
    }

    public int getMeterBulanLalu() { 
        return meterBulanLalu; 
    }
    public void setMeterBulanLalu(int meterBulanLalu) { 
        this.meterBulanLalu = meterBulanLalu; 
    }

    public double getTarifPerMeter() { 
        return tarifPerMeter; 
    }
    public void setTarifPerMeter(double tarifPerMeter) { 
        this.tarifPerMeter = tarifPerMeter; 
    }

    // ===== LOGIKA DI DALAM DTO =====
    public int getPemakaian() {
        return meterBulanIni - meterBulanLalu;
    }

    public double getTotal() {
        return getPemakaian() * tarifPerMeter;
    }

    // ===== KONVERSI DTO → ENTITY =====
    public Transaksi toEntity() {
        Transaksi transaksi = new Transaksi();
        transaksi.setPelanggan(this.pelanggan);
        transaksi.setMeterBulanIni(this.meterBulanIni);
        transaksi.setMeterBulanLalu(this.meterBulanLalu);
        transaksi.setTarifPerMeter(this.tarifPerMeter);
        transaksi.setPemakaian(getPemakaian());
        transaksi.setTotal(getTotal());
        return transaksi;
    }
}
