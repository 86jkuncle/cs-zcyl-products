package cn.cszcyl.products;

import org.pf4j.PluginWrapper;
import org.springframework.stereotype.Component;
import run.halo.app.extension.SchemeManager;
import run.halo.app.plugin.BasePlugin;

/**
 * @author guqing
 * @since 2.0.0
 */
@Component
public class ProductPlugin extends BasePlugin {

    private final SchemeManager schemeManager;

    public ProductPlugin(PluginWrapper wrapper, SchemeManager schemeManager) {
        super(wrapper);
        this.schemeManager = schemeManager;
    }

    @Override
    public void start() {
        schemeManager.register(Product.class);
        schemeManager.register(ProductGroup.class);
    }

    @Override
    public void stop() {
        schemeManager.unregister(schemeManager.get(Product.class));
        schemeManager.unregister(schemeManager.get(ProductGroup.class));
    }
}
