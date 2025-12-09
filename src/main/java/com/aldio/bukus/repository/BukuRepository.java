package com.aldio.bukus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aldio.bukus.model.Buku;

@Repository
public interface BukuRepository extends JpaRepository<Buku, Long> {
}
