package software.potthoff.aopmvp.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import software.potthoff.aopmvp.orm.OrmHelperService;
import software.potthoff.aopmvp.orm.TestEntity;
import software.potthoff.aopmvp.orm.TestEntityRepository;
import software.potthoff.aopmvp.service.TestService;

@RestController
@Slf4j
public class TestController {
    @Autowired
    TestService testService;
    @Autowired
    OrmHelperService ormHelperService;
    @Autowired
    TestEntityRepository testEntityRepository;


    @GetMapping("/test")
    public void test() {
        String myString = "234";
        log.info("Fields for Class: {}", ormHelperService.getFieldsForClass(TestEntity.class));
        testEntityRepository.findByUuid(myString);
        testService.someLongRunningMethod(3000);
    }
}
