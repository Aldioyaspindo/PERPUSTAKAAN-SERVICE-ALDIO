package com.utsaldioyaspindo.uts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.utsaldioyaspindo.uts.model.Transaksi;

@Repository
public interface TransaksiRepository extends JpaRepository<Transaksi, Long> {
        
}