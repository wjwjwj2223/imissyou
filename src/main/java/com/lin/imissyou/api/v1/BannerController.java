package com.lin.imissyou.api.v1;

import com.lin.imissyou.dto.PersonDTO;
import com.lin.imissyou.exception.NotFoundException;
import com.lin.imissyou.model.Banner;
import com.lin.imissyou.services.BannerService;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("banner")
@Validated
public class BannerController {


    @Autowired
    private BannerService bannerService;

    @GetMapping("/name/{name}")
    @ResponseBody
    public Banner getByName(@PathVariable @NotBlank String name) {
        Banner banner = bannerService.getByName(name);
        if (banner == null) {
            throw new NotFoundException(30005);
        }
        return banner;
    }


    //Demo
    @GetMapping("/test1/{id}")
    public String test1(@PathVariable @Range(min = 1, max = 10, message = "超出范围了") Integer id) {
//        throw new NotFoundException(10001);
        return "test get";
    }

    @PostMapping("/test2/{id}")
    public String test2(@PathVariable Integer id, @Validated @RequestBody PersonDTO person) {
        return "test post";
    }

}
