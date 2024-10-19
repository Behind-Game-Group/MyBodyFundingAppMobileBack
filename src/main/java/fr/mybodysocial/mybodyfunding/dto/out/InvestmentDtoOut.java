package fr.mybodysocial.mybodyfunding.dto.out;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@Data
public class InvestmentDtoOut {

    @NonNull
    private AppUserDtoOut contributor;
    @NonNull
    private ProjectDtoOut project;
    @NonNull
    private double amount;
    @NonNull
    private String typeOfInvestment;
    @NonNull
    private Date dateOfInvestment;
    @NonNull
    private boolean investmentDone;
}
