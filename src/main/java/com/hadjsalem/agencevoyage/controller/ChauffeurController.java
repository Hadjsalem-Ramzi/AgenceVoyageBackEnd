package com.hadjsalem.agencevoyage.controller;

import com.hadjsalem.agencevoyage.services.Impl.ChauffeurServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/chauffeurs")
@RequiredArgsConstructor
public class ChauffeurController {

    private  final ChauffeurServiceImpl chauffeurService;

    @GetMapping("/error")
    public ResponseEntity<?> throwException(){
        return ResponseEntity.ok(chauffeurService.throwException());
    }





}
