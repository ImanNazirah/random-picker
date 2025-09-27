package com.example.random_picker;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SpringBootApplication
public class RandomPickerApplication implements CommandLineRunner {

	private static final List<String> SELECTION_POOL = List.of(
			"Apple", "Banana", "Orange", "Grapes", "Mango", "Peach", "Strawberry", "Pineapple"
	);

	public static void main(String[] args) {
		SpringApplication.run(RandomPickerApplication.class, args);
	}

	@Override
	public void run(String... args) {

		ClassPathResource resource = new ClassPathResource("title.txt");

		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
			String line;
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}
		} catch (IOException e) {
            throw new RuntimeException(e);
        }

        Console console = System.console();

		if (console == null) {
			System.out.println("No console available. Exiting.");
			return;
		}

		while (true) {
			String input = console.readLine("Enter number of items to pick (or 'q' to quit): ");
			if (input == null) {
				System.out.println("No input detected. Exiting...");
				break;
			}

			input = input.trim();

			if ("q".equalsIgnoreCase(input)) {
				System.out.println("Exiting...");
				break;
			}

			try {
				int count = Integer.parseInt(input);
				List<String> shuffled = new ArrayList<>(SELECTION_POOL);
				Collections.shuffle(shuffled);
				List<String> selected = shuffled.subList(0, count);

				printProgressBar(30, 50, false); // 30 steps with 50ms delay = ~1.5s total

				System.out.println(" Selected items:");
				selected.forEach(item -> System.out.println(" - " + item));
			} catch (NumberFormatException e) {
				System.out.println("Invalid input. Please enter a valid number.");
			} catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
	}


	private void printProgressBar(int totalSteps, int delayMs, boolean plain) throws InterruptedException {

		if(plain){
			for (int i = 0; i <= totalSteps; i++) {
				int percent = (i * 100) / totalSteps;
				int hashes = (i * 100) / totalSteps;
				String bar = "[" + "#".repeat(hashes) + "-".repeat(100 - hashes) + "]";

				System.out.print("\rPicking items: " + bar + " " + percent + "%");
				Thread.sleep(delayMs);
			}
			System.out.println(); // Move to next line after complete
		} else{
			int barLength = 120;
			char[] spinner = {'|', '/', '-', '\\'};
			for (int i = 0; i <= totalSteps; i++) {
				int percent = (i * 100) / totalSteps;
				int blocks = (i * barLength) / totalSteps;
				String bar = "[" + "█".repeat(blocks) + "-".repeat(barLength - blocks) + "]";
				char spinChar = spinner[i % spinner.length];
				System.out.print("\rPicking items: " + bar + " " + percent + "% " + spinChar);
				Thread.sleep(delayMs);
			}
			System.out.println(); // Move to next line after complete
		}

	}


}
