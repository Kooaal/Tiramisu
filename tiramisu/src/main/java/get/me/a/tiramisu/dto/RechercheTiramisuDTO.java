package get.me.a.tiramisu.dto;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.jpa.entity.RooJpaEntity;

/**
 * dto de recherche des tiramisus
 * Roojpaentity necessaire pour creer les views
 */
@RooJavaBean
@RooJpaActiveRecord(finders = { "findRechercheTiramisuDTOByCodepostalLike" })
public class RechercheTiramisuDTO {

    /**
     * nom du tiramisu
     */
//    private String name;

    /**
     * mots clés (cherché dans le nom et la description)
     */
    private String[] keywords;

    /**
     *
     */
    private String adresse;

    private String ville;

    @Size(max = 5)
    @Pattern(regexp = "^[0-9]*$")
    private String codepostal;

    /**
     */
    @NotNull
    @Digits(integer = 3, fraction = 2)
    private float prix;
    
//    public String getName() {
//        return this.name;
//    }
//    
//    public void setName(String name) {
//        this.name = name;
//    }
    
    public String[] getKeywords() {
        return this.keywords;
    }
    
    public void setKeywords(String[] keywords) {
        this.keywords = keywords;
    }
    
    public String getAdresse() {
        return this.adresse;
    }
    
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
    
    public String getVille() {
        return this.ville;
    }
    
    public void setVille(String ville) {
        this.ville = ville;
    }
    
    public String getCodepostal() {
        return this.codepostal;
    }
    
    public void setCodepostal(String codepostal) {
        this.codepostal = codepostal;
    }
    
    public float getPrix() {
        return this.prix;
    }
//    public static Long countFindRechercheTiramisuDTOByCodepostalLike(String codepostal) {
//        if (codepostal == null || codepostal.length() == 0) throw new IllegalArgumentException("The codepostal argument is required");
//        codepostal = codepostal.replace('*', '%');
//        if (codepostal.charAt(0) != '%') {
//            codepostal = "%" + codepostal;
//        }
//        if (codepostal.charAt(codepostal.length() - 1) != '%') {
//            codepostal = codepostal + "%";
//        }
//        EntityManager em = entityManager();
//        TypedQuery q = em.createQuery("SELECT COUNT(o) FROM RechercheTiramisuDTO AS o WHERE LOWER(o.codepostal) LIKE LOWER(:codepostal)", Long.class);
//        q.setParameter("codepostal", codepostal);
//        return ((Long) q.getSingleResult());
//    }
//    
//    public static TypedQuery<RechercheTiramisuDTO> findRechercheTiramisuDTOByCodepostalLike(String codepostal) {
//        if (codepostal == null || codepostal.length() == 0) throw new IllegalArgumentException("The codepostal argument is required");
//        codepostal = codepostal.replace('*', '%');
//        if (codepostal.charAt(0) != '%') {
//            codepostal = "%" + codepostal;
//        }
//        if (codepostal.charAt(codepostal.length() - 1) != '%') {
//            codepostal = codepostal + "%";
//        }
//        EntityManager em = entityManager();
//        TypedQuery<RechercheTiramisuDTO> q = em.createQuery("SELECT o FROM RechercheTiramisuDTO AS o WHERE LOWER(o.codepostal) LIKE LOWER(:codepostal)", RechercheTiramisuDTO.class);
//        q.setParameter("codepostal", codepostal);
//        return q;
//    }
//    
//    public static TypedQuery<RechercheTiramisuDTO> findRechercheTiramisuDTOByCodepostalLike(String codepostal, String sortFieldName, String sortOrder) {
//        if (codepostal == null || codepostal.length() == 0) throw new IllegalArgumentException("The codepostal argument is required");
//        codepostal = codepostal.replace('*', '%');
//        if (codepostal.charAt(0) != '%') {
//            codepostal = "%" + codepostal;
//        }
//        if (codepostal.charAt(codepostal.length() - 1) != '%') {
//            codepostal = codepostal + "%";
//        }
//        EntityManager em = entityManager();
//        StringBuilder queryBuilder = new StringBuilder("SELECT o FROM RechercheTiramisuDTO AS o WHERE LOWER(o.codepostal) LIKE LOWER(:codepostal)");
//        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
//            queryBuilder.append(" ORDER BY ").append(sortFieldName);
//            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
//                queryBuilder.append(" ").append(sortOrder);
//            }
//        }
//        TypedQuery<RechercheTiramisuDTO> q = em.createQuery(queryBuilder.toString(), class);
//        q.setParameter("codepostal", codepostal);
//        return q;
//    }
}
