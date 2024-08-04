package com.hadjsalem.agencevoyage.controller;
import com.hadjsalem.agencevoyage.services.Impl.ChauffeurServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/chauffeurs")
@RequiredArgsConstructor
public class ChauffeurController {

    private  final ChauffeurServiceImpl chauffeurService;






}
