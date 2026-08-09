package com.example.HomeBooks.recommendation;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class PythonRunner {

    public List<Long> getRecommendedBookIds(
            String email
    ) {

        List<Long> ids = new ArrayList<>();

        try {

            ProcessBuilder processBuilder =
                    new ProcessBuilder(
                            "python",
                            "ml/recommend.py",
                            email
                    );

            processBuilder.redirectErrorStream(true);

            Process process =
                    processBuilder.start();

            BufferedReader reader =
                    new BufferedReader(
                            new InputStreamReader(
                                    process.getInputStream()
                            )
                    );

            String line;

            while ((line = reader.readLine()) != null) {

                line = line.trim();

                // Ignore Python warnings/messages
                if (line.matches("\\d+")) {

                    ids.add(
                            Long.parseLong(line)
                    );
                }
            }

            int exitCode =
                    process.waitFor();

            if (exitCode != 0) {
                System.out.println(
                        "Python exited with code: "
                                + exitCode
                );
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return ids;
    }
}