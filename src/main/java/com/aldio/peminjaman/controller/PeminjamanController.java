package com.aldio.peminjaman.controller;

import com.aldio.peminjaman.model.Peminjaman;
import com.aldio.peminjaman.service.PeminjamanService;
import com.aldio.peminjaman.vo.ResponseTemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

@Slf4j
@RestController
@RequestMapping("/api/peminjaman")
public class PeminjamanController {

    @Autowired
    private PeminjamanService peminjamanService;

    // Get Peminjaman + Buku + Anggota
    @GetMapping("/{id}/details")
    public ResponseEntity<ResponseTemplate> getPeminjamanWithDetails(@PathVariable Long id) {
        log.info("Mencari peminjaman dengan ID: {}", id);
        return ResponseEntity.ok(peminjamanService.getPeminjamanWithDetailsById(id));
    }

    // Create
    @PostMapping
    public ResponseEntity<Peminjaman> createPeminjaman(@RequestBody Peminjaman peminjaman) {
        log.info("Menambahkan peminjaman baru: {}", peminjaman);
        return ResponseEntity.ok(peminjamanService.savePeminjaman(peminjaman));
    }

    // Read all
    @GetMapping
    public ResponseEntity<List<Peminjaman>> getAllPeminjaman() {
        log.info("Mencari semua peminjaman");
        return ResponseEntity.ok(peminjamanService.getAllPeminjamans());
    }

    // Read by ID
    @GetMapping("/{id}")
    public ResponseEntity<Peminjaman> getPeminjamanById(@PathVariable Long id) {
        log.info("Mencari peminjaman dengan ID: {}", id);
        return ResponseEntity.ok(peminjamanService.getPeminjamanById(id).orElse(null));
    }

    // Update
    @PutMapping("/{id}")
    public ResponseEntity<Peminjaman> updatePeminjaman(@PathVariable Long id, @RequestBody Peminjaman peminjamanDetails) {
        log.info("Memperbarui peminjaman dengan ID: {}", id);
        return ResponseEntity.ok(peminjamanService.updatePeminjaman(id, peminjamanDetails));
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePeminjaman(@PathVariable Long id) {
        log.info("Menghapus peminjaman dengan ID: {}", id);
        return ResponseEntity.ok(peminjamanService.deletePeminjaman(id));
    }
}
