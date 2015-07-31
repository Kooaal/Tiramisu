package get.me.a.tiramisu.web;

import get.me.a.tiramisu.entity.Lieu;
import get.me.a.tiramisu.entity.Tiramisu;

import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;
import org.springframework.roo.addon.web.mvc.controller.converter.RooConversionService;

/**
 * A central place to register application converters and formatters. 
 */
@RooConversionService
public class ApplicationConversionServiceFactoryBean extends FormattingConversionServiceFactoryBean {

	@Override
	protected void installFormatters(FormatterRegistry registry) {
		super.installFormatters(registry);
		// Register application converters and formatters
	}
	/**
	 * utiliser pour l'output des Instances Lieus sur les pages web
	 * @return
	 */
    public Converter<Lieu, String> getLieuToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<get.me.a.tiramisu.entity.Lieu, java.lang.String>() {
            public String convert(Lieu lieu) {
                return new StringBuilder().append(lieu.getNom()).append(" - "	).append(lieu.getCodepostal()).toString();
            }
        };
    }
    /**
     * conversion des instances Tiramisu en String
     * @return
     */
    public Converter<Tiramisu, String> getTiramisuToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<get.me.a.tiramisu.entity.Tiramisu, java.lang.String>() {
            public String convert(Tiramisu tiramisu) {
                return new StringBuilder().append(tiramisu.getName()).append("-").append(tiramisu.getLieu().getNom()).toString();
            }
        };
    }
}
