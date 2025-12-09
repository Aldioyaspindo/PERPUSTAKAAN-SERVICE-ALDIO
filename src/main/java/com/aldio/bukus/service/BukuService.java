package com.aldio.bukus.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aldio.bukus.model.Buku;
import com.aldio.bukus.repository.BukuRepository;

@Service
public class BukuService {

    @Autowired
    private BukuRepository bukuRepository;

    public List<Buku> getAllBuku() {
        return bukuRepository.findAll();
    }

    public Buku getBukuById(Long id) {
        return bukuRepository.findById(id).orElse(null);
    }

    public Buku createBuku(Buku buku) {
        // pastikan create tidak pakai id
        buku.setId(null);
        return bukuRepository.save(buku);
    }

    public Buku updateBuku(Long id, Buku dataBaru) {
        Buku existing = bukuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Buku tidak ditemukan dengan id: " + id));

        // update field sesuai kebutuhan
        existing.setJudul(dataBaru.getJudul());
        existing.setPengarang(dataBaru.getPengarang());
        existing.setPenerbit(dataBaru.getPenerbit());
        existing.setTahun_terbit(dataBaru.getTahun_terbit());

        return bukuRepository.save(existing);
    }

    public void deleteBuku(Long id) {
        bukuRepository.deleteById(id);
    }
}
