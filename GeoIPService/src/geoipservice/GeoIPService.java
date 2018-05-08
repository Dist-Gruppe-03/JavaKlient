/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geoipservice;

import com.cdyne.ws.IPInformation;

/**
 *
 * @author krede
 */
public class GeoIPService {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        IPInformation res = resolveIP("93.163.211.158", "0");
        System.out.println(res.getLatitude() + ", " + res.getLongitude() + " i " + res.getCity() + ", "+ res.getCountry());
    }

    private static IPInformation resolveIP(java.lang.String ipAddress, java.lang.String licenseKey) {
        com.cdyne.ws.IP2Geo service = new com.cdyne.ws.IP2Geo();
        com.cdyne.ws.IP2GeoSoap port = service.getIP2GeoSoap12();
        return port.resolveIP(ipAddress, licenseKey);
    }
    
}
