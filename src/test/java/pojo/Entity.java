package pojo;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Data
@Builder(builderMethodName = "hiddenBuilder", toBuilder = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor @NoArgsConstructor
public class Entity {
    private static final Faker faker = new Faker();

    private int id;

    private Addition addition;

    @JsonProperty("important_numbers")
    private List<Integer> importantNumbers;

    private String title;

    private boolean verified = true;

    public static EntityBuilder builder() {
        return hiddenBuilder()
                .addition(Addition.builder().build())
                .importantNumbers(generateRandomNumbers())
                .title(generateRandomTitle())
                .verified(faker.bool().bool());
    }

    private static List<Integer> generateRandomNumbers() {
        int count = faker.number().numberBetween(1, 6);
        return IntStream.range(0, count)
                .map(i -> faker.number().numberBetween(1, 101))
                .boxed()
                .collect(Collectors.toList());
    }

    private static String generateRandomTitle() {
        return faker.lorem().characters(10, 20) + " " +
                faker.number().numberBetween(1000, 10000);
    }


    @Data
    @Builder(builderMethodName = "hiddenAdditionBuilder", toBuilder = true)
    @AllArgsConstructor @NoArgsConstructor
    public static class Addition {
        private int id;

        @JsonProperty("additional_info")
        private String additionalInfo;

        @JsonProperty("additional_number")
        private int additionalNumber;

        public static AdditionBuilder builder() {
            return hiddenAdditionBuilder()
                    .additionalInfo(faker.lorem().sentence(3))
                    .additionalNumber(faker.number().numberBetween(1, 1001));
        }
    }
}
