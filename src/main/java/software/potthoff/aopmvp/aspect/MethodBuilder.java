package software.potthoff.aopmvp.aspect;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import software.potthoff.aopmvp.orm.OrmExecutor;
import software.potthoff.aopmvp.orm.ParamterDefintion;
import software.potthoff.aopmvp.orm.TestEntity;

import java.util.ArrayList;
import java.util.List;

@Aspect
@Component
public class MethodBuilder {
    public OrmExecutor ormExecutor;


    public MethodBuilder(){
        this.ormExecutor =  new OrmExecutor();
    }

//    @Autowired
//    public MethodBuilder(OrmExecutor ormExecutor){
//        this.ormExecutor = ormExecutor;
//    }


    @Pointcut("execution(* *(..))")
    public void methodsToBeProfiled() {

    }
    @Around("methodsToBeProfiled() && @annotation(software.potthoff.aopmvp.aspect.MethodBuilderAnnotation)")
    public Object audit(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        Class<?> returnType = determineReturnType(joinPoint);

        List<ParamterDefintion> params = makeParams(methodName);
        System.out.println("Return type " + returnType);

        String query = generateQuery(methodName);
        System.out.println("Query created: " + query);

        // Ren query based on return type
        if (returnType != null && returnType.equals(TestEntity.class)) {
            return ormExecutor.getSinlgeList(returnType, params);
        } else {
            return ormExecutor.getResultList(returnType, params);
        }

    }

    // Return the type of the method that we intercept
    private Class<?> determineReturnType(JoinPoint joinPoint){
        try {
            return ((MethodSignature) joinPoint.getSignature()).getMethod().getReturnType();
        } catch (ClassCastException e) {
            throw new IllegalArgumentException("Method signature not found", e);
        }
    }

    // Construct Query
    private String generateQuery(String methodName){


        StringBuilder queryBuilder = new StringBuilder("SELECT * FROM TestEntity WHERE uuid ");

        switch(methodName) {
            case "findAllByUuidIn":
                    queryBuilder.append("in ?1");
                    break;

            case "findByUuid":
                queryBuilder.append("= ?1");
                break;

            case "findByUuidAndCount":
                queryBuilder.append(" = ?1 AND count = ?2");
                break;

            default:
                throw new IllegalArgumentException("Unsupported method");
        }
        return queryBuilder.toString();
    }

    // Create an object of ParamterDefintion
    public List<ParamterDefintion> makeParams(String methodName) {
        List<ParamterDefintion> params = new ArrayList<>();
        ParamterDefintion param1 = new ParamterDefintion();
        List <Object> li = new ArrayList<>();
        String [] parts = methodName.split("(?=\\p{Upper})");


        for (String part: parts){
            switch (part.toLowerCase()) {
                case "by":
                case "all":
                    param1.setAttribute("*");
                    break;
                case "in":
                    param1.setOperator("in");
                    break;
                case "uuid":
                    li.add("?1");
                    break;
                case "count":
                    li.add("?2");
                    break;
                case "and":
                    param1.setOperator("AND");
                    break;
                default:
                    param1.setOperator("=");
            }
        }
        param1.setCompareValue(li);
        params.add(param1);
        return params;
    }
}
