package cn.cszcyl.products.vo;

import lombok.Builder;
import lombok.Value;
import run.halo.app.extension.MetadataOperator;
import run.halo.app.theme.finders.vo.ExtensionVoOperator;
import cn.cszcyl.products.Product;

/**
 * @author guqing
 * @since 2.0.0
 */
@Value
@Builder
public class ProductVo implements ExtensionVoOperator {

    MetadataOperator metadata;

    Product.ProductSpec spec;

    public static ProductVo from(Product link) {
        return ProductVo.builder()
            .metadata(link.getMetadata())
            .spec(link.getSpec())
            .build();
    }
}
