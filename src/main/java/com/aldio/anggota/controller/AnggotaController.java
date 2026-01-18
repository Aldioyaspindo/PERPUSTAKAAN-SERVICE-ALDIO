package com.aldio.anggota.controller;

import com.aldio.anggota.model.Anggota;
import com.aldio.anggota.service.AnggotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import  java.util.List;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/anggota")
public class AnggotaController {
    @Autowired
    private AnggotaService anggotaService;

    @PostMapping
    public ResponseEntity<Anggota> createAnggota(@RequestBody Anggota anggota){
        log.info("Membuat anggota baru: {}", anggota);
        return ResponseEntity.ok(anggotaService.saveAnggota(anggota));
    }
    
    @GetMapping
    public ResponseEntity<List<Anggota>> getAllAnggotas(){
        log.info("Mengambil semua anggota");
        return ResponseEntity.ok(anggotaService.getAllAnggotas());
    } 

    
    @GetMapping("/{id}")
    public ResponseEntity<Anggota> getAnggotaById(@PathVariable Long id){
        log.info("Mencari anggota dengan id: {}", id);
        return ResponseEntity.ok(anggotaService.getAnggotaById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Anggota> updateAnggota(@PathVariable Long id, @RequestBody Anggota anggota){
        log.info("Mengupdate anggota dengan id: {}", id);
        return ResponseEntity.ok(anggotaService.updateAnggota(id, anggota));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAnggota(Long id){
        log.info("Menghapus anggota dengan id: {}", id);
        return ResponseEntity.ok(anggotaService.deleAnggota(id));
    }
}
