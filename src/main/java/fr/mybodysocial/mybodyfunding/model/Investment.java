package fr.mybodysocial.mybodyfunding.model;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import jakarta.persistence.*;
import java.util.Date;

@NoArgsConstructor(force = true)
@Data
@RequiredArgsConstructor
@Entity
public class Investment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private AppUser contributors;
    @ManyToOne
    private Project project;
    @NonNull
    private double amount;
    @NonNull
    private String typeOfInvestment;
    @NonNull
    private Date dateOfInvestment;
    @NonNull
    private boolean investmentDone;
}
