package src;

import javax.imageio.ImageIO;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;

public class Constants {
    // Gives the RGB values for the colors below
    public static final int[] white = {255, 255, 255};
    public static final int[] light_gray = {190, 190, 190};
    public static final int[] gray = {127, 127, 127};
    public static final int[] dark_gray = {63, 63, 63};
    public static final int[] black = {0, 0, 0};
    public static final int[] red = {255, 0, 0};
    public static final int[] green = {0, 255, 0};
    public static final int[] blue = {0, 0, 255};
    public static final int[] yellow = {255, 255, 0};
    public static final int[] magenta = {255, 0, 255};
    public static final int[] cyan = {0, 255, 255};
    public static final int[] orange = {255, 100, 0};
    public static final int[] purple = {75, 0, 130};
    public static final int[] medium_green = {0, 100, 0};
    public static final int[] pleasing_green = {51, 178, 73};
    public static final int[] backgroundColor = {200, 200, 200};

    public static final int screenWidth = 1300;
    public static final int screenHeight = 650;

    public static final int KEY_A = KeyEvent.VK_A;
    public static final int KEY_B = KeyEvent.VK_B;
    public static final int KEY_C = KeyEvent.VK_C;
    public static final int KEY_D = KeyEvent.VK_D;
    public static final int KEY_E = KeyEvent.VK_E;
    public static final int KEY_F = KeyEvent.VK_F;
    public static final int KEY_G = KeyEvent.VK_G;
    public static final int KEY_H = KeyEvent.VK_H;
    public static final int KEY_I = KeyEvent.VK_I;
    public static final int KEY_J = KeyEvent.VK_J;
    public static final int KEY_K = KeyEvent.VK_K;
    public static final int KEY_L = KeyEvent.VK_L;
    public static final int KEY_M = KeyEvent.VK_M;
    public static final int KEY_N = KeyEvent.VK_N;
    public static final int KEY_O = KeyEvent.VK_O;
    public static final int KEY_P = KeyEvent.VK_P;
    public static final int KEY_Q = KeyEvent.VK_Q;
    public static final int KEY_R = KeyEvent.VK_R;
    public static final int KEY_S = KeyEvent.VK_S;
    public static final int KEY_T = KeyEvent.VK_T;
    public static final int KEY_U = KeyEvent.VK_U;
    public static final int KEY_V = KeyEvent.VK_V;
    public static final int KEY_W = KeyEvent.VK_W;
    public static final int KEY_X = KeyEvent.VK_X;
    public static final int KEY_Y = KeyEvent.VK_Y;
    public static final int KEY_Z = KeyEvent.VK_Z;
    public static final int KEY_DOWN = KeyEvent.VK_DOWN;
    public static final int KEY_RIGHT = KeyEvent.VK_RIGHT;
    public static final int KEY_UP = KeyEvent.VK_UP;
    public static final int KEY_LEFT = KeyEvent.VK_LEFT;
    public static final int KEY_PERIOD = KeyEvent.VK_PERIOD;
    public static final int KEY_SPACE = KeyEvent.VK_SPACE;
    public static final int KEY_QUESTION_MARK = 47;



    public static HashMap<String, BufferedImage> pathToImage = new HashMap<>();

    public static void loadImage(String path) {
//        try {
//            pathToImage.put(path, ImageIO.read(new File(path)));
//        }
//
//        catch (Exception e) {
//            System.out.println("Image Failed");
//            e.printStackTrace();
//        }
    }
}
