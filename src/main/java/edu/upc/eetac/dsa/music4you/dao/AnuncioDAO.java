package edu.upc.eetac.dsa.music4you.dao;

import edu.upc.eetac.dsa.music4you.entity.Anuncio;
import edu.upc.eetac.dsa.music4you.entity.AnuncioCollection;

import java.sql.SQLException;

/**
 * Created by pauli on 29/03/2016.
 */
public interface AnuncioDAO {

    public Anuncio createSting(String userid, String subject, String description, String image, long precio, int type) throws SQLException;
    public Anuncio getStingById(String id) throws SQLException;
    public AnuncioCollection getStings() throws SQLException;
    public Anuncio updateSting(String id, String subject, String content) throws SQLException;
    public boolean deleteSting(String id) throws SQLException;

}
