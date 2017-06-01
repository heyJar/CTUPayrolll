/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AppPackage;

/**
 *
 * @author H E Y J A R
 */
public class JobTypes {
    String id;
    String name;
    String rate;

    public JobTypes(String id, String name, String rate) {
        this.id = id;
        this.name = name;
        this.rate = rate;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRate() {
        return rate;
    }
    
    
    
}
