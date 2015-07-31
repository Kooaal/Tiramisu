package get.me.a.tiramisu.repo;
import get.me.a.tiramisu.entity.Lieu;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.roo.addon.layers.repository.jpa.RooJpaRepository;

@RooJpaRepository(domainType = Lieu.class)
public interface LieuDAO extends JpaRepository<Lieu, Long> {
}
