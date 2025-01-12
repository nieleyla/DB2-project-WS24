package de.hsesslingen.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WikipediaParseService {

    private static final String API_URL = "https://en.wikipedia.org/w/index.php?title=List_of_marine_mammal_species&action=raw";
    private static final Pattern PATTERN = Pattern.compile("\\* \\[\\[([^\\]]+?)]](?:, ''([^']+)'' )?\\[.*?\\{\\{IUCN status\\|([A-Z]{2,3})\\|\\d+}}");

    public static class MarineSpecies {

        private final String name;
        private final String scientificName;
        private final String iucnStatus;

        public MarineSpecies(String name, String scientificName, String iucnStatus) {
            this.name = name;
            this.scientificName = scientificName;
            this.iucnStatus = iucnStatus;
        }

        public String getName() {
            return name;
        }

        public String getScientificName() {
            return scientificName;
        }

        public String getIucnStatus() {
            return iucnStatus;
        }

        @Override
        public String toString() {
            return "Name: " + name + ", Scientific Name: " + scientificName + ", IUCN Status: " + iucnStatus;
        }
    }

    private String mapIUCNStatus(String status) {
        switch (status) {
            case "EX":
                return "Extinct";
            case "EW":
                return "Extinct in the Wild";
            case "CR":
                return "Critically Endangered";
            case "EN":
                return "Endangered";
            case "VU":
                return "Vulnerable";
            case "NT":
                return "Near Threatened";
            case "LC":
                return "Least Concern";
            case "DD":
                return "Data Deficient";
            case "NE":
                return "Not Evaluated";
            default:
                return "Unknown";
        }
    }

    public List<MarineSpecies> parseSpeciesFromWikipedia() {
        List<MarineSpecies> speciesList = new ArrayList<>();
        try {
            String wikitext = fetchWikipediaRawContent();
            if (wikitext == null) {
                return speciesList;
            }

            Matcher matcher = PATTERN.matcher(wikitext);
            while (matcher.find()) {
                String name = matcher.group(1);
                String scientificName = matcher.group(2) != null ? matcher.group(2) : "Unknown";
                String iucnStatus = matcher.group(3);
                iucnStatus = mapIUCNStatus(iucnStatus);

                speciesList.add(new MarineSpecies(name, scientificName, iucnStatus));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return speciesList;
    }

    private String fetchWikipediaRawContent() {
        try {
            URL url = new URL(API_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = in.readLine()) != null) {
                response.append(line).append("\n");
            }
            in.close();

            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
