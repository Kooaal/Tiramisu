package get.me.a.tiramisu.repo;

import get.me.a.tiramisu.entity.Commentaire;
import get.me.a.tiramisu.entity.Lieu;

import org.springframework.data.jpa.repository.JpaRepository;
/**
 * Repo Spring DATA des commentaires
 * @author loxos
 *
 */
public interface CommentaireDAO extends JpaRepository<Commentaire, Long>  {

}
