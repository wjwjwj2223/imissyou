package com.lin.imissyou.api.v1;

import com.lin.imissyou.model.TestModel;
import com.lin.imissyou.services.TestService;
import org.omg.CORBA.Object;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class TestController {

//    @Autowired TestService testService;

//    @GetMapping("/test")
//    public TestModel test() {
//        return testService.testService();
//    }

}
