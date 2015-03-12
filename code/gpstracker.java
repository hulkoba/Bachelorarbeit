// Registrieren fuer GPS Updates (alle 3 sekunden, nach 5 Metern)
locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 
										Constants.UPDATE_INTERVAL, 
										Constants.MY_DISTANCE, this);
private void getNearestLSA(Location myLocation){
    Log.d("count ", "get Nearest");
    List<LSA> lsas = JSONParser.getLsaList();
    LSA nearestLSA = null;
    float distance = 0;
    float minDistance = Float.MAX_VALUE;

// Ampeln in der Umgebung suchen
// wurden schon Ampeln gespeichert?
if (nearestLSAs == null ) {            
    nearestLSAs = new ArrayList<LSA>();
    for (LSA lsa : lsas) {
    	// fuer saemtliche Ampeln die Distanz errechnen
        distance = myLocation.distanceTo(lsa.getLsaLocation());
        
        // ist die Distanz im Radius?
        if (distance <= Constants.MIN_LSA_DISTANCE) {            
            // Ampel mit Distanz speichern
            LSA nlsa = new LSA(distance, 
               	lsa.getName(), 
               	lsa.getLsaLocation(), 
               	lsa.isDependsOnTraffic(), 
             	lsa.getSzpls());
            nearestLSAs.add(nlsa);
        }
    }

// Ampeln in der Umgebung gefunden, noch keine Ampel festgelegt
} else if(nearestLSAs != null && nearestLSA == null) {          
    for(LSA lsa : nearestLSAs){
    	// fuer jede Ampel in der Umgebung Distanz errechnen
        distance = myLocation.distanceTo(lsa.getLsaLocation());

        // verkleinert sich Distanz im Gegensatz zur Vorherigen?
        // Ist die Distanz von allen nahen LSAs am geringsten? 
        if (distance < lsa.getDistance() 
        && distance <= Constants.MIN_LSA_DISTANCE  
        && minDistance > distance){ 
            minDistance = distance;
        	// naechste Ampel ermittelt!
            nearestLSA = lsa;    
            if(onSetListener != null){
            // LSA gefunden?  per Listener MainActivity benachrichtigen              
        		onSetListener.onLSASet(nearestLSA, myLocation);
    		}               
        }
    }   
}

// Entfernung ist hoeher als gegebene Distanz?
// und Entfernung ist groesser als vorher
if(nearestLSA != null) {
    if(myLocation.distanceTo(nearestLSA.getLsaLocation()) 
	> Constants.MIN_LSA_DISTANCE 
      	|| myLocation.distanceTo(nearestLSA.getLsaLocation()) > (distance+0.1)) {
        
        // Ampeln und temporaere Liste loeschen
        nearestLSAs = null;
      	nearestLSA = null;
    }
}
}
