package com.ll.framework.ioc;

import com.ll.domain.testPost.testPost.repository.TestPostRepository;
import com.ll.domain.testPost.testPost.service.TestFacadePostService;
import com.ll.domain.testPost.testPost.service.TestPostService;

import java.util.HashMap;
import java.util.Map;


public class ApplicationContext {

    private Map<String, Object> dependencyMap = new HashMap<>();

    public ApplicationContext() {
        try {
            dependencyMap.put(
                "testPostRepository",
                createUserDao());
            dependencyMap.put(
                "testPostService",
                createTestPostService((TestPostRepository) dependencyMap.get("testPostRepository")));
            dependencyMap.put(
                "testFacadePostService",
                createTestFacadePostService(
                    (TestPostService) dependencyMap.get("testPostService"),
                    (TestPostRepository) dependencyMap.get("testPostRepository")));

        } catch (Exception e) {
            System.out.println("객체 준비 중 오류 발생!");
            e.printStackTrace();
        }
    }


    public <T> T genBean(String beanName) {
        return (T) dependencyMap.get(beanName);
    }

    public TestPostRepository createUserDao() {
        return new TestPostRepository();
    }

    public TestPostService createTestPostService(TestPostRepository testPostRepository) {
        return new TestPostService(testPostRepository);
    }

    public TestFacadePostService createTestFacadePostService(
        TestPostService testPostService,
        TestPostRepository testPostRepository) {
        return new TestFacadePostService(testPostService, testPostRepository);
    }
}
