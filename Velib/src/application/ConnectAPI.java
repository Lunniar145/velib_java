package application;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class ConnectAPI {

    private static final String API_URL =
        "https://opendata.paris.fr/api/records/1.0/search/?dataset=velib-disponibilite-en-temps-reel&rows=1000";

    public static List<Station> getStations() {
        List<Station> stations = new ArrayList<>();

        try {
            URL url = new URL(API_URL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            in.close();

            JSONObject json = new JSONObject(response.toString());
            JSONArray records = json.getJSONArray("records");

            for (int i = 0; i < records.length(); i++) {
                JSONObject fields = records.getJSONObject(i).getJSONObject("fields");

                String numero = fields.optString("stationcode", "0");
                String nom = fields.optString("name", "Inconnu");
                String statut = fields.optString("status", "UNKNOWN");
                boolean paiement = fields.optInt("paymentterminal", 0) == 1;
                int capacite = fields.optInt("capacity", 0);
                int velos = fields.optInt("numbikesavailable", 0);
                int bornes = fields.optInt("numdocksavailable", 0);
                String commune = fields.optString("nom_arrondissement_communes", "Inconnu");

                String arrondissement = "";
                if (numero.length() >= 2 && commune.equalsIgnoreCase("Paris")) {
                    try {
                        int arr = Integer.parseInt(numero.substring(0, 2));
                        if (arr >= 1 && arr <= 20) {
                            arrondissement = String.valueOf(arr); // "1", "2", ..., "20"
                        }
                    } catch (NumberFormatException e) {
                        arrondissement = "";
                    }
                }


                // Département par commune
                String departement;
                switch (commune) {
                    case "Paris": departement = "75"; break;
                    case "Issy-les-Moulineaux":
                    case "Boulogne-Billancourt":
                    case "Neuilly-sur-Seine":
                    case "Levallois-Perret":
                        departement = "92"; break;
                    case "Pantin":
                    case "Montreuil":
                    case "Aubervilliers":
                        departement = "93"; break;
                    case "Vitry-sur-Seine":
                    case "Ivry-sur-Seine":
                    case "Créteil":
                        departement = "94"; break;
                    default: departement = "Autre"; break;
                }

                Station s = new Station(numero, nom, statut, paiement, capacite, velos, bornes, commune, departement, arrondissement);
                if (commune.equalsIgnoreCase("Paris")) {
                    System.out.println("Station Paris : " + nom + " — Arrondissement = " + arrondissement);
                }
                stations.add(s);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return stations;
    }
}
