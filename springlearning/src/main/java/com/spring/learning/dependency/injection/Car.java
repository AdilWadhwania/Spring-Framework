package com.spring.learning.dependency.injection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("Car")
public class Car implements Vehicle
{
    @Autowired
    private Tyre tyre;

    public Tyre getTyre()
    {
        return tyre;
    }

    public void setTyre(Tyre tyre)
    {
        this.tyre = tyre;
    }

    public void drive()
    {
        System.out.println("I am driving car "+tyre);
    }
}
