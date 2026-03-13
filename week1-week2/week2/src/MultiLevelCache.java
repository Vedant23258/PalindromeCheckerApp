import java.util.*;

public class MultiLevelCache {

    static class VideoData {
        String videoId;
        String content;

        VideoData(String videoId, String content) {
            this.videoId = videoId;
            this.content = content;
        }
    }

    private LinkedHashMap<String, VideoData> l1Cache;
    private final int L1_CAPACITY = 10000;

    private HashMap<String, VideoData> l2Cache;
    private final int L2_CAPACITY = 100000;
    private HashMap<String, Integer> l2AccessCount;

    private HashMap<String, VideoData> database;

    private int l1Hits = 0, l2Hits = 0, l3Hits = 0, totalRequests = 0;
    private double l1Time = 0, l2Time = 0, l3Time = 0;

    private final int PROMOTION_THRESHOLD = 3;

    public MultiLevelCache() {

        l1Cache = new LinkedHashMap<>(L1_CAPACITY, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<String, VideoData> eldest) {
                return size() > L1_CAPACITY;
            }
        };

        l2Cache = new HashMap<>();
        l2AccessCount = new HashMap<>();

        database = new HashMap<>();
    }

    public void addVideoToDB(String videoId, String content) {
        database.put(videoId, new VideoData(videoId, content));
    }

    public VideoData getVideo(String videoId) {
        totalRequests++;

        if (l1Cache.containsKey(videoId)) {
            l1Hits++;
            l1Time += 0.5;
            return l1Cache.get(videoId);
        }

        if (l2Cache.containsKey(videoId)) {
            l2Hits++;
            l2Time += 5;

            int count = l2AccessCount.getOrDefault(videoId, 0) + 1;
            l2AccessCount.put(videoId, count);

            if (count >= PROMOTION_THRESHOLD) {
                l1Cache.put(videoId, l2Cache.get(videoId));
            }

            return l2Cache.get(videoId);
        }

        if (database.containsKey(videoId)) {
            l3Hits++;
            l3Time += 150;

            VideoData video = database.get(videoId);

            if (l2Cache.size() >= L2_CAPACITY) {
                String toRemove = l2Cache.keySet().iterator().next();
                l2Cache.remove(toRemove);
                l2AccessCount.remove(toRemove);
            }

            l2Cache.put(videoId, video);
            l2AccessCount.put(videoId, 1);

            return video;
        }

        return null;
    }

    public void getStatistics() {

        double l1HitRate = totalRequests == 0 ? 0 : (l1Hits * 100.0 / totalRequests);
        double l2HitRate = totalRequests == 0 ? 0 : (l2Hits * 100.0 / totalRequests);
        double l3HitRate = totalRequests == 0 ? 0 : (l3Hits * 100.0 / totalRequests);

        double overallTime = (l1Time + l2Time + l3Time) / totalRequests;
        System.out.printf("L1: Hit Rate %.2f%%, Avg Time: %.2fms\n", l1HitRate, l1Time / (l1Hits == 0 ? 1 : l1Hits));
        System.out.printf("L2: Hit Rate %.2f%%, Avg Time: %.2fms\n", l2HitRate, l2Time / (l2Hits == 0 ? 1 : l2Hits));
        System.out.printf("L3: Hit Rate %.2f%%, Avg Time: %.2fms\n", l3HitRate, l3Time / (l3Hits == 0 ? 1 : l3Hits));
        System.out.printf("Overall: Hit Rate %.2f%%, Avg Time: %.2fms\n",
                (l1Hits + l2Hits + l3Hits) * 100.0 / totalRequests, overallTime);
    }

    public static void main(String[] args) {

        MultiLevelCache cache = new MultiLevelCache();

        for (int i = 1; i <= 5; i++) {
            cache.addVideoToDB("video_" + i, "Content of video " + i);
        }

        cache.getVideo("video_1");
        cache.getVideo("video_1");
        cache.getVideo("video_1");
        cache.getVideo("video_1");
        cache.getVideo("video_2");
        cache.getVideo("video_3");

        cache.getStatistics();
    }
}