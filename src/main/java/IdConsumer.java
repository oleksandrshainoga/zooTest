import org.apache.zookeeper.KeeperException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by Oleksandr_Shainoga on 11/24/2017.
 */
public class IdConsumer implements Runnable {

    ZooIDGeter zooIDGeter = new ZooIDGeter();
    String name;
    int[] ids;

    public IdConsumer(String name) throws IOException, InterruptedException {
        this.name = name;
    }

    public void run() {
        while (true) {
            try {
                ids = zooIDGeter.getIdArray();
            } catch (KeeperException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            for (int id : ids) {
                System.out.println(name + " use " + id);
            }
        }
    }
}
