package company.com.locationfinder.Shopping_Cart_Navigator.Model;

public class Node {
        public int x;
        public int y;
        public int[] pred;

        public Node(int x, int y, int[] pred) {
            this.x = x;
            this.y = y;
            this.pred = pred;
        }
}
