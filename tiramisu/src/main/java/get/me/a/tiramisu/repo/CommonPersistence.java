package get.me.a.tiramisu.repo;
/**
 * Contient les utilitaires pour la base de données.
 * 
 * Ainsi que les noms des tables ( centraliser ici)
 * @author loxos
 *
 */
public interface CommonPersistence {

	
	public static String REQ_SELECT=" SELECT ";
	
	public static String REQ_SELECT_DISTINCT=" SELECT DISTINCT ";
	public static String REQ_FROM=" FROM ";
	
	public static String REQ_WHERE=" WHERE ";

	public static String REQ_LIKE=" LIKE ";

	public static String REQ_AND=" AND ";
	
	public static  String REQ_INF=" < ";

	public static String REQ_JOK="%";
	
	public static String REQ_INNERJOIN=" INNER JOIN ";
	
	public static String REQ_AS=" AS ";

	public static String REQ_IS_NOT_NULL=" IS NOT NULL";
	
	public static String REQ_IS_NULL=" IS NULL";
	
	public static String REQ_COUNT=" COUNT(*)";
	/**
	 * 
	 * 
	 * ============================================================
	 *                          NOM DES TABLES
	 * ============================================================
	 * 
	 * 
	 * 
	 * 
	 */
	final String TIRAMISU_NOM_TABLE=" Tiramisu ";
}

