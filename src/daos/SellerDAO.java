package daos;

import java.util.List;

import entities.Department;
import entities.Seller;

public interface SellerDAO extends EntityDAO<Seller>{
	List<Seller> findByDepartment(Department department);
}
