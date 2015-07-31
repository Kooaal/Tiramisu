package get.me.a.tiramisu.repo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import get.me.a.tiramisu.constantes.TiramisuBeans;
import get.me.a.tiramisu.entity.Lieu;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component(TiramisuBeans.LIEU_DAO_QUERY)
public class LieuQueryDAO {

	@PersistenceContext
	EntityManager em;
	
	/**
	 * compte le nombre de lieu pour un codepostal
	 * @param codepostal
	 * @return Nombre
	 */
	  public  Long countFindLieusByCodepostalLike(String codepostal) {
	        if (codepostal == null || codepostal.length() == 0) throw new IllegalArgumentException("The codepostal argument is required");
	        codepostal = codepostal.replace('*', '%');
	        if (codepostal.charAt(0) != '%') {
	            codepostal = "%" + codepostal;
	        }
	        if (codepostal.charAt(codepostal.length() - 1) != '%') {
	            codepostal = codepostal + "%";
	        }
	        TypedQuery q = em.createQuery("SELECT COUNT(o) FROM Lieu AS o WHERE LOWER(o.codepostal) LIKE LOWER(:codepostal)", Long.class);
	        q.setParameter("codepostal", codepostal);
	        return ((Long) q.getSingleResult());
	    }
	    /**
	     * construit la requête pour le nombre de lieu
	     * @param codepostal
	     * @return requete à passer
	     */
	    public  TypedQuery<Lieu> findLieusByCodepostalLike(String codepostal) {
	        if (codepostal == null || codepostal.length() == 0) throw new IllegalArgumentException("The codepostal argument is required");
	        //interet de faire cela ?
	        codepostal = codepostal.replace('*', '%');
	        if (codepostal.charAt(0) != '%') {
	            codepostal = "%" + codepostal;
	        }
	        if (codepostal.charAt(codepostal.length() - 1) != '%') {
	            codepostal = codepostal + "%";
	        }
	        TypedQuery<Lieu> q = em.createQuery("SELECT o FROM Lieu AS o WHERE LOWER(o.codepostal) LIKE LOWER(:codepostal)", Lieu.class);
	        q.setParameter("codepostal", codepostal);
	        return q;
	    }
	    /**
	     * recuperation de l'ensemble des lieux d'un arrondissement
	     * @param codepostal
	     * @return
	     */
	    public List<Lieu> getAllLieuxFromThisArrondissement(String codepostal){
	    	TypedQuery<Lieu> query=findLieusByCodepostalLike(codepostal);
	    	return query.getResultList();
	    }
	    /**
	     * construit la requete pour recuperer les lieux de ce code postal dans un certain ordre
	     * @param codepostal
	     * @param sortFieldName
	     * @param sortOrder
	     * @return
	     */
	    public  TypedQuery<Lieu> findLieusByCodepostalLike(String codepostal, String sortFieldName, String sortOrder) {
	        if (codepostal == null || codepostal.length() == 0) throw new IllegalArgumentException("The codepostal argument is required");
	        codepostal = codepostal.replace('*', '%');
	        if (codepostal.charAt(0) != '%') {
	            codepostal = "%" + codepostal;
	        }
	        if (codepostal.charAt(codepostal.length() - 1) != '%') {
	            codepostal = codepostal + "%";
	        }
	        StringBuilder queryBuilder = new StringBuilder("SELECT o FROM Lieu AS o WHERE LOWER(o.codepostal) LIKE LOWER(:codepostal)");
	        if (Lieu.fieldNames4OrderClauseFilter.contains(sortFieldName)) {
	            queryBuilder.append(" ORDER BY ").append(sortFieldName);
	            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
	                queryBuilder.append(" ").append(sortOrder);
	            }
	        }
	        TypedQuery<Lieu> q = em.createQuery(queryBuilder.toString(), Lieu.class);
	        q.setParameter("codepostal", codepostal);
	        return q;
	    }
	    
	    
	    /**
	     * compte le nombre de lieu
	     * @return
	     */
	    public  long countLieus() {
	        return em.createQuery("SELECT COUNT(o) FROM Lieu o", Long.class).getSingleResult();
	    }
	    /**
	     * Recuperer tout les lieux avec le champs sur lequel se basait pour l'ordre
	     * @param sortFieldName
	     * @param sortOrder
	     * @return
	     */
	    public  List<Lieu> findAllLieus(String sortFieldName, String sortOrder) {
	        String jpaQuery = "SELECT o FROM Lieu o";
	        if (Lieu.fieldNames4OrderClauseFilter.contains(sortFieldName)) {
	            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
	            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
	                jpaQuery = jpaQuery + " " + sortOrder;
	            }
	        }
	        return em.createQuery(jpaQuery, Lieu.class).getResultList();
	    }
	    /**
	     *  Trouver un lieu à partir de son ID
	     * @param id
	     * @return
	     */
	    public  Lieu findLieu(Long id) {
	        if (id == null) return null;
	        return em.find(Lieu.class, id);
	    }
	    /**
	     * renvoie du nieme au Zieme lieux
	     * @param firstResult
	     * @param maxResults
	     * @return liste de lieu
	     */
	    public  List<Lieu> findLieuEntries(int firstResult, int maxResults) {
	        return em.createQuery("SELECT o FROM Lieu o", Lieu.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
	    }
	    /**
	     * Renvoi une liste de lieu avec un ordre et un Offset
	     * @param firstResult
	     * @param maxResults
	     * @param sortFieldName
	     * @param sortOrder
	     * @return
	     */
	    public  List<Lieu> findLieuEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
	        String jpaQuery = "SELECT o FROM Lieu o";
	        if (Lieu.fieldNames4OrderClauseFilter.contains(sortFieldName)) {
	            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
	            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
	                jpaQuery = jpaQuery + " " + sortOrder;
	            }
	        }
	        return em.createQuery(jpaQuery, Lieu.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
	    }
	    
	    @Transactional
	    public void persist() {
	        em.persist(this);
	    }
	    
	    @Transactional
	    public void remove(Lieu lieu) {
	        if (em.contains(lieu)) {
	        	em.remove(lieu);
	        } else {
	            Lieu attached = findLieu(lieu.getId());
	            em.remove(attached);
	        }
	    }
	    
	    @Transactional
	    public void flush() {
	        em.flush();
	    }
	    
	    @Transactional
	    public void clear() {
	        em.clear();
	    }
	    
	    @Transactional
	    public Lieu merge(Lieu lieu) {
	        Lieu merged = em.merge(lieu);
	        em.flush();
	        return merged;
	    }
	    
}
