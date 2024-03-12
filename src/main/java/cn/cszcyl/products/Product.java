package cn.cszcyl.products;

import cn.cszcyl.products.dto.ExtraInfoDTO;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import run.halo.app.extension.AbstractExtension;
import run.halo.app.extension.GVK;
import cn.cszcyl.products.dto.FeatureDescDTO;
import cn.cszcyl.products.dto.FootFeatureDTO;
import cn.cszcyl.products.dto.TechParaDTO;

/**
 * @author guqing
 * @author ryanwang
 * @since 2.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@GVK(group = "cn.cszcyl.products", version = "v1alpha1",
        kind = "Product", plural = "products", singular = "product")
public class Product extends AbstractExtension {

    private ProductSpec spec;

    @Data
    public static class ProductSpec {


        private String qrcode;

        //topimg
        private String topImage;

        //topdesc
        private String topTitle;

        //videotitle
        private String videoTitle;

        //videosubtitle
        private String videoSubTitle;

        //videosrc
        private String videoSrc;

        //specdesctitle
        private String featureTitle;

        //specdesc
        private List<FeatureDescDTO> featureList;

        //jishucanshu
        private List<TechParaDTO> techList;

        //pname
        private String name;

        //footspec
        private FootFeatureDTO footFeature;

        private ExtraInfoDTO extraInfo;


        private String url;


        private String displayName;

        private String logo;

        private String description;

        private Integer priority;

        private String groupName;
    }
}
