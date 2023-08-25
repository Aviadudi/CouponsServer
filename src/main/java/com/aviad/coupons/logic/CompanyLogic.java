package com.aviad.coupons.logic;


import com.aviad.coupons.dal.ICompaniesDal;
import com.aviad.coupons.dto.Company;
import com.aviad.coupons.entities.CompanyEntity;
import com.aviad.coupons.enums.ErrorType;
import com.aviad.coupons.exceptions.ApplicationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;
@Service
public class CompanyLogic {
    private ICompaniesDal companiesDal;

    public CompanyLogic(ICompaniesDal companiesDal) {
        this.companiesDal = companiesDal;
    }

    public Integer addCompany(Company company) throws ApplicationException {
        validateCompanyData(company);
        CompanyEntity companyEntity = new CompanyEntity(company);
        this.companiesDal.save(companyEntity);
        company.setId(companyEntity.getId());
        return company.getId();
    }

    public Company getCompany(Integer id) {
        return this.companiesDal.getCompanyById(id);
    }

    public void deleteCompany(Integer id) throws ApplicationException {
        this.companiesDal.deleteById(id);
    }

    public void updateCompany(Company company) throws ApplicationException {
        validateCompanyData(company);
        CompanyEntity companyEntity = new CompanyEntity(company);
        this.companiesDal.save(companyEntity);
    }

    public List<Company> getCompanies() throws ApplicationException {
        return this.companiesDal.getCompanies();
    }

    private void validateCompanyData(Company company) throws ApplicationException {
        validateCompanyName(company.getName());
        validateCompanyAddress(company.getAddress());
        validateCompanyPhone(company.getPhone());
        validateCompanyEmail(company.getEmail());

    }

    private void validateCompanyEmail(String email) throws ApplicationException {
        String emailRegex = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(emailRegex);
        if (email == null || email.isEmpty()) {
            throw new ApplicationException(ErrorType.EMAIL_NOT_PROVIDED);
        }
        if (!pattern.matcher(email).matches()) {
            throw new ApplicationException(ErrorType.INVALID_EMAIL);
        }
    }

    private void validateCompanyPhone(String phone) throws ApplicationException{
        if (phone.length() != 10) {
            throw new ApplicationException(ErrorType.PHONE_NUMBER_IS_INCORRECT);
        }
    }

    private void validateCompanyAddress(String address) throws ApplicationException {
        if (address.length() < 5) {
            throw new ApplicationException(ErrorType.COMPANY_ADDRESS_TOO_SHORT);
        }
        if (address.length() > 40) {
            throw new ApplicationException(ErrorType.COMPANY_ADDRESS_TOO_LONG);
        }
    }

    private void validateCompanyName(String companyName) throws ApplicationException {
        if (companyName.length() < 5) {
            throw new ApplicationException(ErrorType.COMPANY_NAME_TOO_SHORT);
        }
        if (companyName.length() > 25) {
            throw new ApplicationException(ErrorType.COMPANY_NAME_TOO_LONG);
        }
    }
}
