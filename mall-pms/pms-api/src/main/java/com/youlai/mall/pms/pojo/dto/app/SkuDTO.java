package com.youlai.mall.pms.pojo.dto.app;

import lombok.Data;

/**
 * @author huawei
 * @desc
 * @email huawei_code@163.com
 * @date 2021/1/13
 */
@Data
public class SkuDTO {

    private Long id;
    private String sn;
    private String name;
    private String picUrl;
    private Long price;

    private Integer stock;
    private String goodsName;

}
