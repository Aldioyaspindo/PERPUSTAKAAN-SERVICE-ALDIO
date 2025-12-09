package com.aldio.bukus.controller;


import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import com.aldio.bukus.model.Buku;
import com.aldio.bukus.service.BukuService;

@RestController
@RequestMapping("/api/buku")
public class BukuController {

    @Autowired
    private BukuService bukuService;

    // GET semua buku
    @GetMapping
    public List<Buku> getAllBuku() {
        return bukuService.getAllBuku();
    }

    // GET buku by ID
    @GetMapping("/{id}")
    public ResponseEntity<Buku> getBukuById(@PathVariable Long id) {
        Buku buku = bukuService.getBukuById(id);
        return (buku != null)
                ? ResponseEntity.ok(buku)
                : ResponseEntity.notFound().build();
    }

    // POST buat buku baru
    @PostMapping
    public Buku createBuku(@RequestBody Buku buku) {
        return bukuService.createBuku(buku);
    }

    // PUT / UPDATE buku
    @PutMapping("/{id}")
    public ResponseEntity<Buku> updateBuku(@PathVariable Long id, @RequestBody Buku dataBaru) {
        try {
            Buku updated = bukuService.updateBuku(id, dataBaru);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE buku by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBuku(@PathVariable Long id) {
        bukuService.deleteBuku(id);
        return ResponseEntity.ok().build();
    }
}
