package org.dao;

import jakarta.persistence.EntityManager;
import org.model.Livro;
import java.util.List;

public class LivroDAO {
    private EntityManager em;

    public LivroDAO(EntityManager em) {
        this.em = em;
    }

    public void salvar(Livro livro) {
        em.getTransaction().begin();
        if (livro.getId() == null) {
            em.persist(livro);
        } else {
            em.merge(livro);
        }
        em.getTransaction().commit();
    }

    public void remover(Long id) {
        em.getTransaction().begin();
        Livro livro = em.find(Livro.class, id);
        if (livro != null) {
            em.remove(livro);
        }
        em.getTransaction().commit();
    }

    public List<Livro> listarTodos() {
        return em.createQuery("SELECT l FROM Livro l", Livro.class).getResultList();
    }
}