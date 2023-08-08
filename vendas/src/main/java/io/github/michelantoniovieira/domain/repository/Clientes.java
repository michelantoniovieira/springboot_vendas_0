package io.github.michelantoniovieira.domain.repository;

import io.github.michelantoniovieira.domain.entity.Cliente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class Clientes
{
/*
    usado junto com o jdbcTemplate
    private static String INSERT = "INSERT into cliente (nome) values (?)";
*/

    /*   private static String UPDATE = "UPDATE cliente set nome = ? WHERE id = ?";

    private static String SELECT_ALL = "SELECT * FROM cliente";


    private static String DELETE = "DELETE FROM cliente WHERE id = ?";
*/
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private EntityManager entityManager;

    /*
    Usado no jdbcTemplate
    public Cliente salvar(Cliente cliente)
    {
        jdbcTemplate.update(INSERT, new Object[]{cliente.getNome()});
        return cliente;
    }*/

   /* public Cliente atualizar(Cliente cliente)
    {
        jdbcTemplate.update(UPDATE, new Object[]{cliente.getNome(), cliente.getId()});
        return cliente;
    }

    public void deletar(Cliente cliente)
    {
        deletar(cliente.getId());
    }

    public void deletar(Integer id)
    {
        jdbcTemplate.update(DELETE, new Object[]{id});
    }

    public List<Cliente> buscarPorNome(String nome)
    {
        return jdbcTemplate.query(SELECT_ALL.concat(" where nome like ? "), new Object[]{"%" + nome + "%"}, obterClienteMapper());
    }

    }

        private RowMapper<Cliente> obterClienteMapper()
    {
        return new RowMapper<Cliente>()
        {
            @Override
            public Cliente mapRow(ResultSet rs, int rowNum) throws SQLException
            {
                Integer id = rs.getInt("id");
                String nome = rs.getString("nome");
                return new Cliente(id, nome);
            }
        };
    }

    */

    @Transactional
    public Cliente salvar(Cliente cliente)
    {
        entityManager.persist(cliente);
        return cliente;
    }

    @Transactional
    public Cliente atualizar(Cliente cliente)
    {
        entityManager.merge(cliente);
        return cliente;
    }

    @Transactional
    public void deletar(Cliente cliente)
    {
        if(!entityManager.contains(cliente))
        {
           cliente = entityManager.merge(cliente);
        }
        entityManager.remove(cliente);
    }

    @Transactional
    public void deletar(Integer id)
    {
        Cliente cliente = entityManager.find(Cliente.class, id);
    }


    //readOnly serve para otimizar a leitura ja que este método é usado para consulta
    @Transactional(readOnly = true)
    public List<Cliente> buscarPorNome(String nome)
    {
        String jpql = "SELECT c FROM Cliente c WHERE c.nome like :nome";
        TypedQuery<Cliente> query = entityManager.createQuery(jpql, Cliente.class);
        query.setParameter("nome", "%" + nome + "%");
        return query.getResultList();
    }

    @Transactional(readOnly = true)
    public List<Cliente> obterTodos()
    {
        return entityManager.createQuery("FROM Cliente", Cliente.class).getResultList();
    }

}
