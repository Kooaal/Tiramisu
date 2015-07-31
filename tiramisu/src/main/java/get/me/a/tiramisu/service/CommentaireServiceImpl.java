package get.me.a.tiramisu.service;

import java.util.List;

import get.me.a.tiramisu.entity.Commentaire;
import get.me.a.tiramisu.repo.CommentaireDAO;
import get.me.a.tiramisu.repo.CommentaireQueryDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
/**
 * classe métier concernant les commentaires
 * @author loxos
 *
 */

@Component
public class CommentaireServiceImpl implements CommentaireService{
	@Autowired
	CommentaireDAO commentaireDAO;
	
	@Autowired
	CommentaireQueryDAO commentairequery;

	public void name() {
	}

	@Override
	public Commentaire findCommentaire(Long id) {
		return commentaireDAO.findOne(id);
	}
	
	@Override
	public  List<Commentaire> findCommentaireEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
		return commentairequery.findCommentaireEntries(firstResult, maxResults, sortFieldName, sortOrder);
	}

	@Override
	public long countCommentaires() {
		return commentaireDAO.count();
	}
	@Override
	public List<Commentaire> findAllCommentaires(String sortFieldName, String sortOrder) {
		return commentairequery.findAllCommentaires(sortFieldName, sortOrder);
	}

	@Override
	public void persist(Commentaire commentaire) {
		commentairequery.persist(commentaire);
	}

	@Override
	public Commentaire merge(Commentaire commentaire){
		return commentairequery.merge(commentaire);
	}
	
	@Override
	public void remove(Commentaire commentaire){
		commentairequery.remove(commentaire);
	}
	
}
