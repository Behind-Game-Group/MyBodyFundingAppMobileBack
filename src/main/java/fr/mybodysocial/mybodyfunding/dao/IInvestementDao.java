package fr.mybodysocial.mybodyfunding.dao;

import fr.mybodysocial.mybodyfunding.model.AppUser;
import fr.mybodysocial.mybodyfunding.model.Investment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface IInvestementDao extends IAbstractDao<Investment> {

    @Query(value = "SELECT * FROM app_user u WHERE u.id = (SELECT i.contributors_id FROM investment i WHERE i.project_id = ?);", nativeQuery = true)
    List<AppUser> findContributorsByProjectId(Long id);

    @Query(value = "SELECT COALESCE(SUM(i.amount),0) FROM investment as i; ", nativeQuery = true)
    Double sumOfInvestment();

    @Query(value = "SELECT SUM(i.amount) as total_investment FROM investment as i WHERE i.project_id = ?;", nativeQuery = true)
    double sumOfInvestmentForAProject(Long id);

    @Query(value = "SELECT COALESCE(SUM(i.amount),0) as total_investment FROM investment as i WHERE i.contributors_id = ?;", nativeQuery = true)
    double sumOfInvestmentForAUser(Long id);
}
