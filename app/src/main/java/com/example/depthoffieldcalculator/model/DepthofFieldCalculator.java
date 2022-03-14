package com.example.depthoffieldcalculator.model;
import com.example.depthoffieldcalculator.model.Lens;
public class DepthofFieldCalculator {

    private double In_focus_near;
    private double In_focus_far;
    private double DoF;
    private double Hyperfocal;

    public DepthofFieldCalculator(){
        In_focus_near=0;
        In_focus_far=0;
        DoF=0;
        Hyperfocal=0;
    }

    public double In_focus_near(Lens len, double aperture, double COC, double distance)
    {
        double Hyperfocal =  (len.get_focal_length()*len.get_focal_length())/(aperture*COC);
        In_focus_near= (Hyperfocal*distance*1000)/(Hyperfocal+(distance*1000-len.get_focal_length()))/1000;
        return In_focus_near;
    }


    public void In_focus_far(Lens len,double aperture, double COC,double distance)
    {
        double Hyperfocal =  (len.get_focal_length()*len.get_focal_length())/(aperture*COC);
        In_focus_far = (Hyperfocal*distance*1000)/(Hyperfocal-(distance*1000-len.get_focal_length()))/1000;

    }

    public void DoF(Lens len,double aperture, double COC,double distance)
    {
        double Hyperfocal =  (len.get_focal_length()*len.get_focal_length())/(aperture*COC);
        double In_focus_far = (Hyperfocal*distance*1000)/(Hyperfocal-(distance*1000-len.get_focal_length()));
        double In_focus_near= (Hyperfocal*distance*1000)/(Hyperfocal+(distance*1000-len.get_focal_length()));
        DoF =  (In_focus_far-In_focus_near)/1000;
    }

    public void Hyperfocal(Lens len, double aperture, double COC)
    {
        Hyperfocal =  (len.get_focal_length()*len.get_focal_length())/(aperture*COC)/1000;

    }

    public double getIn_focus_near()
    {
        return In_focus_near;
    }
    public double getIn_focus_far()
    {
        return In_focus_far;
    }
    public double getDoF()
    {
        return DoF;
    }
    public double getHyperfocal()
    {
        return Hyperfocal;
    }

}
