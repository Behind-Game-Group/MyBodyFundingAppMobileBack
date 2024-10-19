package fr.mybodysocial.mybodyfunding.service;

import fr.mybodysocial.mybodyfunding.dao.IAppUserDao;
import fr.mybodysocial.mybodyfunding.dao.IInvestementDao;
import fr.mybodysocial.mybodyfunding.dao.IProjectDao;
import fr.mybodysocial.mybodyfunding.dto.handler.InvestmentDtoHandler;
import fr.mybodysocial.mybodyfunding.dto.handler.UserDtoHandler;
import fr.mybodysocial.mybodyfunding.dto.in.InvestmentDtoIn;
import fr.mybodysocial.mybodyfunding.dto.out.AppUserDtoOut;
import fr.mybodysocial.mybodyfunding.dto.out.InvestmentDtoOut;
import fr.mybodysocial.mybodyfunding.model.AppUser;
import fr.mybodysocial.mybodyfunding.model.Investment;
import fr.mybodysocial.mybodyfunding.model.Project;
import fr.mybodysocial.mybodyfunding.service.exception.EntityNotFoundException;
import fr.mybodysocial.mybodyfunding.service.exception.ProjectNotFoundException;
import fr.mybodysocial.mybodyfunding.service.interfaces.IInvestmentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class InvestmentService implements IInvestmentService {

    private final IProjectDao iProjectDao;
    private final IInvestementDao iInvestment;
    private final IAppUserDao iAppUserDao;


    /**
     * Investment system
     */

    @Override
    public List<InvestmentDtoOut> getAllInvestments() throws Exception {
        return InvestmentDtoHandler.entitestoDto(this.iInvestment.findAll());
    }

    @Override
    public List<AppUserDtoOut> getContributors(Long id) throws Exception {
        Project target = iProjectDao.findById(id).orElseThrow(ProjectNotFoundException::new);
        List<Investment> listOfInvestment = target.getContributors();
        List<AppUser> allContributors = new ArrayList<>();
        for (Investment i : listOfInvestment) {
            allContributors.add(i.getContributors());
        }
        return UserDtoHandler.dtosOutfromEntities(allContributors);
    }

    @Override
    public List<InvestmentDtoOut> getAllInvestmentForAProject(Long projectId) throws Exception {
        Project target = iProjectDao.findById(projectId).orElseThrow(ProjectNotFoundException::new);
        return InvestmentDtoHandler.entitestoDto(target.getContributors());
    }

    @Override
    public InvestmentDtoOut addInvestment(InvestmentDtoIn newInvestment) throws Exception {
        Project target = this.iProjectDao.findById(newInvestment.getIdProject()).orElseThrow(ProjectNotFoundException::new);
        AppUser appUser = iAppUserDao.findById(newInvestment.getIdContributors()).orElseThrow(EntityNotFoundException::new);
        Investment inv = target.investInProject(newInvestment, appUser);
        iInvestment.save(inv);
        return null;
    }

    @Override
    public InvestmentDtoOut deleteInvestment(Long investmentId) throws Exception {
        return null;
    }

    @Override
    public int getAllNumberOfContributors() throws Exception {
        return this.iInvestment.findAll().size();
    }

    @Override
    public double getAllAmountOfInvestment() throws Exception {
        return this.iInvestment.sumOfInvestment();
    }

    @Override
    public double getAmountOfInvestmentForAUser(Long id) throws Exception {
        return this.iInvestment.sumOfInvestmentForAUser(id);
    }

    /*************************************************************************************************************************/
}
