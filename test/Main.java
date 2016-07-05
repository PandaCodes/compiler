
/**
 * Created by user on 04.06.16.
 */
public class Main {

    public static int main(String[] args) {
        int x = 1;
        int b = 6;
        int i = 0;

        while (i < b) {
            x *= 2;
            i += 1;
        }
       // for (int i = 5; i < 4; i += 1)

        for(int j=0; j < 17; j++) {
x = j;
            j++;
        }


        int a=80;
        b = 160;
        //if (a == 0)
          //  return b;

        while (b != 0) {
            if (a > b)
                a = a - b;
            else
                b = b - a;
        }

        return a;
    }
}
