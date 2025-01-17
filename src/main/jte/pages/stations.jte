@import com.comsystoreply.labs.chargingstations.adapters.web.*
@import com.comsystoreply.labs.chargingstations.app.model.util.*

@param Paged<StationResponse> stationsPage

@template.layout.main(
head = @`
    <script src="https://tiles.versatiles.org/assets/maplibre-gl/maplibre-gl.js"></script>
    <link href="https://tiles.versatiles.org/assets/maplibre-gl/maplibre-gl.css" rel="stylesheet"/>
    <script>
        let map
        let markers = []
        let stations = []

        function removeMarkers(markers) {
            setTimeout(() => {
                markers.forEach(m => m.remove())
            }, 1000)
        }

        function setSelectedStation(stationId) {
            let station = findStationById(stationId)
            document.getElementById("selected-station_name").textContent = station.operator;
            document.getElementById("selected-station_address").textContent =
                station.location.address.street + " " +
                station.location.address.houseNumber + ", " +
                station.location.address.zipCode + " " +
                station.location.address.city;
            document.getElementById("selected-station_geo").textContent =
                station.location.geo.lon + " / " + station.location.geo.lat
            console.log(station.chargers)
            document.getElementById("selected-station_chargers").innerHTML =
                station.chargers.map(charger => "<li class='pl-2'>" + charger.count + "x " + charger.name +"</lii>").join("")


            document.querySelectorAll("li").forEach(el => el.classList.remove("bg-gray-200"))
            document.getElementById("station-" + station.id).classList.add("bg-gray-200")

            map.flyTo({
                center: [station.location.geo.lon, station.location.geo.lat],
                zoom: 16,
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
            let oldMarkers = markers
            markers = stations
                .map(s => {
                    let m = new maplibregl.Marker().setLngLat([
                        s.location.geo.lon,
                        s.location.geo.lat
                    ])
                    m.id = s.id
                    return m;
                });
            markers.forEach(m => m.addTo(map))
            removeMarkers(oldMarkers)
        }

        function findStations() {
            return Array.from(document.querySelectorAll("li.station")).map(elem => JSON.parse(elem.dataset.station))
        }

        function findStationById(stationId) {
            return stations.find(s => s.id === stationId)
        }

        function updateUi() {
            stations = findStations()
            let selected = stations.at(0)
            if (!map) {
                map = createMap(selected.location.geo)
            }
            createMarkers(stations)
            setSelectedStation(selected.id, map)
        }
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
                    <ul id="selected-station_chargers" class="list-disc list-inside">

                    </ul>
                </div>
            </div>
        </div>
    </div>

    <script>
        updateUi();
        document.addEventListener("htmx:afterSettle", (e) => {
            updateUi()
        })
    </script>

`)