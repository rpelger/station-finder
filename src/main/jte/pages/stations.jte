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

        function createMap(lon, lat) {
            if (map) {
                return
            }

            map = new maplibregl.Map({
                container: 'stations-map',
                style: 'https://tiles.versatiles.org/assets/styles/colorful.json',
                center: [lon, lat],
                zoom: 16 // starting zoom
            }).addControl(new maplibregl.NavigationControl());
        }

        function createMarkers(positions) {
            let oldMarkers = markers
            markers = positions.map(pos => new maplibregl.Marker().setLngLat(pos))
            markers.forEach(m => m.addTo(map))
            setTimeout(() => oldMarkers.forEach(m => m.remove()), 1000)
        }

        function centerOnMap(lon, lat) {
            if (map) {
                map.flyTo({
                    center: [lon, lat],
                    zoom: 16,
                    essential: true
                })
            }
        }

        function select(element) {
            element.focus()
            centerOnMap(element.dataset.lon, element.dataset.lat)
        }

        function updateUi() {
            const stationElements = Array.from(document.querySelectorAll("li.station"))
            const selected = stationElements[0]
            createMap(selected.dataset.lon, selected.dataset.lat)
            createMarkers(stationElements.map(elem => [elem.dataset.lon, elem.dataset.lat]))
            select(selected)
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
            <div id="station-details" class="mt-2">
                @template.partials.selected_station(station=selectedStation)
            </div>
        </div>

        <script>
            updateUi();
            document.addEventListener("htmx:afterSettle", (e) => {
                if (e.detail.target.id === "stations-table") {
                    updateUi()
                }
            })
        </script>

`)