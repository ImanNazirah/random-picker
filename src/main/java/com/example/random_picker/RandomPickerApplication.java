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
import java.util.Comparator;
import java.util.List;

@SpringBootApplication
public class RandomPickerApplication implements CommandLineRunner {

	private final DataConfig dataConfig;

	public RandomPickerApplication(DataConfig dataConfig) {
		this.dataConfig = dataConfig;
	}

	public static void main(String[] args) {
		SpringApplication.run(RandomPickerApplication.class, args);
	}

	@Override
	public void run(String... args) {

		String spinnerStyle = (args == null || args.length == 0) ? "basic" : args[0].toLowerCase();
		String outputAlignment = (args == null || args.length == 0) ? "horizontal" : args[1].toLowerCase();


		boolean isBasic = switch (spinnerStyle) {
			case "style" -> false;
			case "basic" -> true;
			default -> {
				System.out.println("Unknown spinner style: " + spinnerStyle + ". Defaulting to BASIC.");
				yield true;
			}
		};

		boolean isHorizontal = switch (outputAlignment) {
			case "horizontal" -> true;
			case "vertical" -> false;
			default -> {
				System.out.println("Unknown alignment position: " + outputAlignment + ". Defaulting to HORIZONTAL.");
				yield true;
			}
		};

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
				List<String> shuffled = dataConfig.getSelectionPool();

				Collections.shuffle(shuffled);
				List<String> selected = shuffled.subList(0, count);

				printProgressBar(30, 50, isBasic);
				printOutput(selected,  isHorizontal);

			} catch (NumberFormatException e) {
				System.out.println("Invalid input. Please enter a valid number.");
			} catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
	}


	private void printProgressBar(int totalSteps, int delayMs, boolean isBasic) throws InterruptedException {

		if(isBasic){
			for (int i = 0; i <= totalSteps; i++) {
				int percent = (i * 100) / totalSteps;
				int hashes = (i * 100) / totalSteps;
				String bar = "[" + "#".repeat(hashes) + "-".repeat(100 - hashes) + "]";

				System.out.print("\rPicking items: " + bar + " " + percent + "%");
				Thread.sleep(delayMs);
			}
			System.out.println();
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
			System.out.println();
		}

	}

	private void printOutput(List<String> selectedTexts, boolean isHorizontal){

		if(isHorizontal){
			String[] texts = selectedTexts.toArray(new String[0]);
			String[] uppers = new String[texts.length];
			String[] borders = new String[texts.length];

			for (int i = 0; i < texts.length; i++) {
				uppers[i] = texts[i].toUpperCase();
				borders[i] = "*".repeat(uppers[i].length() + 8);
			}

			// Print top borders
			for (String border : borders) {
				System.out.print(border + "  ");  // Two spaces between boxes
			}
			System.out.println();

			// Print text lines
			for (String upper : uppers) {
				System.out.print("*   " + upper + "   *  ");
			}
			System.out.println();

			// Print bottom borders
			for (String border : borders) {
				System.out.print(border + "  ");
			}
			System.out.println();
		} else{

			// Sort texts by length ascending
			selectedTexts.sort(Comparator.comparingInt(String::length));

			// Convert to uppercase
			List<String> uppers = new ArrayList<>();
			for (String s : selectedTexts) {
				uppers.add(s.toUpperCase());
			}

			// Find max length
			int maxLen = 0;
			for (String s : uppers) {
				if (s.length() > maxLen) maxLen = s.length();
			}

			String border = "*".repeat(maxLen + 8);

			// Print each text with uniform border
			for (String s : uppers) {
				System.out.println(border);
				// Pad right to maxLen
				String padded = String.format("%-" + maxLen + "s", s);
				System.out.println("*   " + padded + "   *");
				System.out.println(border);
				System.out.println();
			}
		}

	}

}
