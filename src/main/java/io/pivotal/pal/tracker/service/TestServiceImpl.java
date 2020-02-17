package io.pivotal.pal.tracker.service;

import org.springframework.stereotype.Service;

@Service("TestServiceImpl1")
//@Service
public class TestServiceImpl implements  TestService{
    @Override
    public String getService() {
        System.out.println("*** TestServiceImpl1 ! ***");
        return "TestServiceImpl1 !";
    }
}
