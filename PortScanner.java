
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.Scanner;
/**
 * MultiThread port scanner on a given IP Address using the Executor Library
 * @author Adam Ben Hmida
 */
public class PortScanner {

    public static void main(String[] args) {
    	Scanner IPScanner = new Scanner(System.in);
    	
    	System.out.println("Enter the IP Address you would like to scan: "); //user inputs IP to scan
        String host = IPScanner.next();
        int timeout = 1000; // Timeout for the port connection in milliseconds
        int threads = 50; // Number of threads to use for scanning

        scanPorts(host, timeout, threads);
    }
   
    public static void scanPorts(String host, int timeout, int threads) {
        ExecutorService executor = Executors.newFixedThreadPool(threads);

        for (int port = 1; port <= 65535; port++) {
            executor.execute(new PortScanTask(host, port, timeout));
        }

        executor.shutdown();
    }
    
    static class PortScanTask implements Runnable {
        private String host;
        private int port;
        private int timeout;

        public PortScanTask(String host, int port, int timeout) {
            this.host = host;
            this.port = port;
            this.timeout = timeout;
        }

        @Override
        public void run() {
            try {
                Socket socket = new Socket();
                socket.connect(new InetSocketAddress(host, port), timeout);
                socket.close();
                System.out.println("Port " + port + " is open");
            } catch (Exception e) {
                // Port is likely closed or filtered
            }
        }
    }
}
