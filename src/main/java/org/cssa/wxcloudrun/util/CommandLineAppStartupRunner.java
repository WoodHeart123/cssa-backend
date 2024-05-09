package org.cssa.wxcloudrun.util;

import org.cssa.wxcloudrun.dao.CourseMapper;
import org.cssa.wxcloudrun.dao.RentalMapper;
import org.cssa.wxcloudrun.dao.SecondhandMapper;
import org.cssa.wxcloudrun.model.Course;
import org.cssa.wxcloudrun.model.Product;
import org.cssa.wxcloudrun.repo.IProductEsRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.suggest.Completion;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPooled;
import redis.clients.jedis.search.IndexDefinition;
import redis.clients.jedis.search.IndexOptions;
import redis.clients.jedis.search.RediSearchUtil;
import redis.clients.jedis.search.Schema;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(CommandLineAppStartupRunner.class);

    @Autowired
    private IProductEsRepo productEsRepo;

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;


    @Autowired
    SecondhandMapper secondhandMapper;


    @Override
    public void run(String... args) {
        if(elasticsearchOperations.indexOps(Product.class).exists()) {
            elasticsearchOperations.indexOps(Product.class).delete();
        }
        elasticsearchOperations.indexOps(Product.class).create();
        productEsRepo.deleteAll();
        if(productEsRepo.count() == 0) {
            int offset = 0;
            logger.info("Start to load product data to ES.");
            ArrayList<Product> productArrayList;
            productArrayList = secondhandMapper.getProductList(offset, 100);
            while(!productArrayList.isEmpty()) {
                productArrayList.forEach(product -> {
                    product.setSuggest(new Completion(new String[]{product.getProductTitle()}));
                });
                productEsRepo.saveAll(productArrayList);
                offset += 100;
                productArrayList = secondhandMapper.getProductList(offset, 100);
            }
        }
    }
}