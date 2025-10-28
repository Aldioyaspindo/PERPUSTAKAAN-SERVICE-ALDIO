package com.utsaldioyaspindo.uts.service;

import com.utsaldioyaspindo.uts.dto.transaksiDTO;
import com.utsaldioyaspindo.uts.model.Transaksi;
import com.utsaldioyaspindo.uts.repository.TransaksiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransaksiService {

    @Autowired
    private TransaksiRepository transaksirepository;

    //buat data
    public Transaksi saveTransaksi(Transaksi transaksi) {
        return transaksirepository.save(transaksi);
    }

    //baca semua data
    public List<Transaksi> getAllTransaksi() {
        return transaksirepository.findAll();
    }

    //baca data per id
    public Transaksi getTransaksiById(Long id) {
        return transaksirepository.findById(id).orElse(null);
    }

    //logika ubah data
    public Transaksi updateTransaksi(Long id, Transaksi transaksiDetails) {
        Transaksi adaTransaksi = transaksirepository.findById(id).orElse(null);
        if (adaTransaksi != null) {
            adaTransaksi.setPelanggan(transaksiDetails.getPelanggan());
            adaTransaksi.setMeterBulanIni(transaksiDetails.getMeterBulanIni());
            adaTransaksi.setMeterBulanLalu(transaksiDetails.getMeterBulanLalu());
            adaTransaksi.setPemakaian(transaksiDetails.getPemakaian());
            adaTransaksi.setTarifPerMeter(transaksiDetails.getTarifPerMeter());
            adaTransaksi.setTotal(transaksiDetails.getTotal());
            return transaksirepository.save(adaTransaksi);
        }
        return null;
    }

    //hapus data
    public String deleteTransaksi(Long id) {
        transaksirepository.deleteById(id);
        return "Berhasil hapus transaksi dengan ID: " + id;
    }

    //dto
    public Transaksi createFromDTO(transaksiDTO dto) {
    Transaksi transaksi = dto.toEntity();
    return transaksirepository.save(transaksi);
    }
}
