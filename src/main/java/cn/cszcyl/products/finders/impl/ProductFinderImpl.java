package cn.cszcyl.products.finders.impl;

import java.time.Instant;
import java.util.Comparator;
import java.util.function.Function;
import java.util.function.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.Nullable;
import org.springframework.util.comparator.Comparators;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import run.halo.app.extension.Metadata;
import run.halo.app.extension.ReactiveExtensionClient;
import run.halo.app.theme.finders.Finder;
import cn.cszcyl.products.Product;
import cn.cszcyl.products.ProductGroup;
import cn.cszcyl.products.finders.ProductFinder;
import cn.cszcyl.products.vo.ProductGroupVo;
import cn.cszcyl.products.vo.ProductVo;

/**
 * A default implementation for {@link ProductFinder}.
 *
 * @author guqing
 * @author ryanwang
 */
@Finder("productFinder")
public class ProductFinderImpl implements ProductFinder {
    private final ReactiveExtensionClient client;

    public ProductFinderImpl(ReactiveExtensionClient client) {
        this.client = client;
    }

    @Override
    public Flux<ProductVo> listBy(String groupName) {
        return listAll(link -> StringUtils.equals(link.getSpec().getGroupName(), groupName)
            && link.getMetadata().getDeletionTimestamp() != null)
            .map(ProductVo::from);
    }

    @Override
    public Flux<ProductGroupVo> groupBy() {
        Flux<Product> linkFlux = listAll(null);
        return listAllGroups()
            .concatMap(group -> linkFlux
                .filter(link -> StringUtils.equals(link.getSpec().getGroupName(),
                    group.getMetadata().getName())
                )
                .map(ProductVo::from)
                .collectList()
                .map(group::withLinks)
                .defaultIfEmpty(group)
            )
            .mergeWith(Mono.defer(() -> ungrouped()
                .map(ProductGroupVo::from)
                .flatMap(linkGroup -> linkFlux.filter(
                        link -> StringUtils.isBlank(link.getSpec().getGroupName()))
                    .map(ProductVo::from)
                    .collectList()
                    .map(linkGroup::withLinks)
                    .defaultIfEmpty(linkGroup)
                ))
            );
    }

    @Override
    public Flux<Product> get(String productName) {
        return listAll(link -> StringUtils.equals(link.getMetadata().getName(), productName))
            .flatMap(Flux::just);
//        return listAllMono(productName);
    }

    @Override
    public Flux<Product> getByGroupName(String groupName) {
        return listAll(link -> StringUtils.equals(link.getSpec().getGroupName(), groupName))
            .flatMap(Flux::just);
    }


    private Mono<ProductGroupVo> getLinkGroupVo(@Nullable ProductGroup linkGroup) {
        ProductGroupVo linkGroupVo = ProductGroupVo.from(linkGroup);

        return null;
    }

    Mono<ProductGroup> ungrouped() {
        ProductGroup linkGroup = new ProductGroup();
        linkGroup.setMetadata(new Metadata());
        linkGroup.getMetadata().setName("ungrouped");
        linkGroup.setSpec(new ProductGroup.ProductGroupSpec());
        linkGroup.getSpec().setDisplayName("");
        linkGroup.getSpec().setPriority(0);
        return Mono.just(linkGroup);
    }

    Flux<Product> listAll(@Nullable Predicate<Product> predicate) {
        return client.list(Product.class, predicate, defaultLinkComparator());
    }

    Mono<Product> listAllMono(String productName){
        return client.get(Product.class, productName);
    }

    Flux<ProductGroupVo> listAllGroups() {
        return client.list(ProductGroup.class, null, defaultGroupComparator())
            .map(ProductGroupVo::from);
    }

    static Comparator<ProductGroup> defaultGroupComparator() {
        Function<ProductGroup, Integer> priority = group -> group.getSpec().getPriority();
        Function<ProductGroup, Instant> createTime =
            group -> group.getMetadata().getCreationTimestamp();
        Function<ProductGroup, String> name = group -> group.getMetadata().getName();
        return Comparator.comparing(priority, Comparators.nullsLow())
            .thenComparing(createTime)
            .thenComparing(name);
    }

    static Comparator<Product> defaultLinkComparator() {
        Function<Product, Integer> priority = link -> link.getSpec().getPriority();
        Function<Product, Instant> createTime = link -> link.getMetadata().getCreationTimestamp();
        Function<Product, String> name = link -> link.getMetadata().getName();
        return Comparator.comparing(priority, Comparators.nullsLow())
            .thenComparing(createTime)
            .thenComparing(name);
    }
}
