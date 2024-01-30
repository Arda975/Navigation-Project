import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Testing {
    public static String[] splitLine(String newLine)
    {
        // Split the line by comma
        String[] array = newLine.split(",");
        String stop_id = array[0].trim();
        String stop_name = array[1].trim();
        String arrival_time = array[2].trim();
        String stop_sequence = array[3].trim();
        String direction_id = array[4].trim();
        String route_short_name = array[5].trim();
        String route_long_name = array[6].trim();
        String route_type = array[7].trim();
        return array;
    }

    public static void getWay(DirectedGraph graph)
    {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Origin station: ");
        String str1 = scanner.nextLine();
        System.out.print("Destination station: ");
        String str2 = scanner.nextLine();
        String str3;

        int cost=0;


        while(true)
        {
            System.out.print("Preferetion: ");
            str3 = scanner.nextLine();
            if(str3.equals("minimum time") || str3.equals("fewest stop"))
            {
                if(str3.equals("minimum time")){
                    System.out.println();
                    cost = graph.getCheapestPath(str1,str2,true);
                }
                else{
                    //burada the fewest stop olayı yani shortestPath fonksiyonu çalışıyor
                    System.out.println();
                    cost = graph.getShortestPath(str1,str2);
                }
                break;
            }
            else {
                System.out.println("Please just write *minimum time or *fewest stop");
            }
        }

        System.out.println("\ndo you want to alternative path");
        str3 = scanner.nextLine();
        if(str3.equals("yes"))
        {
            graph.getMoreAffordablePath(str1,str2,cost);
        }

        // you can use this pairs for testing

        //"Avron"
        //"Rome"

        //"Bercy"
        //"Pigalle"

        //"Château de Vincennes"
        //"Mouton-Duvernet"

        //"Charles de Gaulle-Etoile"
        //"Odéon"

        //"Nation"
        //"République"

        //"La Motte-Picquet-Grenelle"
        //"Charles de Gaulle-Etoile"

        //"Château de Vincennes"
        //"La Défense (Grande Arche)"

        //"Rue du Bac"
        //"Rambuteau"

        //"Montparnasse-Bienvenue"
        //"Bastille"

    }


    public static void main(String[] args) throws IOException {

        DirectedGraph graph = new DirectedGraph();

        String file_name = "Paris_RER_Metro_v2.csv";
        try (BufferedReader br = new BufferedReader(new FileReader(file_name))) {

            String currentLine = br.readLine();
            currentLine = br.readLine();

            String oldLine = currentLine;
            String[] oldArray = splitLine(oldLine);

            int count = 1;
            while ((currentLine = br.readLine()) != null) {

                String[] currentArray = splitLine(currentLine);


                /** if direction_id is differance */
                if(!currentArray[4].equals(oldArray[4])) {
                    for (int i = 0; i < count; i++) {
                        currentLine = br.readLine();
                    }
                    count=1;
                    if (currentLine == null)
                        break;
                    else
                        oldArray = splitLine(currentLine);
                }

                else {
                    int currentWeight = Integer. parseInt(currentArray[2]);
                    int oldWeight = Integer.parseInt(oldArray[2]);
                    int differance = Math.abs(currentWeight - oldWeight);

                    graph.addEdge(oldArray[1],currentArray[1],differance, oldArray[5]);
                    graph.addEdge(currentArray[1],oldArray[1],differance, currentArray[5]);

                    oldArray = currentArray;
                    count++;
                }


            }//end while

        } catch (IOException e) {
            e.printStackTrace();
            e.getCause();
        }

        getWay(graph);

    }//end main

}//end class



