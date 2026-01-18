package com.aldio.pengembalian.controller;

import com.aldio.pengembalian.*;
import com.aldio.pengembalian.model.Pengembalian;
import com.aldio.pengembalian.service.PengembalianService;
import com.aldio.pengembalian.vo.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/pengembalian")
public class PengembalianController {
    @Autowired
    private PengembalianService pengembalianService;

    @GetMapping("/{id}/details")
    public ResponseEntity<ResponseTemplate> getPengembalianWithDetails(@PathVariable Long id){
        log.info("Mencari pengembalian dengan ID: {}", id);
        return ResponseEntity.ok(pengembalianService.getPengembalianWithDetailsById(id));
    }


    @PostMapping
    public ResponseEntity<Pengembalian> creatPengembalian(@RequestBody Pengembalian pengembalian){
        log.info("Menambahkan pengembalian baru: {}", pengembalian);
        return ResponseEntity.ok(pengembalianService.savePengembalian(pengembalian));
    }

    @GetMapping
    public ResponseEntity<List<Pengembalian>> getAllPengembalians() {
        log.info("Mencari semua pengembalian");
        return ResponseEntity.ok(pengembalianService.getAllPengembalian());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pengembalian> getPengembalianById(@PathVariable Long id){
        log.info("Mencari pengembalian dengan ID: {}", id);
        return ResponseEntity.ok(pengembalianService.getPengembalianById(id).orElse(null));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pengembalian> updatePengembalian(@PathVariable Long id, @RequestBody Pengembalian pengembalianDetails){
        return ResponseEntity.ok(pengembalianService.updatePengembalian(id, pengembalianDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePengembalian(@PathVariable Long id) {
        return ResponseEntity.ok(pengembalianService.deletePengembalian(id));
    }

    // === HITUNG LAMA PINJAM, TERLAMBAT, DENDA ===
    @PostMapping("/hitung")
    public ResponseEntity<pengambalianVo> hitungPengembalian(@RequestBody Peminjaman peminjaman, @RequestParam String tanggalDikembalikan) {
        return ResponseEntity.ok(pengembalianService.hitungPengembalian(peminjaman, tanggalDikembalikan));
    }
}
