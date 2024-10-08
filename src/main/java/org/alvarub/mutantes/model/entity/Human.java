package org.alvarub.mutantes.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter @Setter
public class Human {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @NotNull
    private String name;

    private boolean isMutant = false;

    @NotNull
    @ElementCollection
    @Transient
    private List<String> dna;

    private String fullDna;

    //
    public void setFullDna(List<String> dna){
        this.fullDna = String.join("-", this.dna);
    }
}
