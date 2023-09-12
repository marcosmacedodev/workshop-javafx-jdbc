package daos.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import daos.DepartmentDAO;
import db.DB;
import db.DbException;
import entities.Department;

public class DepartmentDaoImpl implements DepartmentDAO{

	private Connection conn;
	
	public DepartmentDaoImpl(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void insert(Department entity) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO department (Name) "
					+"VALUES "
					+"(?)",
					Statement.RETURN_GENERATED_KEYS
					);
			st.setString(1, entity.getName());
			int rowsAffected = st.executeUpdate();
			if(rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if(rs.first()) {
					Integer id = rs.getInt(1);
					entity.setId(id);
				}
				DB.closeResultSet(rs);
			}
		}
		catch(SQLException ex) {
			throw new DbException(ex.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void update(Department entity) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE department "
					+"SET Name = ? "
					+"WHERE Id = ?");
			st.setString(1, entity.getName());
			st.setInt(2, entity.getId());
			st.executeUpdate();
		}
		catch(SQLException ex) {
			throw new DbException(ex.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"DELETE FROM department "
					+"WHERE Id = ?"
					);
			st.setInt(1, id);
			st.executeUpdate();
		}
		catch(SQLException ex) {
			throw new DbException(ex.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public Department findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT * FROM department "
					+"WHERE Id = ?");
			st.setInt(1, id);
			rs = st.executeQuery();
			if(rs.first()) {
				String name = rs.getString("Name");
				return new Department(id, name);
			}
			return null;
		}
		catch(SQLException ex) {
			throw new DbException(ex.getMessage());
		}
		finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
	}

	@Override
	public List<Department> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT * FROM department "
					+"ORDER BY Name");
			rs = st.executeQuery();
			List<Department> deps = new ArrayList<>();
			while(rs.next()) {
				Integer id = rs.getInt("Id");
				String name = rs.getString("Name");
				deps.add(new Department(id, name));
			}
			return deps;
		}
		catch(SQLException ex) {
			throw new DbException(ex.getMessage());
		}
		finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
	}
}
