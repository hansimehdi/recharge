package mg.recharge.dao;

import java.util.List;

import mg.recharge.entities.CreditCode;

public interface ICreditCodeDao {
    public CreditCode save(CreditCode creditCode);
    public List<CreditCode> listAll();
    public boolean isExist(CreditCode code);
}
