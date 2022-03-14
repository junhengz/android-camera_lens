package com.example.depthoffieldcalculator.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.example.depthoffieldcalculator.model.Lens;

public class LensManager {
    List<Lens> lensList;
    private static LensManager instance;

    private LensManager(){}
    public static LensManager getInstance()
    {
        if(instance == null) {
            instance = new LensManager();
            instance.lensList = new ArrayList<>();
        }
        return instance;
    }

    public static LensManager reconstruct()
    {
        instance = new LensManager();
        instance.lensList = new ArrayList<>();
        return instance;
    }
    public void add(Lens len)
    {
        this.lensList.add(len);
    }



    public Lens get_len(int index)
    {return this.lensList.get(index);}

    public List get_Lenslist()
    {
        return this.lensList;
    }


    public Iterator<Lens> iterator()
    {return lensList.iterator();}
}
