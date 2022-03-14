package com.example.depthoffieldcalculator.model;

import java.text.DecimalFormat;

public class Lens {
    private String make;
    private double maximum_aperture;
    private int  focal_length;


    public Lens(String make_name, double max_aperture, int focal_len)
    {
        this.make = make_name;
        this.maximum_aperture = max_aperture;
        this.focal_length = focal_len;
    }


    public double get_max_aperture()
    {
        return this.maximum_aperture;
    }

    public int get_focal_length()
    {
        return this.focal_length;
    }


    public String formatM(double distanceInM) {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(distanceInM);
    }
    public String lentoString()
    {
        return make+" "+focal_length+"mm "+"F"+formatM(maximum_aperture);

    }

}
