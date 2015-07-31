package get.me.a.tiramisu.repo;

import get.me.a.tiramisu.constantes.Arrondissement;
import get.me.a.tiramisu.entity.Tiramisu;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

/**
 * Test DAO tiramisu
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/test/resources/application-context.xml", "file:src/test/resources/applicationContext-jpa.xml" })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class, TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class })
@DatabaseSetup("file:src/test/resources/tiramisuJeux.xml")
public class TiramisuDAOTest {

	// @Spy
	@Autowired
	ITiramisuQueryDAO tiradao;

	// @Spy
	@Autowired
	TiramisuDAO tirarepo;

	@PersistenceContext
	private EntityManager entityManager;

	@Before
	public void setUp() {
	}

	/**
	 * verifie la creation de requete avec pagination
	 */
	@Test
	public void testcreationQueryTiramisuCAS01() {
		// given
		// when
		TypedQuery<Tiramisu> query = tiradao.creationQueryTiramisu(Arrondissement.ARR_13, 14, 20);

		// then
		Assert.assertEquals(14, query.getFirstResult());
		Assert.assertEquals(20, query.getMaxResults());
	}

	/**
	 * verifie la recuperation par arrondissement Cas ou il y en a
	 */
	@Test
	public void testgetTiramisuByArrondissementCAS00() {
		List<Tiramisu> tiramisus = tiradao.getTiramisuByArrondissement(Arrondissement.ARR_13);
		Assert.assertEquals(3, tiramisus.size());
	}

	/**
	 * verifie la recuperation par arrondissement Cas ou il n'y en a pas
	 */

	@Test
	public void testgetTiramisuByArrondissementCAS01() {
		List<Tiramisu> tiramisus = tiradao.getTiramisuByArrondissement(Arrondissement.ARR_15);
		Assert.assertEquals(0, tiramisus.size());
	}

	/**
	 * Verifie la façon de compter le nombre de tiramisu valide dans un
	 * arrondissement
	 */
	@Ignore
	@Test
	public void testCountAllValidateTiramisuInArrondissement() {
		long nbcorrectTiramisu = tiradao.countAllValidateTiramisuInThisArrondissement("75013");
		Assert.assertEquals(2, nbcorrectTiramisu);
	}

	/**
	 * verifie la façon de compter le nombre de tiramisu valide
	 */
	@Ignore
	@Test
	public void testCountAllValidateTiramisu() {
		long nbcorrectTiramisu = tiradao.countAllValidateTiramisu();
		Assert.assertEquals(3, nbcorrectTiramisu);
	}

}
