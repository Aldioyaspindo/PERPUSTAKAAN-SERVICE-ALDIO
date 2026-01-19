package com.aldio.bukus.controller;


import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;
import com.aldio.bukus.model.Buku;
import com.aldio.bukus.service.BukuService;

@Slf4j
@RestController
@RequestMapping("/api/buku")
public class BukuController {

    @Autowired
    private BukuService bukuService;

    @GetMapping
    public ResponseEntity<List<Buku>> getAllBuku() {
        log.info("berhasil oke");
        return ResponseEntity.ok(bukuService.getAllBuku());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Buku> getBukuById(@PathVariable Long id) {
        log.info("Berhasil Mendapatkan buku bedasarkan id", getBukuById(id));
        return ResponseEntity.ok(bukuService.getBukuById(id));
    }

    @PostMapping
    public ResponseEntity<Buku> createBuku(@RequestBody Buku buku) {
        log.info("berhsil Membuat Buku : {}", buku.getJudul());
        return ResponseEntity.ok(bukuService.createBuku(buku));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Buku> updateBuku(@PathVariable Long id, @RequestBody Buku dataBaru) {
        log.info("berhasil mengupdate buku");
        Buku updated = bukuService.updateBuku(id, dataBaru);
        return ResponseEntity.ok(updated);   
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBuku(@PathVariable Long id) {
        log.info("berhasil menghapus buku");
        bukuService.deleteBuku(id);
        return ResponseEntity.ok().build();
    }
}
