package com.youlai.mall.pms.controller.admin;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.common.result.Result;
import com.youlai.mall.pms.pojo.dto.admin.GoodsFormDTO;
import com.youlai.mall.pms.pojo.entity.PmsSpu;
import com.youlai.mall.pms.pojo.vo.admin.GoodsDetailVO;
import com.youlai.mall.pms.service.IPmsSpuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:xianrui0365@163.com">xianrui</a>
 */
@Api(tags = "系统管理端-商品信息")
@RestController
@RequestMapping("/api/v1/goods")
@AllArgsConstructor
public class GoodsController {

    private IPmsSpuService iPmsSpuService;

    @ApiOperation(value = "商品分页列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "limit", value = "每页数量", paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "categoryId", value = "分类ID", paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "name", value = "商品名称", paramType = "query", dataType = "String")

    })
    @GetMapping("/page")
    public Result list(Integer page, Integer limit, String name, Long categoryId) {
        IPage<PmsSpu> result = iPmsSpuService.list(new Page<>(page, limit), name, categoryId);
        return Result.success(result.getRecords(), result.getTotal());
    }

    @ApiOperation(value = "商品详情")
    @ApiImplicitParam(name = "id", value = "商品id", required = true, paramType = "path", dataType = "Long")
    @GetMapping("/{id}")
    public Result detail(@PathVariable Long id) {
        GoodsDetailVO goodsDetail = iPmsSpuService.getGoodsById(id);
        return Result.success(goodsDetail);
    }

    @ApiOperation(value = "新增商品")
    @ApiImplicitParam(name = "goodsForm", value = "实体JSON对象", required = true, paramType = "body", dataType = "GoodsFormDTO")
    @PostMapping
    public Result add(@RequestBody GoodsFormDTO goodsForm) {
        boolean result = iPmsSpuService.addGoods(goodsForm);
        return Result.judge(result);
    }

    @ApiOperation(value = "修改商品")
    @ApiImplicitParam(name = "id", value = "商品id", required = true, paramType = "path", dataType = "Long")
    @PutMapping(value = "/{id}")
    public Result update(@PathVariable Long id, @RequestBody GoodsFormDTO goods) {
        boolean result = iPmsSpuService.updateGoods(goods);
        return Result.judge(result);
    }

    @ApiOperation(value = "删除商品")
    @ApiImplicitParam(name = "ids", value = "id集合,以英文逗号','分隔", required = true, paramType = "query", dataType = "String")
    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable String ids) {
        boolean result = iPmsSpuService.removeByGoodsIds(Arrays.asList(ids.split(",")).stream().map(id -> Long.parseLong(id)).collect(Collectors.toList()));
        return Result.judge(result);
    }

    @ApiOperation(value = "选择性修改商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "商品ID", required = true, paramType = "path", dataType = "Long"),
            @ApiImplicitParam(name = "spu", value = "实体JSON对象", required = true, paramType = "body", dataType = "PmsSpu")
    })
    @PatchMapping(value = "/{id}")
    public Result patch(@PathVariable Integer id, @RequestBody PmsSpu spu) {
        LambdaUpdateWrapper<PmsSpu> updateWrapper = new LambdaUpdateWrapper<PmsSpu>().eq(PmsSpu::getId, id);
        updateWrapper.set(spu.getStatus() != null, PmsSpu::getStatus, spu.getStatus());
        boolean update = iPmsSpuService.update(updateWrapper);
        return Result.success(update);
    }
}
