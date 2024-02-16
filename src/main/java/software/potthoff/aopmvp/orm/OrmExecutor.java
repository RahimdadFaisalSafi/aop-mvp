package software.potthoff.aopmvp.orm;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class OrmExecutor {
    public List<Object> getResultList(Class type, List<ParamterDefintion> params) {
        log.info("type={}, params ={}", type, params);
        return null;
    }

    public Object getSinlgeList(Class type, List<ParamterDefintion> params) {
        log.info("type={}, params ={}", type, params);
        return null;
    }
}
