package software.potthoff.aopmvp.orm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestEntity {
    private String uuid;
    private Integer count;
    private LocalDateTime createdAt;
    private String title;
}
