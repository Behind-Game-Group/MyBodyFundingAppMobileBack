package fr.mybodysocial.mybodyfunding.dto.handler;

import fr.mybodysocial.mybodyfunding.dto.out.InvestmentDtoOut;
import fr.mybodysocial.mybodyfunding.model.Investment;

import java.util.List;
import java.util.stream.Collectors;

public interface InvestmentDtoHandler {

    /**
     * entity to dto
     *
     * @param inv
     * @return dto
     * @throws Exception
     */
    static InvestmentDtoOut toDtoOut(Investment inv) throws Exception {
        var result = new InvestmentDtoOut();

        System.out.println("here");
        result.setContributor(UserDtoHandler.dtoOutFromEntity(inv.getContributors()));
        result.setProject(ProjectDtoHandler.toDtoOut(inv.getProject()));
        result.setAmount(inv.getAmount());
        result.setDateOfInvestment(inv.getDateOfInvestment());
        result.setTypeOfInvestment(inv.getTypeOfInvestment());
        result.setInvestmentDone(inv.isInvestmentDone());

        return result;
    }

    static List<InvestmentDtoOut> entitestoDto(List<Investment> comments) {

        return comments.stream().map(e -> {
            try {
                return InvestmentDtoHandler.toDtoOut(e);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }).collect(Collectors.toList());
    }
}
