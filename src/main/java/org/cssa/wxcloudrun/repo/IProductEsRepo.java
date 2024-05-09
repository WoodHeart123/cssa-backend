package org.cssa.wxcloudrun.repo;

import org.cssa.wxcloudrun.model.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductEsRepo extends ElasticsearchRepository<Product, Long> {

}
