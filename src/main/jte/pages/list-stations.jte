@import com.comsystoreply.labs.chargingstations.app.model.*
@import com.comsystoreply.labs.chargingstations.app.model.util.*
@param Paged<Station> stationsPage

@template.layout.main(
head = @`
    <script src="https://tiles.versatiles.org/assets/maplibre-gl/maplibre-gl.js"></script>
    <link href="https://tiles.versatiles.org/assets/maplibre-gl/maplibre-gl.css" rel="stylesheet"/>

`,
pageTitle = "Charging Stations",
pageDescription = "A list of all the charging stations.",
content = @`

    !{var currentCenter = stationsPage.items().get(stationsPage.items().size()/2-1);}

    <div class="flex justify-between gap-24">
        @template.partials.stationsTable(stationsPage = stationsPage)
        <div class="grow">
            <div id="map" class="w-full h-[480px] border border-gray-400"></div>
            <div class="mt-2" id="current-center">
                <h2 class="font-bold">${currentCenter.operator()}</h2>
                <div>
                    ${currentCenter.location().address().street()}
                    ${currentCenter.location().address().houseNumber()},
                    ${currentCenter.location().address().zipCode()}
                    ${currentCenter.location().address().city()}
                    <br>
                    <span class="text-gray-400">${currentCenter.location().geo().lon()} / ${currentCenter.location().geo().lat()}</span>
                </div>
            </div>
        </div>
    </div>

    <!-- start map -->
    <script>
        map = new maplibregl.Map({
            container: 'map',
            style: 'https://tiles.versatiles.org/assets/styles/colorful.json',
            center: [${currentCenter.location().geo().lon()}, ${currentCenter.location().geo().lat()}], // starting position [lng, lat]
            zoom: 16 // starting zoom
        }).addControl(new maplibregl.NavigationControl());
        currentMarkers = []
        @for(Station s : stationsPage.items())
        currentMarkers.push(
            new maplibregl.Marker().setLngLat([${s.location().geo().lon()}, ${s.location().geo().lat()}])
        )
        @endfor
        currentMarkers.forEach(marker => marker.addTo(map))
    </script>


`)