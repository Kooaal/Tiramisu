// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package get.me.a.tiramisu.web;

import get.me.a.tiramisu.dto.RechercheTiramisuDTO;
import get.me.a.tiramisu.entity.Commentaire;
import get.me.a.tiramisu.entity.Lieu;
import get.me.a.tiramisu.entity.Tiramisu;
import get.me.a.tiramisu.service.CommentaireService;
import get.me.a.tiramisu.service.LieuService;
import get.me.a.tiramisu.service.TiramisuService;
import get.me.a.tiramisu.web.ApplicationConversionServiceFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;

privileged aspect ApplicationConversionServiceFactoryBean_Roo_ConversionService {
    
    declare @type: ApplicationConversionServiceFactoryBean: @Configurable;
    
    @Autowired
    CommentaireService ApplicationConversionServiceFactoryBean.commentaireService;
    
    @Autowired
    LieuService ApplicationConversionServiceFactoryBean.lieuService;
    
    @Autowired
    TiramisuService ApplicationConversionServiceFactoryBean.tiramisuService;
    
    public Converter<RechercheTiramisuDTO, String> ApplicationConversionServiceFactoryBean.getRechercheTiramisuDTOToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<get.me.a.tiramisu.dto.RechercheTiramisuDTO, java.lang.String>() {
            public String convert(RechercheTiramisuDTO rechercheTiramisuDTO) {
                return new StringBuilder().append(rechercheTiramisuDTO.getAdresse()).append(' ').append(rechercheTiramisuDTO.getVille()).append(' ').append(rechercheTiramisuDTO.getCodepostal()).append(' ').append(rechercheTiramisuDTO.getPrix()).toString();
            }
        };
    }
    
    public Converter<Long, RechercheTiramisuDTO> ApplicationConversionServiceFactoryBean.getIdToRechercheTiramisuDTOConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, get.me.a.tiramisu.dto.RechercheTiramisuDTO>() {
            public get.me.a.tiramisu.dto.RechercheTiramisuDTO convert(java.lang.Long id) {
                return RechercheTiramisuDTO.findRechercheTiramisuDTO(id);
            }
        };
    }
    
    public Converter<String, RechercheTiramisuDTO> ApplicationConversionServiceFactoryBean.getStringToRechercheTiramisuDTOConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, get.me.a.tiramisu.dto.RechercheTiramisuDTO>() {
            public get.me.a.tiramisu.dto.RechercheTiramisuDTO convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), RechercheTiramisuDTO.class);
            }
        };
    }
    
    public Converter<Commentaire, String> ApplicationConversionServiceFactoryBean.getCommentaireToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<get.me.a.tiramisu.entity.Commentaire, java.lang.String>() {
            public String convert(Commentaire commentaire) {
                return new StringBuilder().append(commentaire.getTitre()).append(' ').append(commentaire.getCommentaire()).append(' ').append(commentaire.getNote()).append(' ').append(commentaire.getDateajout()).toString();
            }
        };
    }
    
    public Converter<Long, Commentaire> ApplicationConversionServiceFactoryBean.getIdToCommentaireConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, get.me.a.tiramisu.entity.Commentaire>() {
            public get.me.a.tiramisu.entity.Commentaire convert(java.lang.Long id) {
                return commentaireService.findCommentaire(id);
            }
        };
    }
    
    public Converter<String, Commentaire> ApplicationConversionServiceFactoryBean.getStringToCommentaireConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, get.me.a.tiramisu.entity.Commentaire>() {
            public get.me.a.tiramisu.entity.Commentaire convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Commentaire.class);
            }
        };
    }
    
    public Converter<Long, Lieu> ApplicationConversionServiceFactoryBean.getIdToLieuConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, get.me.a.tiramisu.entity.Lieu>() {
            public get.me.a.tiramisu.entity.Lieu convert(java.lang.Long id) {
                return lieuService.findLieu(id);
            }
        };
    }
    
    public Converter<String, Lieu> ApplicationConversionServiceFactoryBean.getStringToLieuConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, get.me.a.tiramisu.entity.Lieu>() {
            public get.me.a.tiramisu.entity.Lieu convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Lieu.class);
            }
        };
    }
    
    public Converter<Long, Tiramisu> ApplicationConversionServiceFactoryBean.getIdToTiramisuConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, get.me.a.tiramisu.entity.Tiramisu>() {
            public get.me.a.tiramisu.entity.Tiramisu convert(java.lang.Long id) {
                return tiramisuService.findTiramisu(id);
            }
        };
    }
    
    public Converter<String, Tiramisu> ApplicationConversionServiceFactoryBean.getStringToTiramisuConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, get.me.a.tiramisu.entity.Tiramisu>() {
            public get.me.a.tiramisu.entity.Tiramisu convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Tiramisu.class);
            }
        };
    }
    
    public void ApplicationConversionServiceFactoryBean.installLabelConverters(FormatterRegistry registry) {
        registry.addConverter(getRechercheTiramisuDTOToStringConverter());
        registry.addConverter(getIdToRechercheTiramisuDTOConverter());
        registry.addConverter(getStringToRechercheTiramisuDTOConverter());
        registry.addConverter(getCommentaireToStringConverter());
        registry.addConverter(getIdToCommentaireConverter());
        registry.addConverter(getStringToCommentaireConverter());
        registry.addConverter(getLieuToStringConverter());
        registry.addConverter(getIdToLieuConverter());
        registry.addConverter(getStringToLieuConverter());
        registry.addConverter(getTiramisuToStringConverter());
        registry.addConverter(getIdToTiramisuConverter());
        registry.addConverter(getStringToTiramisuConverter());
    }
    
    public void ApplicationConversionServiceFactoryBean.afterPropertiesSet() {
        super.afterPropertiesSet();
        installLabelConverters(getObject());
    }
    
}
