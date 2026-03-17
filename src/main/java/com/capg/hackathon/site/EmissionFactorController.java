package com.capg.hackathon.site;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/materials")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class EmissionFactorController {

    private final EmissionFactorRepository emissionFactorRepository;

    @GetMapping
    public ResponseEntity<List<EmissionFactor>> getAllMaterials() {
        List<EmissionFactor> materials = emissionFactorRepository.findAll().stream()
                .filter(ef -> ef.getCode().startsWith("MATERIAL_"))
                .toList();
        return ResponseEntity.ok(materials);
    }

    @PostMapping
    public ResponseEntity<EmissionFactor> addMaterial(@RequestBody EmissionFactor request) {
        String newCode = request.getCode().toUpperCase();
        if (!newCode.startsWith("MATERIAL_")) {
            newCode = "MATERIAL_" + newCode;
        }

        EmissionFactor newMaterial = EmissionFactor.builder()
                .code(newCode)
                .facteurKgCO2ParUnite(request.getFacteurKgCO2ParUnite())
                .build();

        EmissionFactor saved = emissionFactorRepository.save(newMaterial);
        return ResponseEntity.ok(saved);
    }
}