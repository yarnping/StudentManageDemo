package demo.util;

/**
 * 编码生成,参考了snowflake改的
 */
public class IdGen {
    private static IdGen instance;
    private long sequence = 0L;

    private long twepoch = 1469534974657L;
    private long sequenceBits = 3L;

    private long workerIdShift = sequenceBits;
    private long timestampLeftShift = sequenceBits;
    private long sequenceMask = -1L ^ (-1L << sequenceBits);

    private long lastTimestamp = -1L;

    private IdGen() {}

    public static synchronized IdGen getInstance() {
        if (instance == null) {
            instance = new IdGen();
        }
        return instance;
    }

    public synchronized long nextId() {
        long timestamp = timeGen();

        if (timestamp < lastTimestamp) {
            throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }

        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }

        lastTimestamp = timestamp;

        return ((timestamp - twepoch) << timestampLeftShift) | sequence;
    }

    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    protected long timeGen() {
        return System.currentTimeMillis();
    }
}