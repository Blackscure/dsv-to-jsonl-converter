package org.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.stream.Collectors;
import java.util.stream.Stream;
public class DSVToJsonLConverter {

    // Method to convert DSV to JSONL format
    public void convertDSVToJsonL(String inputFilePath) throws IOException {
        // Define output file path
        String outputFilePath = "src/output/JSONLoutput.jsonl";

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {

            // Read the DSV file line by line
            String line;
            while ((line = reader.readLine()) != null) {
                // Split the line by delimiter (assuming it's comma-separated)
                String[] parts = line.split(",");

                // Exclude the date field (first element) from JSONL output
                String[] data = new String[parts.length - 1];
                System.arraycopy(parts, 1, data, 0, parts.length - 1);

                // Construct JSONL string
                String jsonl = constructJsonL(data);

                // Write JSONL string to output file
                writer.write(jsonl);
                writer.newLine();
            }
        }
    }

    // Method to construct JSONL string
    private String constructJsonL(String[] data) {
        // Assuming JSONL format is { "data": ["value1", "value2", ...] }
        String jsonData = Stream.of(data)
                .map(value -> "\"" + value + "\"")
                .collect(Collectors.joining(", "));
        return "{ \"data\": [" + jsonData + "] }";
    }
}