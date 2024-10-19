package fr.mybodysocial.mybodyfunding.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;


@NoRepositoryBean
public interface IAbstractDao<T> extends JpaRepository<T, Long> {

}
