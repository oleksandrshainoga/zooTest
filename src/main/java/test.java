import java.io.IOException;

/**
 * Created by Oleksandr_Shainoga on 11/24/2017.
 */
public class test {
    public static void main(String[] args) throws IOException, InterruptedException {
        ZooKeeperConnection zooKeeperConnection=new ZooKeeperConnection();
        zooKeeperConnection.connect("127.0.0.1:2181");
        System.out.println("qwe");
    }
}
