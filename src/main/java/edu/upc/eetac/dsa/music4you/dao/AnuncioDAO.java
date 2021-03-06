package edu.upc.eetac.dsa.music4you.dao;

import edu.upc.eetac.dsa.music4you.entity.Anuncio;
import edu.upc.eetac.dsa.music4you.entity.AnuncioCollection;

import java.io.InputStream;
import java.sql.SQLException;

/**
 * Created by pauli on 29/03/2016.
 */
public interface AnuncioDAO {

    Anuncio createAnuncio(String userid, String subject, String description, double precio, int type, InputStream image) throws SQLException;
    Anuncio getAnuncioById(String id) throws SQLException;
    AnuncioCollection getAnuncios() throws SQLException;
    Anuncio updateAnuncio(String id, String subject, String description, double precio) throws SQLException;
    boolean deleteAnuncio(String id) throws SQLException;

}
