private void getNearestLSA(Location myLocation){
    
    List<LSA> lsas = JSONParser.getLsaList();
    LSA nearestLSA = null;
    float distance;
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
            if (distance < lsa.getDistance() && 
            distance <= Constants.MIN_LSA_DISTANCE && minDistance > distance){ 
                minDistance = distance;
                nearestLSA = lsa;
            }
        }
        // LSA gefunden --> per Listener MainActivity benachrichtigen
        if(nearestLSA != null && onSetListener != null){                
            onSetListener.onLSASet(nearestLSA, myLocation);
        }
    }
    // LSA gesetzt und Entfernung ist hoeher als gegebene Distanz
    if(nearestLSA!=null && 
        myLocation.distanceTo(nearestLSA.getLsaLocation()) > Constants.MIN_LSA_DISTANCE){
        nearestLSAs = null;
    }
}
