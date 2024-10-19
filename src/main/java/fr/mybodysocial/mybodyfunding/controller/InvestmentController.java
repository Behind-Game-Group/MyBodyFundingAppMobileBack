package fr.mybodysocial.mybodyfunding.controller;

import fr.mybodysocial.mybodyfunding.dto.out.InvestmentDtoOut;
import fr.mybodysocial.mybodyfunding.service.interfaces.IInvestmentService;
import fr.mybodysocial.mybodyfunding.util.Constante;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(Constante.BASE_URL + "/investment")
@SecurityRequirement(name= "Bearer Authentification")
public class InvestmentController {

    private final IInvestmentService investmentService;


    @Operation(tags = {
            "Investment API"}, summary = "Get all of the investments of the application", description = "Will return all the investments of the application (only for admin)")
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/")
    public ResponseEntity<List<InvestmentDtoOut>> getAllInvestments() throws Exception {
        return ResponseEntity.ok(investmentService.getAllInvestments());
    }

    @Operation(tags = {"Investment API"}, summary = "Get the sum of all the investment in the application",
            description = "Will return the sum of the investment in the application")
    @GetMapping("/sumOfInvestment")
    public ResponseEntity<Double> getSumOfInvestment() throws Exception {
        return ResponseEntity.ok(investmentService.getAllAmountOfInvestment());
    }

    @Operation(tags = {"Investment API"}, summary = "Get the count of users who invest in the application",
            description = "Will return all users who invest in the application")
    @GetMapping("/sumOfCollaborator")
    public ResponseEntity<Integer> getSumOfCollaborator() throws Exception {
        return ResponseEntity.ok(investmentService.getAllNumberOfContributors());
    }
}
