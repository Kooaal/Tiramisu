package get.me.a.tiramisu.web;

import get.me.a.tiramisu.constantes.Arrondissement;
import get.me.a.tiramisu.constantes.CommonConstantes;
import get.me.a.tiramisu.constantes.Constantes;
import get.me.a.tiramisu.entity.Tiramisu;
import get.me.a.tiramisu.service.LieuService;
import get.me.a.tiramisu.service.TiramisuService;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.List;

import javax.naming.Context;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.io.IOUtils;
import org.hibernate.validator.internal.util.privilegedactions.GetClassLoader;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

/**
 * Controler des tiramisus
 * 
 * @author loxos
 *
 */
@RequestMapping("/tiramisus")
@RooJson
@Controller
public class TiramisuControler implements CommonConstantes {

	/**
	 * Code métier des tiramisus
	 */
	@Autowired
	TiramisuService tiramisuService;

	/**
	 * code métier des lieux
	 */
	@Autowired
	LieuService lieuservice;

	/**
	 * contexte de la servlet
	 */
	@Autowired
	ServletContext context;

	/**
	 * image par défaut pour les tiramisus n'ayant pas d'image TODO :
	 * externaliser dans un fichier properties--> injection par Spring
	 */
	private String NOM_IMAGE_DEFAULT_TIRAMISU = "logo_tiramisu.png";

