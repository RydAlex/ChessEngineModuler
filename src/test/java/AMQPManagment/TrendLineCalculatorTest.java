package AMQPManagment;

import java.awt.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by aleksanderr on 16/07/17.
 */
public class TrendLineCalculatorTest {

    List<Point> lowPeak_pointsList = new LinkedList<Point>(Arrays.asList(
            new Point(1, 1000),
            new Point(2, 970),  // -30
            new Point(3, 950),  // -20
            new Point(4, 920),  // -30
            new Point(5, 960),  // +40
            new Point(6, 1020), // +60
            new Point(7, 1070), // +50
            new Point(8, 1090), // +20
            new Point(9, 1120), // +30
            new Point(10, 1160) // +40
    ));

    List<Point> highDrop_pointsList = new LinkedList<Point>(Arrays.asList(
            new Point(1, 1000),
            new Point(2, 1020),  // +20
            new Point(3, 1050),  // +30
            new Point(4, 1080),  // +30
            new Point(5, 1130),  // +50
            new Point(6, 1150),  // +20
            new Point(7, 1050),  // -100
            new Point(8, 1000),  // -50
            new Point(9, 970),   // -30
            new Point(10, 950)   // -20
    ));



}