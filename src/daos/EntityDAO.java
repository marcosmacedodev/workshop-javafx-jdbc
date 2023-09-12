package daos;

import java.util.List;

public interface EntityDAO<Entity> {
	
	void insert(Entity entity);
	void update(Entity entity);
	void deleteById(Integer id);
	Entity findById(Integer id);
	List<Entity> findAll();
}
