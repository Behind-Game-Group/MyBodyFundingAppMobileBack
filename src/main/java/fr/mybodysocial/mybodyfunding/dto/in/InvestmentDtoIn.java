package fr.mybodysocial.mybodyfunding.dto.in;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@Data
public class InvestmentDtoIn {

    @NonNull
    private long idContributors;
    @NonNull
    private long idProject;
    @NonNull
    private double amount;
    @NonNull
    private String typeOfInvestment;
}
