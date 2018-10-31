package dao;

public interface CustomerDAO {

    void save(CustomerDAO customer);

    CustomerDAO getCustomer(long id);

}
