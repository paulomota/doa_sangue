package br.com.doasangue.producer;

import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.deltaspike.jpa.api.transaction.TransactionScoped;

public class EntityManagerProducer
{
    //or manual bootstrapping
    @PersistenceContext
    private EntityManager entityManager;

    @Produces
    @TransactionScoped
    protected EntityManager createEntityManager()
    {
        return this.entityManager;
    }

    protected void closeEntityManager(@Disposes EntityManager entityManager)
    {
        if (entityManager.isOpen())
        {
            entityManager.close();
        }
    }
}