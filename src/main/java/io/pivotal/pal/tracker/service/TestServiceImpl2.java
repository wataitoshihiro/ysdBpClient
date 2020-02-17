package io.pivotal.pal.tracker.service;

import org.springframework.stereotype.Service;

@Service("TestServiceImpl2")
//@Service
public class TestServiceImpl2 implements  TestService{
    @Override
    public String getService() {
        System.out.println("*** TestServiceImpl2 ! ***");
//        throw new IllegalArgumentException("TestService !");
        TestServiceImpl2 ts = null;
        ts.getService();
        return "TestServiceImpl2 !";
    }
}
