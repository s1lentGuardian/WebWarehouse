package org.kharkiv.khpi.model.service;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import org.kharkiv.khpi.model.Supplier;
import org.kharkiv.khpi.model.repository.SupplierDAO;

import java.util.List;

@Stateless
public class SupplierService {

    @Inject
    private SupplierDAO supplierDAO;

    public List<Supplier> findAllSuppliers() {
        return supplierDAO.findAllSuppliers();
    }

    public Supplier save(Supplier supplier) {
        return supplierDAO.save(supplier);
    }

    public void delete(Long id) {
        supplierDAO.delete(id);
    }

    public void update(Long id, String country, String city, String phoneNumber) {
        Supplier supplier = supplierDAO.findById(id);

        supplier.setCountry(country);
        supplier.setCity(city);
        supplier.setPhoneNumber(phoneNumber);

        supplierDAO.save(supplier);
    }
}
