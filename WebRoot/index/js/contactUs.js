$(function(){
	
	/**
	 * Initializes the Google Map.
	 */
	function initializeGoogleMap() {
		// Define the coordinates and name of Meta-Universe Technology Inc.
		var metaUniverseCoords = {lat: 51.0839149, lng: -114.1305542};
		var metaUniverseName = 'Meta-Universe Technology Inc.';
		
		var mapCanvas = document.getElementById('map');
		var mapOptions = {
			center: metaUniverseCoords,
			zoom: 15,
			mapTypeId: google.maps.MapTypeId.ROADMAP
		};
		var map = new google.maps.Map(mapCanvas, mapOptions);
		
		var marker = new google.maps.Marker({
	    	position: metaUniverseCoords,
			map: map,
			title: metaUniverseName
		});
	}
	google.maps.event.addDomListener(window, 'load', initializeGoogleMap);
});
