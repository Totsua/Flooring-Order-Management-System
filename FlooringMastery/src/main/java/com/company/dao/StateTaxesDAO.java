package com.company.dao;

import com.company.model.StateTaxes;

import java.util.List;

public interface StateTaxesDAO {
    List<StateTaxes> getAllStateTax() throws FilePersistenceException;
}
