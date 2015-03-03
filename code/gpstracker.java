if(myNewLocation == null || location.distanceTo(myNewLocation) >= Constants.MY_DISTANCE){
    myNewLocation = location;
    getNearestLSA(location);
}
private void getNearestLSA(Location myLocation){
    Log.d("count ", "get Nearest");
    List<LSA> lsas = JSONParser.getLsaList();
    LSA nearestLSA = null;
    float distance = 0;
    float minDistance = Float.MAX_VALUE;

// Ampeln in der Umgebung suchen
if (nearestLSAs == null ) {            
    nearestLSAs = new ArrayList<LSA>();

    for (LSA lsa : lsas) {
        distance = myLocation.distanceTo(lsa.getLsaLocation());
        if (distance <= Constants.MIN_LSA_DISTANCE) {
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
        distance = myLocation.distanceTo(lsa.getLsaLocation());
        if (distance < lsa.getDistance() 
        && distance <= Constants.MIN_LSA_DISTANCE  
        && minDistance > distance){ 
            minDistance = distance;
            nearestLSA = lsa;    
            if(onSetListener != null){
            // LSA gefunden?  per Listener MainActivity benachrichtigen              
        		onSetListener.onLSASet(nearestLSA, myLocation);
    		}               
        }
    }   
}

//Entfernung ist hoeher als geg. Distanz oder Entfernung ist groesser als vorher
if(nearestLSA != null) {
    if(myLocation.distanceTo(nearestLSA.getLsaLocation()) > Constants.MIN_LSA_DISTANCE 
      	|| myLocation.distanceTo(nearestLSA.getLsaLocation()) > (distance+0.1)) {
        nearestLSAs = null;
      	nearestLSA = null;
    }
}
}