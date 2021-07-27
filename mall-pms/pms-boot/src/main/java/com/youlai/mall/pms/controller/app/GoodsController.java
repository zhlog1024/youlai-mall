package com.youlai.mall.pms.controller.app;

import com.youlai.common.result.Result;
import com.youlai.mall.pms.pojo.dto.app.SkuDTO;
import com.youlai.mall.pms.pojo.dto.app.SkuLockDTO;
import com.youlai.mall.pms.service.IPmsSkuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "移动端-商品")
@RestController("appGoodsController")
@RequestMapping("/app-api/v1/goods")
@AllArgsConstructor
public class GoodsController {

    private IPmsSkuService iPmsSkuService;

    @ApiOperation(value = "商品详情")
    @ApiImplicitParam(name = "id", value = "商品ID", required = true, paramType = "path", dataType = "Long")
    @GetMapping("/{id}")
    public Result detail(@PathVariable Long id) {
        SkuDTO sku = iPmsSkuService.getSkuById(id);
        return Result.success(sku);
    }

    @ApiOperation("获取商品的库存数量")
    @ApiImplicitParam(name = "id", value = "商品ID", required = true, paramType = "path", dataType = "Long")
    @GetMapping("/{id}/stock")
    public Result<Integer> getStockById(@PathVariable Long id) {
        Integer stock = iPmsSkuService.getStockById(id);
        return Result.success(stock);
    }


    @ApiOperation(value = "锁定库存")
    @ApiImplicitParam(name = "list", value = "商品列表", required = true, paramType = "body", dataType = "SkuLockDTO")
    @PutMapping("/stocks/_lock")
    public Result<Boolean> lockStock(@RequestBody List<SkuLockDTO> list) {
        boolean result = iPmsSkuService.lockStock(list);
        return Result.judge(result);
    }


    @ApiOperation(value = "解锁库存")
    @ApiImplicitParam(name = "orderToken", value = "订单令牌", required = true, paramType = "body", dataType = "String")
    @PutMapping("/stocks/_unlock")
    public Result<Boolean> unlockStock(String orderToken) {
        boolean result = iPmsSkuService.unlockStock(orderToken);
        return Result.judge(result);
    }

    @ApiOperation(value = "扣减库存")
    @ApiImplicitParam(name = "orderToken", value = "订单令牌", required = true, paramType = "body", dataType = "String")
    @PutMapping("/stocks/_deduct")
    public Result<Boolean> deductStock(String orderToken) {
        boolean result = iPmsSkuService.deductStock(orderToken);
        return Result.judge(result);
    }
}
