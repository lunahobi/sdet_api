package pojo;
import java.util.List;
import com.google.gson.annotations.SerializedName;
import lombok.*;

@Data
@Builder
@AllArgsConstructor @NoArgsConstructor
public class Entity {
    private int id;

    @Builder.Default
    private Addition addition = Addition.builder().build();

    @SerializedName("important_numbers")
    @Builder.Default
    private List<Integer> importantNumbers = List.of(42, 87, 15);

    @Builder.Default
    private String title = "Заголовок сущности";

    @Builder.Default
    private boolean verified = true;

    @Data
    @Builder
    public static class Addition {
        private int id;

        @SerializedName("additional_info")
        @Builder.Default
        private String additionalInfo = "Дополнительные сведения";

        @SerializedName("additional_number")
        @Builder.Default
        private int additionalNumber = 123;
    }
}
