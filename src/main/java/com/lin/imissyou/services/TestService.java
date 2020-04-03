package com.lin.imissyou.services;

import com.lin.imissyou.core.configuration.TestConfiguration;
import com.lin.imissyou.model.TestModel;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestService {

    @Autowired
    private ObjectFactory<TestModel> testModelObjectFactory;

    public TestModel testService() {
        String name1 = "1";
        String name2 = "2";
        TestModel testModel = testModelObjectFactory.getObject();
        return testModel;
    }

}
