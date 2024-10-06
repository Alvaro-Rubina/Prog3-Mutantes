package org.alvarub.mutantes.controller;

import jakarta.validation.Valid;
import org.alvarub.mutantes.model.dto.HumanDTO;
import org.alvarub.mutantes.model.dto.StatsDTO;
import org.alvarub.mutantes.service.IHumanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/human")
public class HumanController {

    @Autowired
    IHumanService humanService;

    @PostMapping("/mutant/")
    public ResponseEntity<String> saveMutant(@Valid @RequestBody HumanDTO humanDTO) {
        boolean isMutant = humanService.saveHuman(humanDTO);

        if (isMutant) {
            return ResponseEntity.ok("Mutant verified.");
        } else {
            return ResponseEntity.status(403).body("Human detected.");
        }
    }

    @GetMapping("/stats/")
    @ResponseBody
    public ResponseEntity<StatsDTO> getStats(){
        return ResponseEntity.ok(humanService.getStats());
    }
}