	/**
	 * recuperation Image d'un tiramisu
	 * 
	 * @param id
	 *            du tiramisu dont on veut recuperer l'image
	 * @param response
	 * @param model
	 *
	 * @return
	 * @throws IOException
	 */
	// FIXME: refacto pour stocker dans un dossier
	@RequestMapping(value = "/{id}/image", method = RequestMethod.GET)
	public String showImage(@PathVariable("id") Long id, HttpServletResponse response, Model model) throws IOException {
		Tiramisu tiramisu = tiramisuService.findTiramisu(id);
		if (tiramisu != null && tiramisu.getImage() != null && tiramisu.getImage().length != 0) {
			byte[] image = tiramisu.getImage();
			if (image != null) {
				try {
					response.setContentType(tiramisu.getContentType());
					OutputStream out = response.getOutputStream();
					IOUtils.copy(new ByteArrayInputStream(image), out);
					out.flush();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		// cas ou le tiramisu n'a pas d'image
		else {
			response.setContentType("image/jpeg");
			String uploadPath = context.getRealPath("") + File.separator + "images" + "/" + NOM_IMAGE_DEFAULT_TIRAMISU;
			File file = new File(uploadPath);
			InputStream is = new FileInputStream(file);
			byte[] image = IOUtils.toByteArray(is);
			OutputStream out = response.getOutputStream();
			IOUtils.copy(new ByteArrayInputStream(image), out);
			out.flush();
		}
		// TODO : changer l'image retourner par défaut
		return null;
	}

	/**
	 * permet de transformer les tableaux de byte en image (apparently)
	 * 
	 * @param request
	 * @param binder
	 * @throws ServletException
	 */
	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws ServletException {
		binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
	}

	/**
	 * POPULATE FORM remplit le formulaire pour la création/update
	 * 
	 * @param uiModel
	 * @param tiramisu
	 */
	void populateEditForm(Model uiModel, Tiramisu tiramisu) {
		uiModel.addAttribute("tiramisu", tiramisu);
		addDateTimeFormatPatterns(uiModel);
		uiModel.addAttribute("lieus", lieuservice.findAllLieus());
	}

	/**
	 * FORMAT DATE
	 * 
	 * Rajoute les formats de date au model pour l'affichage
	 * 
	 * @param uiModel
	 */
	void addDateTimeFormatPatterns(Model uiModel) {
		uiModel.addAttribute("tiramisu_dateajout_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
		uiModel.addAttribute("tiramisu_datevalidation_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
		uiModel.addAttribute("tiramisu_datesuppression_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
	}

	/**
	 * encode l'url selon l'encodage fourni par le client
	 * 
	 * @param pathSegment
	 *            url à encoder
	 * @param httpServletRequest
	 *            requête client
	 * @return nouvelle url
	 */
	String encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
		String enc = httpServletRequest.getCharacterEncoding();
		if (enc == null) {
			enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
		}
		try {
			pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
		} catch (UnsupportedEncodingException uee) {
		}
		return pathSegment;
	}

	/**
	 * SHOW TIRAMISU
	 * 
	 * @param id
	 *            du tiramisu à montrer
	 * @param uiModel
	 *            données à transférer
	 * @return
	 */
	@RequestMapping(value = "/{id}", produces = "text/html")
	public String show(@PathVariable("id") Long id, Model uiModel, HttpServletResponse httpsR) {
		addDateTimeFormatPatterns(uiModel);
		uiModel.addAttribute("tiramisu", tiramisuService.findTiramisu(id));
		uiModel.addAttribute("itemId", id);
		return "tiramisus/show";
	}

	/**
	 * (C)REATE TIRAMISU
	 * 
	 * @param tiramisu
	 *            objet
	 * @param bindingResult
	 * @param uiModel
	 * @param httpServletRequest
	 * @param multipartFile
	 *            image du tOiramisu
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
	public String create(@Valid Tiramisu tiramisu, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest, @RequestParam("image") MultipartFile multipartFile) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, tiramisu);
			return "tiramisus/create";
		}
		uiModel.asMap().clear();
		// set de la date de creation
		tiramisu.setDateajout(Calendar.getInstance());
		// set du content type de l'image
		tiramisu.setContentType(multipartFile.getContentType());
		tiramisuService.saveTiramisu(tiramisu);
		// redirection vers la page de show du tiramisu
		return "redirect:/tiramisus/" + encodeUrlPathSegment(tiramisu.getId().toString(), httpServletRequest);
	}

	/**
	 * FORMULAIRE CREATION TIRAMISU
	 * 
	 * @param uiModel
	 * @return
	 */
	@RequestMapping(params = "form", produces = "text/html")
	public String createForm(Model uiModel) {
		populateEditForm(uiModel, new Tiramisu());
		return "tiramisus/create";
	}

	/**
	 * (U)PDATE
	 * 
	 * @param tiramisu
	 * @param bindingResult
	 * @param uiModel
	 * @param httpServletRequest
	 * @return
	 */
	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
	public String update(@Valid Tiramisu tiramisu, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, tiramisu);
			return "tiramisus/update";
			// TODO sdsd
		}
		uiModel.asMap().clear();
		tiramisuService.updateTiramisu(tiramisu);
		return "redirect:/tiramisus/" + encodeUrlPathSegment(tiramisu.getId().toString(), httpServletRequest);
	}

	/**
	 * (R)ETRIEVE LIST TIRAMISU
	 * 
	 * @param page
	 *            que l'on veut
	 * @param size
	 *            nombre de tiramisu par parge
	 * @param sortFieldName
	 *            nom du champs servant pour le filtre
	 * @param sortOrder
	 *            ordre
	 * @param uiModel
	 * @return page de liste
	 */
	// davantage spring data
	@RequestMapping(produces = "text/html")
	public String list(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page, @RequestParam(value = "arrondissement", required = false) String codepostal,
			@RequestParam(value = "size", required = false) Integer size, @RequestParam(value = "sortFieldName", required = false) String sortFieldName,
			@RequestParam(value = "sortOrder", required = false) String sortOrder, Model uiModel, HttpServletRequest request) {
		// initialisation des valeurs
		initValue(size, page);
		// la conversion doit permettre d'éviter l'injection
		int sizeNo = size.intValue();
		float nrOfPages = 0;

		// si ordre sur un champs -> on ne renvoie que la page souhaité
		if (sortFieldName != null && sortOrder != null) {
			ordreChamp(sortOrder, sortFieldName, page, nrOfPages, uiModel, sizeNo);
		}
		// si critère sur le codepostal
		else if (codepostal != null) {
			critereCodePostal(codepostal, nrOfPages, uiModel, sizeNo);
		}
		// on renvoie un nombre de tiramisu
		else {
			// par défaut premier resultat =0 sinon premier resultat de la page
			final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
			getSetOfTiramisu(firstResult, sizeNo, uiModel, nrOfPages);
		}
		addDatesFormatAndNbPage(uiModel, nrOfPages);
		return "tiramisus/list";
	}

	/**
	 * init value Page and Size
	 * 
	 * @param size
	 *            nb tiramisus par page
	 * @param page
	 *            page souhaité
	 */
	private void initValue(Integer size, Integer page) {
		page = page == null ? new Integer(1) : page;
		size = (size == null || size > 20) ? Constantes.NB_TIRAMISU_LIST_PAGE : size;
	}

	/**
	 * renvoie un set de tiramisu
	 * 
	 * @param firstResult
	 *            de resultat
	 * @param sizeNo
	 *            taille de la page
	 * @param uiModel
	 *            données
	 * @param nrOfPages
	 *            nb de pages totales
	 */
	private void getSetOfTiramisu(int firstResult, int sizeNo, Model uiModel, float nrOfPages) {
		List<Tiramisu> resultat = tiramisuService.findTiramisuEntries(firstResult, sizeNo);
		// renvoie les resultats correspondant à la page demandé
		uiModel.addAttribute("tiramisus", resultat);
		nrOfPages = (float) tiramisuService.countAllTiramisus() / sizeNo;
	}

	/**
	 * rajoute le modèle des dates + le nombre de page max au model + l'attribut Arrondissement
	 * 
	 * @param uiModel
	 *            données
	 * @param nrOfPages
	 *            nb page max
	 */
	private void addDatesFormatAndNbPage(Model uiModel, float nrOfPages) {
		uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
		addDateTimeFormatPatterns(uiModel);
		uiModel.addAttribute("arrondissements", Arrondissement.getAllArrondissement());
	}

	/**
	 * recupere les tiramisus liés à un arrondissement
	 * 
	 * @param codepostal
	 *            souhaité
	 * @param nrOfPages
	 *            nombre de page avec ces résultats
	 * @param uiModel
	 *            model pour le passage de données
	 * @param sizeNo
	 *            nb tiramisu par page
	 */
	private void critereCodePostal(String codepostal, float nrOfPages, Model uiModel, int sizeNo) {
		Arrondissement arr = Arrondissement.getFromCodepostal(codepostal);
		// si arrondissement non null ou non séléctionné
		if (arr != null && !codepostal.equals(Arrondissement.ARR_NULL.getCodepostal())) {
			List<Tiramisu> resultats = tiramisuService.findAllTiramisuByArrondissement(arr);
			// TOCHECK
			nrOfPages = (float) tiramisuService.countAllTiramisusInThisArrondissement(codepostal);
			uiModel.addAttribute("tiramisus", resultats);
			uiModel.addAttribute("selectedcodepostal", arr.getCodepostal());
		}
		// si pas d'arrondissement->on renvoie tout et on selectionne le
		// nulle
		else {
			uiModel.addAttribute("tiramisus", tiramisuService.findAllTiramisus());
			nrOfPages = (float) tiramisuService.countAllTiramisus() / sizeNo;
			uiModel.addAttribute("selectedcodepostal", Arrondissement.ARR_NULL.getCodepostal());
		}
	}

	/**
	 * recupere les tiramisus pour une certaine page triés selon un certain
	 * ordre
	 * 
	 * @param sortOrder
	 *            ordre de tri
	 * @param sortFieldName
	 *            champs de tri
	 * @param page
	 *            souhaitée
	 * @param sizeNo
	 *            nombre de résultat par page
	 * @param nrOfPages
	 *            de page de résultat
	 * @param uiModel
	 *            model avec les données
	 */
	private void ordreChamp(String sortOrder, String sortFieldName, Integer page, float nrOfPages, Model uiModel, int sizeNo) {
		List<Tiramisu> resultats = tiramisuService.findAllOrdedByFieldAndPaged(new Sort(Direction.valueOf(sortOrder), sortFieldName), page - 1, sizeNo);
		uiModel.addAttribute("tiramisus", resultats);
		nrOfPages = (float) tiramisuService.countAllTiramisus() / sizeNo;
	}

	/**
	 * FORMULAIRE DE MAJ
	 * 
	 * @param id
	 * @param uiModel
	 * @return
	 */
	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
	public String updateForm(@PathVariable("id") Long id, Model uiModel) {
		populateEditForm(uiModel, tiramisuService.findTiramisu(id));
		return "tiramisus/update";
	}

	/**
	 * (D)ELETE
	 * 
	 * @param id
	 * @param page
	 * @param size
	 * @param uiModel
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
	public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		Tiramisu tiramisu = tiramisuService.findTiramisu(id);
		tiramisuService.deleteTiramisu(tiramisu);
		uiModel.asMap().clear();
		uiModel.addAttribute("page", (page == null) ? String.valueOf(Constantes.DEFAULT_TIRAMISU_PAGE) : page.toString());
		uiModel.addAttribute("size", (size == null) ? String.valueOf(Constantes.NB_TIRAMISU_LIST_PAGE) : size.toString());
		return "redirect:/tiramisus";
	}
}
