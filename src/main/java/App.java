import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.math.BigInteger;

/**
 * Created by Oleksandr_Shainoga on 11/23/2017.
 */
public class App {
    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        //init stage
        ZooKeeper zk = new ZooKeeperConnection().connect("127.0.0.1:2181");
        byte[] data = "0".getBytes();
        try {
            zk.create("/maxId", BigInteger.valueOf(0).toByteArray(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        } catch (Exception e){
            System.out.println("node already exist");
        }
        Thread.sleep(5000);
        zk.setData("/maxId", intToByteArray(0), zk.exists("/maxId",true).getVersion());
        IdConsumer idConsumer1 = new IdConsumer("first");
        IdConsumer idConsumer2 = new IdConsumer("second");
        Thread t1 = new Thread(idConsumer1);
        Thread t2 = new Thread(idConsumer2);
        t1.start();
        t2.start();
        for (int i = 0; i <30 ; i++) {
            Thread t=new Thread(new IdConsumer("name"+i));
            t.start();
        }
    }
    public static final byte[] intToByteArray(int value) {
        return new byte[] {
                (byte)(value >>> 24),
                (byte)(value >>> 16),
                (byte)(value >>> 8),
                (byte)value};
    }
}
