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
    public String convertDSVToJsonL(String inputFilePath) throws IOException {
        // Define output file path
        String outputFilePath = "output.jsonl"; // Modify this as needed

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {

            // Read the DSV file line by line
            String line;
            while ((line = reader.readLine()) != null) {
                // Split the line by delimiter (assuming it's comma-separated)
                String[] parts = line.split(",");

                // Format the date (assuming it's in the first column)
                String formattedDate = formatDate(parts[0]);

                // Construct JSONL string
                String jsonl = constructJsonL(formattedDate, parts);

                // Write JSONL string to output file
                writer.write(jsonl);
                writer.newLine();
            }
        }
        return outputFilePath;
    }

    // Method to format date according to YYYY-MM-dd format
    private String formatDate(String date) {
        // Assuming date format is YYYYMMDD
        String year = date.substring(0, 4);
        String month = date.substring(4, 6);
        String day = date.substring(6, 8);
        return year + "-" + month + "-" + day;
    }

    // Method to construct JSONL string
    private String constructJsonL(String date, String[] data) {
        // Assuming JSONL format is { "date": "YYYY-MM-dd", "data": ["value1", "value2", ...] }
        String jsonData = Stream.of(data)
                .map(value -> "\"" + value + "\"")
                .collect(Collectors.joining(", "));
        return "{ \"date\": \"" + date + "\", \"data\": [" + jsonData + "] }";
    }
}
