package com.kai.home.product.service;

import com.kai.home.product.pojo.PmsSearchParam;
import com.kai.home.product.pojo.PmsSearchSkuInfo;

import java.util.List;

public interface SearchService {

    List<PmsSearchSkuInfo> list(PmsSearchParam pmsSearchParam);
}
