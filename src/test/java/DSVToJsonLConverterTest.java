import org.example.DSVToJsonLConverter;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class DSVToJsonLConverterTest {

    @Test
    public void testConversion() throws IOException {
        // Input DSV data
        String dsvData = "20220101,value1,value2\n20220102,value3,value4";

        // Expected JSONL output
        String expectedJsonl = "{\"date\": \"2022-01-01\", \"data\": [\"value1\", \"value2\"]}\n" +
                "{\"date\": \"2022-01-02\", \"data\": [\"value3\", \"value4\"]}";

        // Create a temporary file to write DSV data
        String inputFilePath = "src/input/DSVinput1.txt";

        Files.write(Paths.get(inputFilePath), dsvData.getBytes());

        // Call the conversion method
        DSVToJsonLConverter converter = new DSVToJsonLConverter();
        converter.convertDSVToJsonL(inputFilePath);

        // Read the content of the output file
        String actualJsonl = Files.lines(Paths.get("output.jsonl"))
                .collect(Collectors.joining("\n"));

        // Print the expected and actual JSONL outputs for debugging
        System.out.println("Expected JSONL: " + expectedJsonl);
        System.out.println("Actual JSONL: " + actualJsonl);

        // Assert the output matches the expected JSONL output
        assertEquals(expectedJsonl, actualJsonl);

        // Clean up temporary files
        Files.deleteIfExists(Paths.get(inputFilePath));
        Files.deleteIfExists(Paths.get("output.jsonl"));
    }
}
