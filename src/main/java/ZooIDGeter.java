import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;


public class ZooIDGeter {
    ZooKeeper zk = new ZooKeeperConnection().connect("127.0.0.1:2181");

    public ZooIDGeter() throws IOException, InterruptedException {
    }

    int step = 50;

    public int[] getIdArray() throws KeeperException, InterruptedException, UnsupportedEncodingException {
        Integer start = getNewArray();
        int retInt[] = new int[step];
        for (int i = 0; i < step; i++) {
            retInt[i] = start + i;
        }
        return retInt;
    }

    public Integer getNewArray() throws KeeperException, InterruptedException, UnsupportedEncodingException {
        while (true) {

            Stat stat = new Stat();
            byte data[] = zk.getData("/maxId", false, stat);
            int max = fromByteArray(data);
            int newMax = max + step;
            try {
                zk.setData("/maxId", intToByteArray(max + step), stat.getVersion());
                return newMax;
            } catch (Exception e) {
                System.out.println("concurent troubles ");
            }
        }
    }

    public static final byte[] intToByteArray(int value) {
        return new byte[]{
                (byte) (value >>> 24),
                (byte) (value >>> 16),
                (byte) (value >>> 8),
                (byte) value};
    }

    int fromByteArray(byte[] bytes) {
        return ByteBuffer.wrap(bytes).getInt();
    }
}
