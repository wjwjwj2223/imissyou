package com.lin.imissyou.vo;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

public class PagingDozer<T, K> extends Paging {

    @SuppressWarnings("unchecked")
    public PagingDozer(Page<T> page, Class<K> kClass) {
        this.initPageParamters(page);
        List<T> tlist = page.getContent();
        Mapper mapper = DozerBeanMapperBuilder.buildDefault();
        List<K> volist = new ArrayList<>();
        tlist.forEach(s->{
            K vo = mapper.map(s, kClass);
            volist.add(vo);
        });
        this.setItems(volist);
    }
}
