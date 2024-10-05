package org.alvarub.mutantes.controller;

import jakarta.validation.Valid;
import org.alvarub.mutantes.model.dto.HumanDTO;
import org.alvarub.mutantes.service.IHumanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/human")
public class HumanController {

    @Autowired
    IHumanService humanService;

    @PostMapping("/mutant")
    public ResponseEntity<String> saveMutant(@Valid @RequestBody HumanDTO humanDTO) {
        boolean isMutant = humanService.saveHuman(humanDTO);

        if (isMutant) {
            return ResponseEntity.ok("Mutant verified.");
        } else {
            return ResponseEntity.status(403).body("Human detected.");
        }
    }
}
