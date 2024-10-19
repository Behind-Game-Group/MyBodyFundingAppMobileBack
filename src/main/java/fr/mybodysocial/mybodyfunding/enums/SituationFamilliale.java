package fr.mybodysocial.mybodyfunding.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SituationFamilliale {
    marié("M"), pacsé("O"), divorcé("D"), séparé("D"), célibataire("C"), veuf("V");

    private String valeur;


}
