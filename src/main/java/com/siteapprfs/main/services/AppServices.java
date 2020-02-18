package com.siteapprfs.main.services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class AppServices extends DatabaseConnection {
	
	private static final Logger logger = LoggerFactory.getLogger(AppServices.class);
	
	public AppServices() {
		super();
	}
	
	public AppServices(DataSource ds) {
		super(ds);
	}
	
	public void createService(Servs srv){
		PreparedStatement ps = null;
		String sql = "insert into services(nome,descricao,conteudo,img) values(?,?,?,?)";
		try{
			PreparedStatement smt =  super.prepareStatement(sql);
			smt.setString(1, srv.getNome());
			smt.setString(2, srv.getDescricao());
			smt.setString(3, srv.getConteudo());
			smt.setString(4, srv.getImg());
			smt.execute();
			smt.close();	
		}catch(SQLException|NullPointerException e ){
			logger.error("Error CREATE >> ",e);
		}
		finally {
			closeCn(ps);
		}
	}

	private void closeCn(PreparedStatement ps) {
		try {
		super.closeConn(ps);
		}catch(SQLException|NullPointerException e ) {
			logger.error("Error closeConn >> ", e);
		}
	}
	
	private void closeCn(PreparedStatement ps, ResultSet result) {
		closeCn(ps);
		if(result != null) {
			try {
				result.close();
			} catch (SQLException e) {
				logger.error("Error Result >> ",e);
			}
		}
	}
	
	private void addService(List<Servs> srv, ResultSet result) throws SQLException {
		Servs sr = new Servs();
		sr.setId(result.getInt("id"));
		sr.setNome(result.getString("nome"));
		sr.setConteudo(result.getString("conteudo"));
		sr.setDescricao(result.getString("descricao"));
		sr.setImg(result.getString("img"));
		srv.add(sr);
	}
	
	public List<Servs> readService(){
		List<Servs> srv = new ArrayList<>();
		String sql = " SELECT * FROM services ORDER by nome ASC ";
		PreparedStatement ps = null;
		ResultSet result = null;
		try{
			ps =  super.prepareStatement(sql);
			result = ps.executeQuery();
			while(result.next()){
				addService(srv, result);
			}
		}
		catch(SQLException|NullPointerException e){
			logger.error("Error READ >> ",e);
		}
		finally {
			closeCn(ps, result);
		}
		return srv;
	}  //Busca todos os registros

	
	public List<Servs> readService(int id){
		List<Servs> srv = new ArrayList<>();
		String sql = "SELECT * FROM services where id=? ";
		PreparedStatement ps = null;
		ResultSet rs =  null ;
		try{
			ps = prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			while(rs.next()){
				addService(srv, rs);
			}
		}
		catch(SQLException e){
			logger.error("Error READ >> ",e);
		}finally {
			closeCn(ps, rs);
		}
		return srv;
	} // Pesquisa por Id
	
	public List<Servs> readService(String nome){
		List<Servs> srv = new ArrayList<>();
		String sql = "SELECT * FROM services where nome LIKE ? ";
		PreparedStatement ps =  null;
		ResultSet rs = null;
		try{
			ps =  super.prepareStatement(sql);
			ps.setString(1, "%"+nome+"%");
			rs = ps.executeQuery();
			while(rs.next()){
				addService(srv, rs);
			}
		}
		catch(SQLException|NullPointerException e){
			logger.error("Error READ<string> >> ",e);
		}finally {
			closeCn(ps,rs);
		}
		return srv;
	} 

	public void updateService(Servs srv){
		String sql = "UPDATE services SET nome=?, descricao =? , conteudo = ? , img =  ? WHERE id = ? ";
		PreparedStatement ps = null;
		try{
			ps = super.prepareStatement(sql);
			ps.setString(1, srv.getNome());
			ps.setString(2, srv.getDescricao());
			ps.setString(3, srv.getConteudo());
			ps.setString(4, srv.getImg());
			ps.setInt(5, srv.getId());
			ps.execute();
		}catch(SQLException|NullPointerException e){	
			logger.error("Error UPDATE  >> ",e);
		}
		finally {
			closeCn(ps);
		}
	}// CRUD UPDATE
	
	public void deleteService(Servs srv){
		String sql = "DELETE FROM services WHERE id=?";
		PreparedStatement ps = null;
		try{
			ps = super.prepareStatement(sql);
			ps.setInt(1, srv.getId());
			ps.execute();
		}catch(SQLException|NullPointerException e){
			logger.error("Error DELETE<srv>  >> ",e);
		}
		finally {
			closeCn(ps);
		}
	}// CRUD DELETE
		
	public void deleteService(Integer id){
		String sql = "DELETE FROM services WHERE id=?";
		PreparedStatement ps = null;
		try{
			ps = super.prepareStatement(sql);
			ps.setInt(1, id);
			ps.execute();
		}catch(SQLException|NullPointerException e){
			logger.error("Error DELETE  >> ",e);
		}
		finally {
			closeCn(ps);
		}
	}// CRUD DELETE
	
}
