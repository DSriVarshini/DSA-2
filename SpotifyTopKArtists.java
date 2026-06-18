import java.util.*;

class Artist {
    String name;
    int listeners;

    Artist(String name, int listeners) {
        this.name = name;
        this.listeners = listeners;
    }
}

public class SpotifyTopKArtists {
    public static void main(String[] args) {

        int k = 5;

        Artist[] artists = {
            new Artist("Artist1", 45),
            new Artist("Artist2", 12),
            new Artist("Artist3", 78),
            new Artist("Artist4", 23),
            new Artist("Artist5", 56),
            new Artist("Artist6", 89),
            new Artist("Artist7", 34),
            new Artist("Artist8", 67),
            new Artist("Artist9", 18),
            new Artist("Artist10", 91),
            new Artist("Artist11", 50),
            new Artist("Artist12", 39)
        };

        PriorityQueue<Artist> minHeap = new PriorityQueue<>(
                (a, b) -> a.listeners - b.listeners);

        for (Artist artist : artists) {
            if (minHeap.size() < k) {
                minHeap.offer(artist);
            } else if (artist.listeners > minHeap.peek().listeners) {
                minHeap.poll();
                minHeap.offer(artist);
            }
        }

        List<Artist> topArtists = new ArrayList<>(minHeap);

        topArtists.sort((a, b) -> b.listeners - a.listeners);

        System.out.println("Top " + k + " Artists by Monthly Listeners:");

        for (Artist a : topArtists) {
            System.out.println(a.name + " : " + a.listeners + "M");
        }
    }
}
