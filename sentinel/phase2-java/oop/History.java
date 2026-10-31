import java.util.ArrayList;
import java.util.List;

/**
 * The dashboard's memory: every valid reading that went by, and what can be
 * asked about them.
 *
 * ┌─ ENCAPSULATION ────────────────────────────────────────────────────────┐
 * │ The array and the counter are private and are NEVER handed out. Nobody │
 * │ can insert a reading without going through add(), nor delete, reorder, │
 * │ or set count = -5. Compare with PAIN 2 and PAIN 5 in the procedural     │
 * │ version: sumPct, maxPct, tsMaxPct, names[], times[]... eleven variables │
 * │ anyone could overwrite from any line.                                   │
 * └─────────────────────────────────────────────────────────────────────────┘
 *
 * ┌─ ABSTRACTION ──────────────────────────────────────────────────────────┐
 * │ Main asks getAverageMemory() and gets a number. It does not know        │
 * │ whether there is an array, a list or a database underneath. The whole  │
 * │ implementation can change without touching whoever uses it.            │
 * └─────────────────────────────────────────────────────────────────────────┘
 */
public class History {

    /** One hour of readings at one per second. Private detail: nobody outside knows. */
    private static final int CAPACITY = 3600;

    private final List<Snapshot> readings = new ArrayList<>();

    public void add(Snapshot reading) {
        if (reading == null) {
            throw new IllegalArgumentException("cannot add a null reading");
        }
        if (readings.size() == CAPACITY) {
            readings.remove(0);                // olvidar la más vieja: una línea
        }
        readings.add(reading);
    }

    public int getCount() {
        return readings.size();               // el contador desapareció
    }

    /** Integer average of memory %, or 0 if there are no readings. */
    public int getAverageMemory() {
        if (getCount() == 0) {
            return 0;
        }
        long sum = 0;
        for(Snapshot r : readings){
            sum += r.getMemoryPercent();
        }
        return (int) (sum / getCount());
    }

    /** The reading with the highest memory %, or null if there are none. */
    public Snapshot getMemoryPeak() {
        Snapshot peak = null;
        for(Snapshot p : readings){
            if(peak == null || p.getMemoryPercent() > peak.getMemoryPercent()) {
                peak = p;
            }
        }
        return peak;
    }

    public double getMaxLoad() {
        double max = 0;
        for(Snapshot l : readings){
            if(l.getLoad1() > max){
                max = l.getLoad1();
            }
        }
        return max;
    }

    /** How many readings have that process as top_proc. */
    public int countOf(String process) {
        int times = 0;

        for(Snapshot p: readings){
            if(p.getTopProc().equals(process)){
                times++;
            }
        }
        return times;
    }

    /** The top_proc that appeared most often, or "" if there are no readings. */
    public String getMostFrequentProcess() {
        String mostFrequent = "";
        int mostTimes = 0;

        for(Snapshot c : readings){
            String candidate = c.getTopProc();
            int times = countOf(candidate);
            if(times > mostTimes){
                mostTimes = times;
                mostFrequent = candidate;
            }
        }
        return mostFrequent;
    }
}
