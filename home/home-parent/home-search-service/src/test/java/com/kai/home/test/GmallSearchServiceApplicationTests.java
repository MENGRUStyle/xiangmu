package com.kai.home.test;


import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import com.kai.home.product.pojo.PmsSearchSkuInfo;
import com.kai.home.product.pojo.PmsSkuInfo;
import com.kai.home.product.service.PmsSkuInfoService;
import io.searchbox.client.JestClient;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableDubbo
public class GmallSearchServiceApplicationTests {


    @Reference
    PmsSkuInfoService  pmsSkuInfoService;// 查询mysql

    @Autowired
    JestClient jestClient;

    @Test
    public void contextLoads() throws IOException {
        //get();
        put();
    }

    public void put() throws IOException {

        // 查询mysql数据
        List<PmsSkuInfo> pmsSkuInfoList = new ArrayList<>();

        pmsSkuInfoList = pmsSkuInfoService.getAllSku();

        System.out.println(pmsSkuInfoList);

        // 转化为es的数据结构
        List<PmsSearchSkuInfo> pmsSearchSkuInfos = new ArrayList<>();

        for (PmsSkuInfo pmsSkuInfo : pmsSkuInfoList) {
            PmsSearchSkuInfo pmsSearchSkuInfo = new PmsSearchSkuInfo();

            //BeanUtils.copyProperties(pmsSkuInfo,pmsSearchSkuInfo);

            pmsSearchSkuInfo.setId(pmsSkuInfo.getId());
            pmsSearchSkuInfo.setSkuName(pmsSkuInfo.getSkuName());
            //skuName
            pmsSearchSkuInfo.setSkuDesc(pmsSkuInfo.getSkuDesc());
            //skuDesc
            pmsSearchSkuInfo.setCatalog3Id(pmsSkuInfo.getCatalog3Id());
            //catalog3Id
            pmsSearchSkuInfo.setPrice(new BigDecimal(pmsSkuInfo.getPrice()));
            //price
            pmsSearchSkuInfo.setSkuDefaultImg(pmsSkuInfo.getSkuDefaultImg());
            //skuDefaultImg
            //hotScore
            pmsSearchSkuInfo.setProductId(pmsSkuInfo.getSpuId());
            //productId
            pmsSearchSkuInfo.setSkuAttrValueList(pmsSkuInfo.getSkuAttrValueList());


            pmsSearchSkuInfos.add(pmsSearchSkuInfo);
        }
        // 导入es
        for (PmsSearchSkuInfo pmsSearchSkuInfo : pmsSearchSkuInfos) {
            Index put = new Index.Builder(pmsSearchSkuInfo).index("gmall20200607").type("PmsSkuInfo").id(pmsSearchSkuInfo.getId()+"").build();
            System.out.println("jestClient.execute="+pmsSearchSkuInfo.getSkuName());
            jestClient.execute(put);
        }
    }

    public void get() throws IOException {

        // jest的dsl工具
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // bool
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        // filter
        TermQueryBuilder termQueryBuilder = new TermQueryBuilder("skuAttrValueList.valueId","43");
        boolQueryBuilder.filter(termQueryBuilder);
        // must
        MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("skuName","华为");
        boolQueryBuilder.must(matchQueryBuilder);
        // query
        searchSourceBuilder.query(boolQueryBuilder);
        // from
        searchSourceBuilder.from(0);
        // size
        searchSourceBuilder.size(20);
        // highlight
        searchSourceBuilder.highlight();

        String dslStr = searchSourceBuilder.toString();

        System.err.println(dslStr);


        // 用api执行复杂查询
        List<PmsSearchSkuInfo> pmsSearchSkuInfos = new ArrayList<>();

        Search search = new Search.Builder(dslStr).addIndex("gmall20200310").addType("PmsSkuInfo").build();

        SearchResult execute = jestClient.execute(search);

        List<SearchResult.Hit<PmsSearchSkuInfo, Void>> hits = execute.getHits(PmsSearchSkuInfo.class);

        for (SearchResult.Hit<PmsSearchSkuInfo, Void> hit : hits) {
            PmsSearchSkuInfo source = hit.source;
            pmsSearchSkuInfos.add(source);
        }

        System.out.println(pmsSearchSkuInfos.size());
    }



}
