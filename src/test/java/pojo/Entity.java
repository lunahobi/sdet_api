package pojo;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@AllArgsConstructor @NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Entity {
    private int id;

    @Builder.Default
    private Addition addition = Addition.builder().build();

    @JsonProperty("important_numbers")
    @Builder.Default
    private List<Integer> importantNumbers = List.of(42, 87, 15);

    @Builder.Default
    private String title = "Заголовок сущности";

    @Builder.Default
    private boolean verified = true;

    @Data
    @Builder
    @AllArgsConstructor @NoArgsConstructor
    public static class Addition {
        private int id;

        @JsonProperty("additional_info")
        @Builder.Default
        private String additionalInfo = "Дополнительные сведения";

        @JsonProperty("additional_number")
        @Builder.Default
        private int additionalNumber = 123;
    }
}
