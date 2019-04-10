package com.sp.report;

import java.io.Serializable;

public class NameWrapper implements Serializable
{
    private String name;
    private Boolean selected = Boolean.FALSE;
 
    public NameWrapper(String wrapped)
    {
        this.name = wrapped;
    }
 
    public Boolean getSelected()
    {
        return selected;
    }
 
    public void setSelected(Boolean selected)
    {
        this.selected = selected;
    }
 
    public String getName()
    {
        return name;
    }
 
    public void setName(String wrapped)
    {
        this.name = wrapped;
    }
     
    public String toString()
    {
        return name + ": " + selected;
    }
}
