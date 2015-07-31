package get.me.a.tiramisu.entity;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PersistenceContext;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.jpa.entity.RooJpaEntity;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;

@Entity
public class Commentaire {

    /**
     */
    @NotNull
    private String titre;

    /**
     */
    private String commentaire;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    
    @Version
    @Column(name = "version")
    private Integer version;
    
    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Integer getVersion() {
        return this.version;
    }
    
    public void setVersion(Integer version) {
        this.version = version;
    }
    
    /**
     */
    @DecimalMin("0")
    @DecimalMax("5")
    @Digits(integer = 1, fraction = 0)
    private int note;
    
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Calendar dateajout;

    /**
     */
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Calendar datevalidation;

    /**
     */
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Calendar datesuppression;
    
    /**
     * reference vers le tiramisu visé
     */
    @ManyToOne
    private Tiramisu tiramisu;
    
//    @PersistenceContext
//    transient EntityManager entityManager;
//    
//    public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("titre", "commentaire", "note", "dateajout", "datevalidation", "datesuppression", "tiramisu");
//    
//    public static final EntityManager entityManager() {
//        EntityManager em = new Commentaire().entityManager;
//        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
//        return em;
//    }
//    
//    public static long countCommentaires() {
//        return entityManager().createQuery("SELECT COUNT(o) FROM Commentaire o", Long.class).getSingleResult();
//    }
//    
//    public static List<Commentaire> findAllCommentaires() {
//        return entityManager().createQuery("SELECT o FROM Commentaire o", Commentaire.class).getResultList();
//    }
//    
//    public static List<Commentaire> findAllCommentaires(String sortFieldName, String sortOrder) {
//        String jpaQuery = "SELECT o FROM Commentaire o";
//        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
//            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
//            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
//                jpaQuery = jpaQuery + " " + sortOrder;
//            }
//        }
//        return entityManager().createQuery(jpaQuery, Commentaire.class).getResultList();
//    }
//    
//    public static Commentaire findCommentaire(Long id) {
//        if (id == null) return null;
//        return entityManager().find(Commentaire.class, id);
//    }
//    
//    public static List<Commentaire> findCommentaireEntries(int firstResult, int maxResults) {
//        return entityManager().createQuery("SELECT o FROM Commentaire o", Commentaire.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
//    }
//    
//    public static List<Commentaire> findCommentaireEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
//        String jpaQuery = "SELECT o FROM Commentaire o";
//        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
//            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
//            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
//                jpaQuery = jpaQuery + " " + sortOrder;
//            }
//        }
//        return entityManager().createQuery(jpaQuery, Commentaire.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
//    }
//    
//    @Transactional
//    public void persist() {
//        if (this.entityManager == null) this.entityManager = entityManager();
//        this.entityManager.persist(this);
//    }
//    
//    @Transactional
//    public void remove() {
//        if (this.entityManager == null) this.entityManager = entityManager();
//        if (this.entityManager.contains(this)) {
//            this.entityManager.remove(this);
//        } else {
//            Commentaire attached = findCommentaire(this.id);
//            this.entityManager.remove(attached);
//        }
//    }
//    
//    @Transactional
//    public void flush() {
//        if (this.entityManager == null) this.entityManager = entityManager();
//        this.entityManager.flush();
//    }
//    
//    @Transactional
//    public void clear() {
//        if (this.entityManager == null) this.entityManager = entityManager();
//        this.entityManager.clear();
//    }
//    
//    @Transactional
//    public Commentaire merge() {
//        if (this.entityManager == null) this.entityManager = entityManager();
//        Commentaire merged = this.entityManager.merge(this);
//        this.entityManager.flush();
//        return merged;
//    }
    public String getTitre() {
        return this.titre;
    }
    
    public void setTitre(String titre) {
        this.titre = titre;
    }
    
    public String getCommentaire() {
        return this.commentaire;
    }
    
    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }
    
    public int getNote() {
        return this.note;
    }
    
    public void setNote(int note) {
        this.note = note;
    }
    
    public Calendar getDateajout() {
        return this.dateajout;
    }
    
    public void setDateajout(Calendar dateajout) {
        this.dateajout = dateajout;
    }
    
    public Calendar getDatevalidation() {
        return this.datevalidation;
    }
    
    public void setDatevalidation(Calendar datevalidation) {
        this.datevalidation = datevalidation;
    }
    
    public Calendar getDatesuppression() {
        return this.datesuppression;
    }
    
    public void setDatesuppression(Calendar datesuppression) {
        this.datesuppression = datesuppression;
    }
    
    public Tiramisu getTiramisu() {
        return this.tiramisu;
    }
    
    public void setTiramisu(Tiramisu tiramisu) {
        this.tiramisu = tiramisu;
    }

    
}
