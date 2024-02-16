package software.potthoff.aopmvp.orm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParamterDefintion {
    private String attribute;
    private String operator;
    private Object compareValue;
}
