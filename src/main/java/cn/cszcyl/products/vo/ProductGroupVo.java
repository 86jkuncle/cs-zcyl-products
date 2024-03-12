package cn.cszcyl.products.vo;

import lombok.Builder;
import lombok.Value;
import lombok.With;
import run.halo.app.extension.MetadataOperator;
import run.halo.app.theme.finders.vo.ExtensionVoOperator;
import cn.cszcyl.products.ProductGroup;

import java.util.List;

/**
 * @author guqing
 * @since 2.0.0
 */
@Value
@Builder
public class ProductGroupVo implements ExtensionVoOperator {

    MetadataOperator metadata;

    ProductGroup.ProductGroupSpec spec;

    @With
    List<ProductVo> links;

    public static ProductGroupVo from(ProductGroup linkGroup) {
        return ProductGroupVo.builder()
            .metadata(linkGroup.getMetadata())
            .spec(linkGroup.getSpec())
            .links(List.of())
            .build();
    }
}
