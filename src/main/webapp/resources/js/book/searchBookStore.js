//마커를 클릭하면 장소명을 표출할 인포윈도우 입니다
var infowindow = null;
//지도 객체입니다.
var map = null;
//장소 검색 객체입니다.
var ps = null;
//내 위치 객체입니다.
var curPos = {
	lat : 0,
	lon : 0
};
//지도 확대레벨
var defZoomLevel = 7;

$(function() {
	var isInit = false;
	if(navigator.geolocation) {
		navigator.geolocation.getCurrentPosition(function(position){
			curPos.lat = position.coords.latitude;
			curPos.lon = position.coords.longitude;
			initMap();
		});
	} else {
		alert("Geolocation is not supported by this browser.");
	}
	
	$("#chaekgabang_search_bookstore_searchinput").keypress(function(event){
		if(event.which === 13)
			if(ps !== null) // 키워드로 장소를 검색합니다
				ps.keywordSearch($(this).val(), placesSearchCB); 
	});

	$("#chaekgabang_search_bookstore_searchbtn").click(function(){
		var query = $("#chaekgabang_search_bookstore_searchinput").val();
		if(ps !== null) // 키워드로 장소를 검색합니다
			ps.keywordSearch(query, placesSearchCB); 
	});
});

function initMap() {
	infowindow = new daum.maps.InfoWindow({zIndex:1});

	var mapContainer = document.getElementById('chaekgabang_search_bookstore_map'), // 지도를 표시할 div 
	    mapOption = {
	        center: new daum.maps.LatLng(curPos.lat, curPos.lon), // 지도의 중심좌표
	        level: defZoomLevel // 지도의 확대 레벨
	    };  

	// 지도를 생성합니다    
	map = new daum.maps.Map(mapContainer, mapOption); 

	// 장소 검색 객체를 생성합니다
	ps = new daum.maps.services.Places();
	
	//내 위치 마커 표시
	var bounds = new daum.maps.LatLngBounds();

	var place = {
		place_name : "내 위치",
		y : curPos.lat,
		x : curPos.lon
	};
	displayMarker(place);
	bounds.extend(new daum.maps.LatLng(curPos.lat, curPos.lon));

	map.setBounds(bounds);
	map.setLevel(defZoomLevel);
	map.setCenter(new daum.maps.LatLng(curPos.lat, curPos.lon));
}

// 키워드 검색 완료 시 호출되는 콜백함수 입니다
function placesSearchCB (data, status, pagination) {
    if (status === daum.maps.services.Status.OK) {

        // 검색된 장소 위치를 기준으로 지도 범위를 재설정하기위해
        // LatLngBounds 객체에 좌표를 추가합니다
        var bounds = new daum.maps.LatLngBounds();

        for (var i=0; i<data.length; i++) {
            displayMarker(data[i]);    
            bounds.extend(new daum.maps.LatLng(data[i].y, data[i].x));
        }

        // 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다
        map.setBounds(bounds);
        map.setLevel(defZoomLevel);
        map.setCenter(new daum.maps.LatLng(curPos.lat, curPos.lon));
    } 
}

// 지도에 마커를 표시하는 함수입니다
function displayMarker(place) {
    // 마커를 생성하고 지도에 표시합니다
    var marker = new daum.maps.Marker({
        map: map,
        position: new daum.maps.LatLng(place.y, place.x) 
    });

    // 마커에 클릭이벤트를 등록합니다
    daum.maps.event.addListener(marker, 'click', function() {
        // 마커를 클릭하면 장소명이 인포윈도우에 표출됩니다
        infowindow.setContent('<div style="padding:5px;font-size:12px;">' + place.place_name + '</div>');
        infowindow.open(map, marker);
    });
}