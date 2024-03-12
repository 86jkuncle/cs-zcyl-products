package cn.cszcyl.products;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.LinkedHashSet;
import lombok.Data;
import lombok.EqualsAndHashCode;
import run.halo.app.extension.AbstractExtension;
import run.halo.app.extension.GVK;

/**
 * @author guqing
 * @author ryanwang
 * @since 2.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@GVK(group = "cn.cszcyl.products", version = "v1alpha1", kind = "ProductGroup", plural = "productgroups", singular = "productgroup")
public class ProductGroup extends AbstractExtension {

    private ProductGroupSpec spec;

    @Data
    public static class ProductGroupSpec {
        @Schema(required = true)
        private String displayName;

        private Integer priority;

        @Deprecated(since = "1.2.0", forRemoval = true)
        @Schema(description = "Names of links below this group.")
        @ArraySchema(arraySchema = @Schema(description = "Links of this group."), schema = @Schema(description = "Name of link."))
        private LinkedHashSet<String> links;
    }
}
