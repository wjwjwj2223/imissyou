package com.lin.imissyou.api.v1;

import com.lin.imissyou.bo.PageCounter;
import com.lin.imissyou.exception.http.NotFoundException;
import com.lin.imissyou.model.Spu;
import com.lin.imissyou.services.SpuService;
import com.lin.imissyou.util.CommonUtil;
import com.lin.imissyou.vo.PagingDozer;
import com.lin.imissyou.vo.SpuSimplityVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;

@RestController
@RequestMapping("spu")
@Validated
public class SpuController {

    @Autowired
    SpuService spuService;

    @GetMapping("/id/{id}/detail")
    @ResponseBody
    public Spu getByName(@PathVariable @Positive(message = "{id.positive}") Long id) {
        Spu spu = spuService.getSpu(id);
        if (spu == null) {
            throw new NotFoundException(30003);
        }
        return spu;
    }

    @GetMapping("/id/{id}/simplity")
    public SpuSimplityVO getSimplitySpu(@PathVariable @Positive(message = "{id.positive}") Long id) {
        Spu spu = spuService.getSpu(id);
        if (spu == null) {
            throw new NotFoundException(30003);
        }
        SpuSimplityVO vo = new SpuSimplityVO();
        BeanUtils.copyProperties(spu, vo);
        return vo;
    }

    @GetMapping("/latest")
    public PagingDozer<Spu, SpuSimplityVO> getLatestSpuList(@RequestParam(defaultValue = "0") Integer start,
                                                @RequestParam(defaultValue = "10") Integer count) {
        PageCounter pageCounter = CommonUtil.convertToPageParameter(start, count);
        Page<Spu> page= spuService.getLatestPagingSpu(pageCounter.getPage(),pageCounter.getCount());
        PagingDozer dozer = new PagingDozer(page, SpuSimplityVO.class);
        return dozer;
    }

    @GetMapping("/by/category/{id}")
    public PagingDozer<Spu, SpuSimplityVO> getByCategoryId(@PathVariable @Positive(message = "{id.positive}") Long id,
                                                           @RequestParam(name = "is_root", defaultValue = "false") Boolean isRoot,
                                                           @RequestParam(name = "start", defaultValue = "0") Integer start,
                                                           @RequestParam(name = "count", defaultValue = "10") Integer count) {
        PageCounter pageCounter = CommonUtil.convertToPageParameter(start, count);
        Page<Spu> page= spuService.getByCategory(id, isRoot, pageCounter.getPage(), pageCounter.getCount());
        PagingDozer dozer = new PagingDozer(page, SpuSimplityVO.class);
        return dozer;
    }


}
