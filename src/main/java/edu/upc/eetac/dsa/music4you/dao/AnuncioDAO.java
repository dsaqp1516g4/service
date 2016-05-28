package edu.upc.eetac.dsa.music4you.dao;

import edu.upc.eetac.dsa.music4you.entity.Anuncio;
import edu.upc.eetac.dsa.music4you.entity.AnuncioCollection;

import java.io.InputStream;
import java.sql.SQLException;

/**
 * Created by pauli on 29/03/2016.
 */
public interface AnuncioDAO {

    public Anuncio createAnuncio(String userid, String subject, String description, long precio, int type) throws SQLException;
    public Anuncio getAnuncioById(String id) throws SQLException;
    public AnuncioCollection getAnuncios() throws SQLException;
    public Anuncio updateAnuncio(String id, String subject, String description) throws SQLException;
    public boolean deleteAnuncio(String id) throws SQLException;

}
