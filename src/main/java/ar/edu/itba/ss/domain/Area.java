package ar.edu.itba.ss.domain;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

public class Area {
    private Vector2D upLeftPoint;
    private Vector2D downRightPoint;

    private Area(){}

    Area(double x1, double y1, double x2, double y2){
        upLeftPoint = new Vector2D(x1,y1);
        downRightPoint = new Vector2D(x2,y2);
    }
}
