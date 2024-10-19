package fr.mybodysocial.mybodyfunding.service.interfaces;

import fr.mybodysocial.mybodyfunding.dto.in.InvestmentDtoIn;
import fr.mybodysocial.mybodyfunding.dto.out.AppUserDtoOut;
import fr.mybodysocial.mybodyfunding.dto.out.InvestmentDtoOut;

import java.util.List;

public interface IInvestmentService {

    List<InvestmentDtoOut> getAllInvestments() throws Exception;

    List<AppUserDtoOut> getContributors(Long id) throws Exception;

    //avoir tous les investissement d'un projet
    List<InvestmentDtoOut> getAllInvestmentForAProject(Long projectId) throws Exception;

    // Add un investissement
    InvestmentDtoOut addInvestment(InvestmentDtoIn newInvestment) throws Exception;

    // Delete un investissement

    InvestmentDtoOut deleteInvestment(Long investmentId) throws Exception;

    int getAllNumberOfContributors() throws Exception;

    double getAllAmountOfInvestment() throws Exception;

    double getAmountOfInvestmentForAUser(Long id) throws Exception;

}
