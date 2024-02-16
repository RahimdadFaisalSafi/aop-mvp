package software.potthoff.aopmvp.orm;

import org.springframework.stereotype.Component;
import software.potthoff.aopmvp.aspect.MethodBuilderAnnotation;

import java.util.List;

@Component
public class TestEntityRepository {
    @MethodBuilderAnnotation
    public List<TestEntity> findAllByUuidIn(List<String> uuids) {
        return null;
    }
    @MethodBuilderAnnotation
    public TestEntity findByUuid(String uuid) {
        return null;
    }
    @MethodBuilderAnnotation
    public List<TestEntity> findByUuidAndCount(String uuid, Integer count) {
        return null;
    }
}
