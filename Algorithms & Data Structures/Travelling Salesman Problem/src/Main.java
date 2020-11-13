import java.util.PriorityQueue;

public class Main {
    public static void main(String[] args) {
        FileIO input = new FileIO();
        String[] coordinates = input.load("data/EquipmentGPSCo-ordinates.csv");
        Coordinate[] cities = new Coordinate[coordinates.length];
        for(int i = 0; i < cities.length; i++) {
            String[] coordArray = coordinates[i].split(",");
            cities[i] = new Coordinate(""+i, Double.parseDouble(coordArray[0]), Double.parseDouble(coordArray[1]), new PriorityQueue<>());
        }
        StringBuilder solution = new StringBuilder();

        //Prepare to run the algorithm by 'manually' processing the first node.
        cities[0].setVisited(true);
        solution.append(cities[0].getName()).append(",");

        Coordinate currentCoord = cities[0].getNext(cities);
        currentCoord.setVisited(true);
        solution.append(currentCoord.getName()).append(",");

        for(int i = 0; i < cities.length; i++) {
            currentCoord = currentCoord.getNext(cities);
            if (currentCoord == null){
                if (i < cities.length - 2) { // we subtract 2 because we are iterating over paths only and we do not compute a path back to 0.
                    System.err.println("Retrieved null coordinate before travelling all cities");
                    break;
                } else {
                    System.out.println("Successfully linked all coordinates");
                    break;
                }
            }
            currentCoord.setVisited(true);
            solution.append(currentCoord.getName()).append(",");
        }
        //Return back home once we visited every node.
        solution.append("0");
        System.out.println(solution);
    }
}

class Coordinate {
    private String name;
    private double lat;
    private double lon;
    private boolean visited;
    public PriorityQueue<Edge> paths;

    public Coordinate(String name, double lat, double lon, PriorityQueue<Edge> paths){
        this.name = name;
        this.lat = lat;
        this.lon = lon;
        this.paths = paths;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public String getName() {
        return name;
    }

    public Coordinate getNext(Coordinate[] coords){
        for (Coordinate coord : coords) {
            if(!coord.isVisited())
                paths.add(new Edge(coord, haversine(this.lat, this.lon, coord.getLat(), coord.getLon())));
        }
        for(Edge p : paths){
            //Due to the paths being a PriorityQueue, it is guaranteed that we will get
            //the best option first if we iterate through the queue.
            if(!(p.getTarget().isVisited()) && p.getWeight() > 100d) {
                return p.getTarget();
            }
        }
        return null;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public boolean isVisited() {
        return visited;
    }

    /*
    Haversine implementation - https://gist.github.com/vananth22/888ed9a22105670e7a4092bdcf0d72e4
     */
    public static double haversine(double lat1, double lon1, double lat2, double lon2) {
        final double R = 6371; // Radius of the earth
        double latDistance = toRad(lat2 - lat1);
        double lonDistance = toRad(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
                Math.cos(toRad(lat1)) * Math.cos(toRad(lat2)) *
                        Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }
    private static Double toRad(Double value) {
        return value * Math.PI / 180;
    }
}

//We implement Comparable so we can have our possible paths auto-sorted as we insert them into a PriorityQueue.
//This will allow us to get the shortest distance we can possibly travel each time.
class Edge implements Comparable<Edge>{
    private double weight;
    private Coordinate target;

    public Edge(Coordinate target, double weight){
        this.target = target;
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }

    public Coordinate getTarget() {
        return target;
    }

    @Override
    public int compareTo(Edge o) {
        return Double.compare(this.weight, o.getWeight());
    }
}