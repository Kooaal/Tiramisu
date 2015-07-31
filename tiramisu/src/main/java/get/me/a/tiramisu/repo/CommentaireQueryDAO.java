package get.me.a.tiramisu.repo;

import get.me.a.tiramisu.entity.Commentaire;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
@Component
public class CommentaireQueryDAO {

	@PersistenceContext
	EntityManager em;
	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("titre", "commentaire", "note", "dateajout", "datevalidation", "datesuppression", "tiramisu");

	
	/**
	 * recuperation des commentaires selon un ordre particulier, avec un nombre max de résultat à partir d'un certain résultat
	 * @param firstResult
	 * @param maxResults
	 * @param sortFieldName
	 * @param sortOrder
	 * @return
	 */
    public  List<Commentaire> findCommentaireEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Commentaire o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return em.createQuery(jpaQuery, Commentaire.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();

    }
	
    public List<Commentaire> findAllCommentaires(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Commentaire o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return em.createQuery(jpaQuery, Commentaire.class).getResultList();
    }
    
    @Transactional
    public void persist(Commentaire commentaire){
    	em.persist(commentaire);
    }
    
    @Transactional
    public Commentaire merge(Commentaire commentaire){
    	Commentaire merged = em.merge(commentaire);
        em.flush();
        return merged;
    }

    public Commentaire findCommentaire(long id){
    	return em.find(Commentaire.class, id);
    }
    
    @Transactional
	public void remove(Commentaire commentaire) {
		 if (em.contains(this)) {
	            em.remove(this);
	        } else {
	            Commentaire attached = findCommentaire(commentaire.getId());
	            em.remove(attached);
	        }
		
	}

}
