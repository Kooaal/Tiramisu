package get.me.a.tiramisu.web;
import get.me.a.tiramisu.dto.RechercheTiramisuDTO;
import get.me.a.tiramisu.entity.Tiramisu;
import get.me.a.tiramisu.service.TiramisuService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.web.mvc.controller.finder.RooWebFinder;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
/**
 *  Controller de la recherche de tiramisu
 * @author loxos
 *
 */
@RequestMapping("/recherchetiramisudto")
@Controller
//@RooWebScaffold(path = "recherchetiramisudto", formBackingObject = RechercheTiramisuDTO.class)
//@RooWebFinder
public class RechercheControler {

    @Autowired
    TiramisuService tiramisuService;

    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid RechercheTiramisuDTO rechercheTiramisuDTO, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, rechercheTiramisuDTO);
            return "recherchetiramisudto/create";
        }
        //enleve toutes les données du model
        uiModel.asMap().clear();
        List<Tiramisu> tiramisus = tiramisuService.getTiramisus(rechercheTiramisuDTO);
        uiModel.addAttribute("tiramisus", tiramisus);
        //renvoie ver la liste des tiramisus
        return "tiramisus/list";
    }

    void populateEditForm(Model uiModel, RechercheTiramisuDTO rechercheTiramisuDTO) {
        uiModel.addAttribute("rechercheTiramisuDTO", rechercheTiramisuDTO);
    }

    @RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, @RequestParam(value = "sortFieldName", required = false) String sortFieldName, @RequestParam(value = "sortOrder", required = false) String sortOrder, Model uiModel) {
        return "recherchetiramisudto/list";
    }
}
