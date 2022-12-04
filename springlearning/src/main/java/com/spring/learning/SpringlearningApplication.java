package com.spring.learning;


import com.spring.learning.dependency.injection.Vehicle;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringlearningApplication
{
    public static void main(String[] args)
    {
        //Created an object of application bean and provided xml configuration file in it
        ApplicationContext applicationContext=new ClassPathXmlApplicationContext("spring.xml");

        //getting a bean of id car using xml based configuration
        /*Vehicle car=(Vehicle) applicationContext.getBean("Car");
        car.drive();*/

        // getting a bean of id bike
        Vehicle bike=(Vehicle) applicationContext.getBean("bike");
        bike.drive();

        // getting an object of tyre
        /*Tyre tyre=(Tyre) applicationContext.getBean("tyre");
        System.out.println(tyre.toString());*/

        //Annotation based configuration
        Vehicle car=(Vehicle) applicationContext.getBean("Car");
        car.drive();
    }

}
