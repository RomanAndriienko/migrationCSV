package com.softseve.migration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MigrationApplication {

    public static void main(String[] args) {
        SpringApplication.run(MigrationApplication.class, args);
//        Connection con;
//        Statement stmt;
//        int result = 0;
//        try {
//            Class.forName("org.hsqldb.jdbc.JDBCDriver");
//            con = DriverManager.getConnection("jdbc:hsqldb:mem:patient", "SA", "");
//            stmt = con.createStatement();
//            result = stmt.executeUpdate(
//                "INSERT into source VALUES (1, 'name', 'date', 'line', 'a46c8b35-6943-4058-89ed-fef8a9698efe')");
//            con.commit();
//        } catch (Exception e) {
//            e.printStackTrace(System.out);
//        }
//        System.out.println(result + " rows effected");
//        System.out.println("Rows inserted successfully");
    }
}
