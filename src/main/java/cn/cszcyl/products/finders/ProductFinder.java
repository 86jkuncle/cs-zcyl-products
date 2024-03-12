package cn.cszcyl.products.finders;

import reactor.core.publisher.Flux;
import cn.cszcyl.products.Product;
import cn.cszcyl.products.vo.ProductGroupVo;
import cn.cszcyl.products.vo.ProductVo;

/**
 * A finder for {@link Product}.
 *
 * @author guqing
 * @author ryanwang
 */
public interface ProductFinder {

    Flux<ProductVo> listBy(String group);

    Flux<ProductGroupVo> groupBy();

    Flux<Product> get(String productName);

    Flux<Product> getByGroupName(String groupName);

}
