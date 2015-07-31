package get.me.a.tiramisu.service;

import java.util.List;

import org.springframework.roo.addon.layers.service.RooService;

import get.me.a.tiramisu.entity.Commentaire;
/**
 * 
 * @author loxos
 *
 */
@RooService(domainTypes = { get.me.a.tiramisu.entity.Commentaire.class })
public interface CommentaireService {

	public Commentaire findCommentaire(Long id);
/**
 * recuperation des commentaires ordonné selon un champs aveec un nombre de résultat <br>
 * ainsi qu'un numéro de départ(pagination)
 * @param firstResult résultat à partir duquel il faut renvoyer les commentaires
 * @param maxResults 
 * @param sortFieldName
 * @param sortOrderasc ou desc
 * @return
 */
	List<Commentaire> findCommentaireEntries(int firstResult, int maxResults,
			String sortFieldName, String sortOrder);
	
	/**
	 * donne le nombre de commentaire en base
	 * @return long (0 si rien)
	 */
	long countCommentaires();
	/**
	 * renvoie une liste de commentaire trié sur le critère donné et dans l'ordre donné
	 * @param sortFieldName
	 * @param sortOrder
	 * @return
	 */
	List<Commentaire> findAllCommentaires(String sortFieldName, String sortOrder);
	/**
	 * sauvegarde l'objet
	 * @param commentaire
	 */
	void persist(Commentaire commentaire);
	
	Commentaire merge(Commentaire commentaire);
	
	void remove(Commentaire commentaire);
	
}
