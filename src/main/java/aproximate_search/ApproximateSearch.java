package aproximate_search;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.apache.commons.text.similarity.LevenshteinDistance;

public class ApproximateSearch {
	public static void main(String[] args) {
		List<String> stringSet = new ArrayList<>();
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader("output.txt"));
			String name;
			while ((name = bufferedReader.readLine()) != null) {
				stringSet.add(name);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		Scanner scanner = new Scanner(System.in);

		System.out.println("Welcome to Approximate Search!");

		while (true) {
			System.out.print("Enter a search query (or 'q' to quit): ");
			String searchQuery = scanner.nextLine();

			if (searchQuery.equals("q")) {
				break;
			}

			System.out.print("Enter the number of similar strings to retrieve: ");
			int k = Integer.parseInt(scanner.nextLine());

			List<String> topKStrings = approximateSearch(searchQuery, k, stringSet);

			System.out.println("Top " + k + " similar strings to '" + searchQuery + "':");
			for (int i = 0; i < topKStrings.size(); i++) {
				System.out.println((i + 1) + ". " + topKStrings.get(i));
			}
		}
	}

	public static List<String> approximateSearch(String query, int k, List<String> stringSet) {
		LevenshteinDistance levenshteinDistance = LevenshteinDistance.getDefaultInstance();

		Map<String, Integer> similarityScores = new HashMap<>();

		for (String str : stringSet) {
			int similarity = levenshteinDistance.apply(query, str);
			similarityScores.put(str, similarity);
		}

		// Sort the results by similarity scores
		List<Map.Entry<String, Integer>> sortedResults = new ArrayList<>(similarityScores.entrySet());
		sortedResults.sort(Comparator.comparingInt(Map.Entry::getValue));

		List<String> topKStrings = new ArrayList<>();

		for (int i = 0; i < k && i < sortedResults.size(); i++) {
			topKStrings.add(sortedResults.get(i).getKey());
		}

		return topKStrings;
	}

}
