package com.example.HomeBooks.recommendation;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class PythonRunner {

    public List<Long> getRecommendedBookIds(Long bookId) {

        List<Long> ids = new ArrayList<>();

        try {

            ProcessBuilder pb = new ProcessBuilder(
                    "python",
                    "ml/recommend.py",
                    bookId.toString()
            );

            pb.redirectErrorStream(true);

            Process process = pb.start();

            BufferedReader reader =
                    new BufferedReader(
                            new InputStreamReader(
                                    process.getInputStream()
                            )
                    );

            String line;

            while ((line = reader.readLine()) != null) {

                ids.add(
                        Long.parseLong(line)
                );

            }

            process.waitFor();

        }
        catch (Exception e) {

            e.printStackTrace();

        }

        return ids;
    }

}