/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package myfaces1;
import java.io.*;
import java.sql.*;

/**
 * A typical simple backing bean, that is backed to <code>helloworld.jsp</code>
 *
 * @author <a href="mailto:matzew@apache.org">Matthias We�endorf</a>
 */
public class HelloWorldBacking
{

    //properties
    private String name;
    private String safe;
    private String dbdata;

    /**
     * default empty constructor
     */
    public HelloWorldBacking()
    {
    }

    //-------------------getter & setter
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getSafe()
    {
        return safe;
    }
    public void setSafe(String safe)
    {
        this.safe = safe;
    }
    public String getDbdata() {
        return dbdata;
    }
    public void setDbdata(String dbdata) {
        this.dbdata = dbdata;
    }

    /**
     * Method that is backed to a submit button of a form.
     */
    public String send()
    {
        System.out.println("XXX hi: " + this.getName());
        this.safe = "safe";
        try {
            Connection c = DriverManager.getConnection("jdbc:hsqldb:mem:aname", "sa", "");
            Statement stmt = c.createStatement();
            stmt.execute("CREATE TABLE tbl (a int, b varchar(200));");
            Statement stmt2 = c.createStatement();
            stmt2.execute("INSERT INTO tbl (a, b) VALUES (1, '" + this.getName() + "');");    
            Statement stmt3 = c.createStatement();
            stmt3.execute("INSERT INTO tbl (a, b) VALUES (2, '" + this.getSafe() + "');");
            PreparedStatement stmt4 = c.prepareStatement("SELECT * FROM tbl");
            ResultSet rs = stmt4.executeQuery();
            StringBuffer sb = new StringBuffer();
            while(rs.next()) {
                sb.append(rs.getString("b"));
                sb.append(" / ");
            }
            setDbdata(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            FileInputStream fis = new FileInputStream(this.getName());      
        } catch (Exception ignore) {
        }
        try {
            FileInputStream fis = new FileInputStream(this.getSafe());
        } catch (Exception ignore) {
        }
        //do real logic
        return ("success");
    }
}
