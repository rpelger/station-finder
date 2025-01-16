@import com.comsystoreply.labs.chargingstations.app.model.*
@import com.comsystoreply.labs.chargingstations.app.model.util.*
@param Paged<Station> stationsPage

@template.layout.main(
head = @`
    <script src="https://tiles.versatiles.org/assets/maplibre-gl/maplibre-gl.js"></script>
    <link href="https://tiles.versatiles.org/assets/maplibre-gl/maplibre-gl.css" rel="stylesheet"/>
    <!-- start map -->
    <script>
        function setSelectedStation(stationId, map) {
            let {station} = stations.find(s => s.id === stationId)
            document.getElementById("selected-station_name").textContent = station.name;
            document.getElementById("selected-station_address").textContent =
                station.address.street + " " +
                station.address.houseNr + ", " +
                station.address.zip + " " +
                station.address.city;
            document.getElementById("selected-station_geo").textContent =
                station.geo.lon + " / " + station.geo.lat

            document.querySelectorAll("li").forEach(el => el.classList.remove("bg-gray-200"))
            document.getElementById("station-"+stationId).classList.add("bg-gray-200")

            map.flyTo({
                center: [station.geo.lon, station.geo.lat],
                essential: true
            });
        }

        function createMap(geo) {
            return new maplibregl.Map({
                container: 'stations-map',
                style: 'https://tiles.versatiles.org/assets/styles/colorful.json',
                center: [geo.lon, geo.lat],
                zoom: 16 // starting zoom
            }).addControl(new maplibregl.NavigationControl());
        }

        function createMarkers(stations) {
            return stations
                .map(s => new maplibregl.Marker().setLngLat([
                    s.geo.lon,
                    s.geo.lat
                ]))
        }

        let map, markers, selected
        let stations = []

    </script>

`,
pageTitle = "Charging Stations",
pageDescription = "A list of all the charging stations.",
content = @`



    !{var selectedStation = stationsPage.items().get(stationsPage.items().size()/2-1);}

    <div class="flex justify-between gap-24">
        <div id="stations-table" class="px-0 sm:w-full lg:w-1/3 ">
            @template.partials.stations_paginated_list(stationsPage = stationsPage)
        </div>

        <div class="grow">
            <div id="stations-map" class="w-full h-[480px] border border-gray-400"></div>
            <div id="selected-station" class="mt-2">
                <h2 id="selected-station_name" class="font-bold">${selectedStation.operator()}</h2>
                <div>
                    <span id="selected-station_address">
                    </span>
                    <br>
                    <span id="selected-station_geo" class="text-gray-400">
                    </span>
                </div>
            </div>
        </div>
    </div>

    <script>

        if (selected) {
            map = createMap(selected.station.geo)
            markers = createMarkers(stations.map(s => s.station))
            markers.forEach(m => m.addTo(map))
            setSelectedStation(selected.id, map)
        }
    </script>



`)