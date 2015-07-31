package get.me.a.tiramisu.repo;
import get.me.a.tiramisu.entity.Tiramisu;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.roo.addon.layers.repository.jpa.RooJpaRepository;
/**
 * en implemetant JPArepository, on recupere automatiquement toutes les opérations CRUD
 * @author loxos
 *
 */
@RooJpaRepository(domainType = Tiramisu.class)
public interface TiramisuDAO extends JpaRepository<Tiramisu, Long>{
	
}
