package com.utsaldioyaspindo.uts.controller;

import com.utsaldioyaspindo.uts.dto.transaksiDTO;
import com.utsaldioyaspindo.uts.model.Transaksi;
import com.utsaldioyaspindo.uts.service.TransaksiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transaksi")
public class ServiceController {
    @Autowired
    private TransaksiService transaksiService;

    // @PostMapping
    // public Transaksi createTransaksi(@RequestBody Transaksi transaksi){
    //     return transaksiService.saveTransaksi(transaksi);
    // }

    @PostMapping
    public Transaksi createTransaksi(@RequestBody transaksiDTO dto) {
    return transaksiService.createFromDTO(dto);
    }
    
    @GetMapping
    public List<Transaksi> getAllTransaksi(){
        return transaksiService.getAllTransaksi();
    } 

    @GetMapping("/{id}")
    public Transaksi getTransaksiById(@PathVariable Long id){
        return transaksiService.getTransaksiById(id);
    }

    @PutMapping("/{id}")
    public Transaksi updateTransaksi(@PathVariable Long id, @RequestBody Transaksi TransaksiDetails){
        return transaksiService.updateTransaksi(id, TransaksiDetails);
    }

    @DeleteMapping("/{id}")
    public String deleteTransaksi(Long id){
        return transaksiService.deleteTransaksi(id);
    }
}
