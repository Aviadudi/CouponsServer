package com.aviad.coupons.dal;

import com.aviad.coupons.dto.Company;
import com.aviad.coupons.entities.CompanyEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ICompaniesDal extends CrudRepository<CompanyEntity, Integer> {

    @Query("SELECT new com.aviad.coupons.dto.Company (c.id, c.name, c.address, c.phone, c.email) FROM CompanyEntity c")
    List<Company> getCompanies();

    @Query("SELECT new com.aviad.coupons.dto.Company (c.id, c.name, c.address, c.phone, c.email) FROM CompanyEntity c WHERE id = :id")
    Company getCompanyById(@Param("id") int id);
}
