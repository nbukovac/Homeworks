package hr.fer.zemris.web.servlet.voting.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import hr.fer.zemris.web.servlet.definitions.BandInfo;
import hr.fer.zemris.web.servlet.definitions.ResultsInfo;
import hr.fer.zemris.web.servlet.definitions.VotingInfo;

/**
 * Utility class that contains methods used for reading file contents and
 * creating the appropriate collections of classes.
 * 
 * @author Nikola Bukovac
 * @version 1.0
 */
public class Utility {

	/**
	 * Creates a list of {@link ResultsInfo} from the combination of
	 * {@link VotingInfo} and {@link BandInfo} classes.
	 * 
	 * @param bandFile
	 *            path to the file containing band information
	 * @param resultFile
	 *            path to the file containing voting information
	 * @return list of {@link ResultsInfo}
	 * @throws IOException
	 *             if anything happens that is specified in the
	 *             {@link IOException} documentation
	 */
	public static List<ResultsInfo> createResults(final String bandFile, final String resultFile) throws IOException {
		final Map<Integer, VotingInfo> votingResults = Utility.extractVotingInfo(bandFile);
		final List<BandInfo> bands = Utility.extractBandInfo(resultFile);
		final List<ResultsInfo> results = new ArrayList<>();

		for (final BandInfo band : bands) {
			results.add(new ResultsInfo(band.getBandName(), votingResults.get(band.getId()).getVotes(), band.getUrl()));
		}
		return results;
	}

	/**
	 * Creates a list of {@link BandInfo} from the specified file.
	 * 
	 * @param fileName
	 *            path to the file containing band information
	 * @return list of {@link BandInfo}
	 * @throws IOException
	 *             if anything happens that is specified in the
	 *             {@link IOException} documentation
	 */
	public static List<BandInfo> extractBandInfo(final String fileName) throws IOException {
		final BufferedReader reader = new BufferedReader(new FileReader(fileName));
		String line = "";
		final List<BandInfo> bands = new ArrayList<>();

		while (true) {
			line = reader.readLine();

			if (line == null) {
				break;
			}

			line = line.trim();

			if (line.isEmpty()) {
				break;
			}

			final String[] info = line.split("\\t");

			if (info.length != 3) {
				continue;
			}

			int id = 0;
			try {
				id = Integer.parseInt(info[0]);
			} catch (final NumberFormatException e) {
				System.err.println("Id isn't an integer");
				continue;
			}

			bands.add(new BandInfo(id, info[1], info[2]));
		}

		reader.close();

		return bands;
	}

	/**
	 * Creates a map of {@link VotingInfo} where the key is the unique
	 * identifier of the {@link VotingInfo} object. Map is created from the
	 * specified file.
	 * 
	 * @param fileName
	 *            path to the file containing voting information
	 * @return map containing {@link VotingInfo}
	 * @throws IOException
	 *             if anything happens that is specified in the
	 *             {@link IOException} documentation
	 */
	public static Map<Integer, VotingInfo> extractVotingInfo(final String fileName) throws IOException {
		final BufferedReader reader = new BufferedReader(new FileReader(fileName));
		String line = "";
		final Map<Integer, VotingInfo> votes = new TreeMap<>();

		while (true) {
			line = reader.readLine();

			if (line == null) {
				break;
			}

			line = line.trim();

			if (line.isEmpty()) {
				break;
			}

			final String[] info = line.split("\\t");

			if (info.length != 2) {
				continue;
			}

			int id = 0;
			int numberOfVotes = 0;
			try {
				id = Integer.parseInt(info[0]);
				numberOfVotes = Integer.parseInt(info[1]);
			} catch (final NumberFormatException e) {
				System.err.println("Id or number of votes isn't an integer");
				continue;
			}

			votes.put(id, new VotingInfo(id, numberOfVotes));
		}

		reader.close();

		return votes;
	}
}
